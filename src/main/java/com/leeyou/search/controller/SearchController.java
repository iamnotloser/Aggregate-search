package com.leeyou.search.controller;
import com.google.common.collect.Lists;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leeyou.search.common.BaseResponse;
import com.leeyou.search.common.ErrorCode;
import com.leeyou.search.common.ResultUtils;
import com.leeyou.search.exception.ThrowUtils;
import com.leeyou.search.manager.SearchFacade;
import com.leeyou.search.model.dto.picture.PictureQueryRequest;
import com.leeyou.search.model.dto.post.PostQueryRequest;
import com.leeyou.search.model.dto.search.SearchRequest;
import com.leeyou.search.model.dto.user.UserQueryRequest;
import com.leeyou.search.model.entity.Picture;
import com.leeyou.search.model.enums.SearchTypeEnum;
import com.leeyou.search.model.vo.PostVO;
import com.leeyou.search.model.vo.SearchVO;
import com.leeyou.search.model.vo.UserVO;
import com.leeyou.search.service.PictureService;
import com.leeyou.search.service.PostService;
import com.leeyou.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private PictureService pictureService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Resource
    private SearchFacade searchFacade;
    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) throws IOException {
        return ResultUtils.success(searchFacade.doSearch(searchRequest, request)) ;
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                        HttpServletRequest request) throws IOException {

        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        String searchText = pictureQueryRequest.getSearchText();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Picture> pictures = pictureService.getPictures(searchText, current, size);
        return ResultUtils.success(pictures);


    }


}




package com.leeyou.search.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leeyou.search.common.BaseResponse;
import com.leeyou.search.common.ErrorCode;
import com.leeyou.search.common.ResultUtils;
import com.leeyou.search.exception.ThrowUtils;
import com.leeyou.search.model.dto.picture.PictureQueryRequest;
import com.leeyou.search.model.entity.Picture;
import com.leeyou.search.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;



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




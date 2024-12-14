package com.leeyou.search.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leeyou.search.common.BaseResponse;
import com.leeyou.search.common.ErrorCode;
import com.leeyou.search.common.ResultUtils;
import com.leeyou.search.datasource.*;
import com.leeyou.search.exception.ThrowUtils;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 搜索门面
 */
@Component
public class SearchFacade {
    @Resource
    private PictureService pictureService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Resource
    private DataSourceRegistry dataSourceRegistry;
    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PictureDataSource pictureDataSource;
    public SearchVO doSearch(@RequestBody SearchRequest searchRequest, HttpServletRequest request) throws IOException {
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();
        long pageSize = searchRequest.getPageSize();
        long current = searchRequest.getCurrent();

        SearchVO searchVO = new SearchVO();

        if(searchTypeEnum == null){
            Page<Picture> pictures = pictureDataSource.dosearch(searchText, current, pageSize);
            Page<PostVO> postVOPage = postDataSource.dosearch(searchText, current, pageSize);
            Page<UserVO> userVOPage = userDataSource.dosearch(searchText, current, pageSize);
            searchVO.setUserList(userVOPage.getRecords());
            searchVO.setPostList(postVOPage.getRecords());
            searchVO.setPictureList(pictures.getRecords());
            return searchVO;
        }else{
            DataSource dataSource = dataSourceRegistry.getDataSourceByType(type);
            Page page = dataSource.dosearch(searchText, current, pageSize);
            searchVO.setDataList(page.getRecords());
            return searchVO;

        }


    }
}

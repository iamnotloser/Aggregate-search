package com.leeyou.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leeyou.search.model.dto.post.PostQueryRequest;
import com.leeyou.search.model.entity.Post;
import com.leeyou.search.model.vo.PostVO;
import com.leeyou.search.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子服务实现
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Service
@Slf4j
public class PostDataSource implements DataSource<PostVO> {

    @Resource
    private PostService postService;



    @Override
    public Page<PostVO> dosearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);

        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);
        ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        return postService.getPostVOPage(postPage, request);
    }
}





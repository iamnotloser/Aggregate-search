package com.leeyou.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leeyou.search.model.dto.user.UserQueryRequest;
import com.leeyou.search.model.vo.UserVO;
import com.leeyou.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Service
@Slf4j
public class UserDataSource implements DataSource<UserVO> {

    @Autowired
    private UserService userService;

    @Override
    public Page<UserVO> dosearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize( pageSize);
        return userService.listUserVOByPage(userQueryRequest);
    }
}

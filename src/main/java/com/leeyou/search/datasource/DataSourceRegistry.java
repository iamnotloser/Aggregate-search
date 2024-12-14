package com.leeyou.search.datasource;

import com.leeyou.search.service.PictureService;
import com.leeyou.search.service.PostService;
import com.leeyou.search.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
@Component
public class DataSourceRegistry {


    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PictureDataSource pictureDataSource;
    private  Map<String,DataSource> typeDataSourceMap ;
    @PostConstruct
    public void doinit() {
        System.out.println("初始化数据源");
       typeDataSourceMap = new HashMap() {
            {
                put("post", postDataSource);
                put("picture", pictureDataSource);
                put("user", userDataSource);
            }
        };
    }

    public DataSource getDataSourceByType(String type){
        if(typeDataSourceMap == null){
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}

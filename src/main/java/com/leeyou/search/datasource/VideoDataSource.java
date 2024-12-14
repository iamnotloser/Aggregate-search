package com.leeyou.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class VideoDataSource implements DataSource<Object>{
    @Override
    public Page<Object> dosearch(String searchText, long pageNum, long pageSize) {
        return null;
    }
}

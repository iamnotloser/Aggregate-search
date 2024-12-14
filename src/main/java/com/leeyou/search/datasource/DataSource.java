package com.leeyou.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leeyou.search.model.entity.Picture;
import org.apache.poi.ss.formula.functions.T;

/**
 * 数据源接口，任何调用的数据源得实现该接口的do search
 */
public interface DataSource<T> {
    /**
     * 搜索
     *
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> dosearch(String searchText, long pageNum, long pageSize);
}

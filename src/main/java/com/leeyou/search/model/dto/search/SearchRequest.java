package com.leeyou.search.model.dto.search;

import com.leeyou.search.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchRequest extends PageRequest implements Serializable {
    private String searchText;
    /**
     * 类型
     */
    private String type;
    private static final long serialVersionUID = 1L;
}

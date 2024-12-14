package com.leeyou.search.model.dto.picture;

import com.leeyou.search.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {
    private String searchText;
    private static final long serialVersionUID = 1L;
}

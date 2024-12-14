package com.leeyou.search.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leeyou.search.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class SearchVO implements Serializable {

    private static final long serialVersionUID = 9079756257998777115L;
    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<Picture> pictureList;

    private List<Object> dataList;

    public SearchVO(List<UserVO> userList, List<PostVO> postList, List<Picture> pictureList) {
        this.userList = userList;
        this.postList = postList;
        this.pictureList = pictureList;
    }

    public SearchVO() {}
}

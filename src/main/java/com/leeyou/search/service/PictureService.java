package com.leeyou.search.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leeyou.search.model.entity.Picture;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface PictureService {

    Page<Picture> getPictures(String searchText, long pageNum, long pageSize) throws IOException;
}

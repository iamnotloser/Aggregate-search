package com.leeyou.search.datasource;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leeyou.search.common.ErrorCode;
import com.leeyou.search.exception.BusinessException;
import com.leeyou.search.model.entity.Picture;
import org.apache.poi.ss.formula.functions.T;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PictureDataSource implements DataSource<Picture> {



    @Override
    public Page<Picture> dosearch(String searchText, long pageNum, long pageSize) {
        long current = (pageNum-1)*pageSize;
        String url = String.format("https://cn.bing.com/images/search?q=%s&first=%d", searchText,current);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据获取异常");
        }
        List<Picture> pictureList = new ArrayList<>();

        Elements newsHeadlines = doc.select("#mmComponent_images_1");
        Elements elements = newsHeadlines.select(".iuscp.isv.iuscp.isv");
        for (Element headline : elements) {

            String temp = headline.select(".iusc").get(0).attr("m");
            Map<String, Object> bean = JSONUtil.toBean(temp, Map.class);

            String urlStr = (String) bean.get("murl");
//            System.out.println(urlStr);
            //取标题
            String title = headline.select(".inflnk").get(0).attr("aria-label");
//            System.out.println(title);
            Picture picture = new Picture();

            picture.setUrl(urlStr);
            picture.setTitle(title);
            pictureList.add(picture);
            if(pictureList.size()>=pageSize){
                break;
            }
        }
        Page<Picture> picturePage = new Page<>(pageNum,pageSize);
        picturePage.setRecords(pictureList);
        return picturePage;
    }
}

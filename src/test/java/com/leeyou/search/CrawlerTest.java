package com.leeyou.search;
import java.io.IOException;
import java.util.ArrayList;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.leeyou.search.model.entity.Picture;
import com.leeyou.search.model.entity.Post;
import com.leeyou.search.service.PostService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.jsoup.Jsoup;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class CrawlerTest {
    @Autowired
    private PostService postService;
    @Test
    void testFetchPassage() {
        //1.获取数据
        String json = "{\n" +
                "    \"pageSize\": 12,\n" +
                "    \"sortOrder\": \"descend\",\n" +
                "    \"sortField\": \"createTime\",\n" +
                "    \"tags\": [],\n" +
                "    \"current\": 3,\n" +
                "    \"reviewStatus\": 1,\n" +
                "    \"category\": \"文章\",\n" +
                "    \"hiddenContent\": true\n" +
                "}";
        String url = "https://api.codefather.cn/api/post/list/page/vo";
        String result = HttpRequest.post(url)
                .body(json)
                .execute().body();

        //2.json转java对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
        System.out.println(map);
        JSONObject object = (JSONObject) map.get("data");
        JSONArray records = (JSONArray)object.get("records");
        System.out.println(records);
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject temp = (JSONObject) record;
            Post post = new Post();
            post.setTitle(temp.getStr("title"));
            post.setContent(temp.getStr("plainTextDescription"));
            JSONArray tags = temp.get("tags", JSONArray.class);
            List<String> list = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(list));
            post.setThumbNum(temp.getInt("thumbNum"));
            post.setFavourNum(0);
            post.setUserId(1L);
            postList.add(post);

        }
        System.out.println(postList);
        //3.插入数据库
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
    }

    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = "https://cn.bing.com/images/search?q=%E8%B7%AF%E9%A3%9E&form=HDRSC3&first="+current;
        Document doc = Jsoup.connect(url).get();
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
        }
        System.out.println(pictureList);
    }
}

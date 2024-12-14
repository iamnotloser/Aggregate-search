package com.leeyou.search.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.leeyou.search.esdao.PostEsDao;
import com.leeyou.search.model.entity.Post;
import com.leeyou.search.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 全量同步帖子到 es
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FetchInitPost implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Resource
    private PostEsDao postEsDao;

    @Override
    public void run(String... args) {

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
        JSONArray records = (JSONArray) object.get("records");
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

    }

}

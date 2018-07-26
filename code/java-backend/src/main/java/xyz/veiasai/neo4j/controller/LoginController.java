package xyz.veiasai.neo4j.controller;

import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.service.AuthorService;

@Api(value = "login-controller")
@RestController
@RequestMapping("/")
public class LoginController {
    private String head = "https://api.weixin.qq.com/sns/jscode2session?appid=wxc067744e5e858dce" +
            "&secret=4090f0dfcfc2e55e32b6a84ddd47dcb5" +
            "&grant_type=authorization_code" +
            "&js_code=";
    @Autowired
    private AuthorService authorService;

    @GetMapping("/login")
    public JSONObject Login(@RequestParam String code) {
        RestTemplate template = new RestTemplate();
        String authorJson = template.getForObject(head + code, String.class);
        JSONObject jsonObject = JSONObject.fromObject(authorJson);
        try {
            Author author = new Author();
            String authorId = jsonObject.getString("openid");
            author.setId(authorId);
            authorService.addAuthor(author);
        } catch (Exception ignored) {

        }
        return jsonObject;
    }
}

package xyz.veiasai.neo4j.controller;

import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.pojo.LoginInfo;
import xyz.veiasai.neo4j.result.Result;
import xyz.veiasai.neo4j.service.AuthorService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(value = "login-controller")
@RestController
@RequestMapping("/")
public class LoginController {
    private static Map<String, JSONObject> map = new HashMap<>();
    private static Date date = new Date();
    private String head = "https://api.weixin.qq.com/sns/jscode2session?appid=wxc067744e5e858dce" +
            "&secret=4090f0dfcfc2e55e32b6a84ddd47dcb5" +
            "&grant_type=authorization_code" +
            "&js_code=";
    private static final int LONG_TIME_WAIT = 30000;//30s

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

    @GetMapping("/login/qrcode")
    public JSONObject loginQrcode() {
        Long current = new Date().getTime();
        /*for (Map.Entry<String, JSONObject> entry : map.entrySet()){
            JSONObject jsonObject = entry.getValue();
            Long time = jsonObject.getLong("date");
            if (current - time > LONG_TIME_WAIT){
                map.remove(entry.getKey());
            }
        }*/
        UUID uuid = UUID.randomUUID();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", uuid.toString());
        jsonObject.put("date", current);
        jsonObject.put("state", 0);
        map.put(uuid.toString(), jsonObject);
        jsonObject.put("code", 200);
        return jsonObject;
    }

    @PostMapping("/login/scan")
    public Result loginScan(@RequestBody LoginInfo loginInfo) {
        Result result = new Result();
        JSONObject jsonObject = map.get(loginInfo.getToken());
        if (jsonObject != null) {
            jsonObject.put("state", 1);
            jsonObject.put("userId", loginInfo.getUserId());
            map.put(loginInfo.getToken(), jsonObject);
            result.setMessage("扫码成功");
            result.setCode(200);
        }else {
            result.setCode(404);
            result.setMessage("二维码失效");
        }
        return result;
    }

    @GetMapping("/login/check")
    public JSONObject loginCheck(@RequestParam String token){
        long inTime = new Date().getTime();
        JSONObject jsonObject = null;
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //检测登录
            jsonObject = map.get(token);
            if(jsonObject != null){
                if (jsonObject.getInt("state") == 1) {
                    map.remove(token);
                    break;
                } else if(new Date().getTime() - inTime > LONG_TIME_WAIT){
                    map.remove(token);
                    jsonObject.put("code", 404);
                    break;
                }
            }else{
                jsonObject.put("code", 404);
                break;
            }
        }
        return jsonObject;
    }
}

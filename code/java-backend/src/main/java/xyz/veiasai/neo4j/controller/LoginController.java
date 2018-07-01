package xyz.veiasai.neo4j.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class LoginController {
    private String head = "https://api.weixin.qq.com/sns/jscode2session?appid=wxc067744e5e858dce" +
            "&secret=4090f0dfcfc2e55e32b6a84ddd47dcb5" +
            "&grant_type=authorization_code" +
            "&js_code=";

    @GetMapping("/login")
    public String Login(@RequestParam String code)
    {
        RestTemplate template = new RestTemplate();
        return template.getForObject(head + code, String.class);
    }
}

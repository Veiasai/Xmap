package xyz.veiasai.neo4j.controller;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.veiasai.neo4j.result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Api(value = "uploading-controller")
@RestController
@RequestMapping("/")
public class UploadImgController {
    static private String ACCESS_KEY = "N-lYCoKv-ywDwPvYnga3K_tz1kM3ZYsRkkybuamC";
    static private String SECRET_KEY = "74g5AEbD1jiMZl2xqIpM__OYHG2zWaW592nx0Dj-";
    //要上传的空间
    static private String bucketname = "wechatprogram-veia";

    //密钥配置
    static private Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    ///////////////////////指定上传的Zone的信息//////////////////

    //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
    static private Zone z = Zone.autoZone();
    static private Configuration c = new Configuration(z);
    //创建上传对象
    static private UploadManager uploadManager = new UploadManager(c);

    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    @PostMapping("/upload")
    public Result upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        Result result = new Result();
        try {
            String uuid = UUID.randomUUID().toString();
            //上传到七牛后保存的文件名
            //上传文件的路径
            //调用put方法上传
            Response res = uploadManager.put(file.getBytes(), uuid, getUpToken());
            //打印返回的信息
            result.setCode(200);
            result.setMessage(uuid);
        } catch (QiniuException e) {
            Response r = e.response;
            result.setCode(400);
            result.setMessage(r.toString());
            // 请求失败时打印的异常的信息
        }
        return result;
    }
}

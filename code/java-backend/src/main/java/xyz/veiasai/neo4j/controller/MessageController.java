package xyz.veiasai.neo4j.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.Message;
import xyz.veiasai.neo4j.result.MessageResult;
import xyz.veiasai.neo4j.result.Result;
import xyz.veiasai.neo4j.service.AuthorService;
import xyz.veiasai.neo4j.service.BuildingAdminService;
import xyz.veiasai.neo4j.service.BuildingService;
import xyz.veiasai.neo4j.service.MessageService;

@Api(value = "message-controller")
@RestController
@RequestMapping("/")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private BuildingAdminService buildingAdminService;

    @ApiOperation(value="发布信息" ,notes="建筑管理员发布信息")
    @PostMapping("/message")
    public MessageResult postMessage(@RequestBody Message message, @RequestParam String buildingId, @RequestParam String authorId){

        MessageResult result= new MessageResult();
        if(buildingService.getBuildingById(buildingId)==null){
            result.setMessage("建筑不存在");
            result.setCode(404);
            return result;
        }
        if(authorService.getAuthorById(authorId)==null){
            result.setMessage("用户不存在");
            result.setCode(405);
            return result;
        }
        if(!buildingAdminService.existBuildingAdmin(buildingId, authorId)){
            result.setMessage("用户无权限发布信息");
            result.setCode(403);
            return result;
        }
        message.setBuilding(buildingService.findById(buildingId));
        message.setAuthor(authorService.findById(authorId));
        message=messageService.addMessage(message);
        result.setSingleMessage(message);
        result.setCode(200);
        result.setMessage("发布信息成功");
        return result;
    }

    @ApiOperation(value="删除信息",notes="建筑管理员删除信息")
    @DeleteMapping("/message")
    public Result deleteMessage(@RequestParam String authorId,@RequestParam String messageId){
        Result result =new Result();
        if(authorService.getAuthorById(authorId)==null){
            result.setMessage("用户不存在");
            result.setCode(405);
            return result;
        }
        if(messageService.getMessageById(messageId)==null){
            result.setMessage("信息不存在");
            result.setCode(404);
            return result;
        }
        if(messageService.existMessageAndAuthor(authorId, messageId)==false){
            result.setMessage("该用户无权限删除此信息");
            result.setCode(403);
            return result;
        }
        messageService.deleteMessage(authorId, messageId);
        result.setMessage("删除信息成功");
        result.setCode(200);
        return result;
    }

    @GetMapping("/message")
    public MessageResult getMessage(@RequestParam(required = false)String buildingId,
                                    @RequestParam(required = false) String authorId,
                                    @RequestParam(required = false,defaultValue = "0")Integer skip,
                                    @RequestParam(required = false,defaultValue = "5")Integer limit){
        MessageResult result =new MessageResult();
        if(buildingId != null && authorId !=null){

        }
        else if(buildingId != null){

        }
        else if(authorId != null){

        }
        else{

        }
        return result;
    }
}

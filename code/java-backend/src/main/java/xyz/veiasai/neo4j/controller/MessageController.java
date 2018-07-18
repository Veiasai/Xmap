package xyz.veiasai.neo4j.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.Message;
import xyz.veiasai.neo4j.result.MessageResult;
import xyz.veiasai.neo4j.service.AuthorService;
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

    @ApiOperation(value="发布信息" ,notes="建筑管理员发布信息")
    @PostMapping("/message")
    public MessageResult postMessage(@RequestBody Message message, @RequestParam String buildingId, @RequestParam String authorId){

        message.setBuilding(buildingService.findById(buildingId));
        message.setAuthor(authorService.findById(authorId));
        message=messageService.addMessage(message);
        MessageResult result= new MessageResult();
        result.setSingleMessage(message);
        return result;
    }
}

package xyz.veiasai.neo4j.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.result.NodeResult;
import xyz.veiasai.neo4j.result.Result;
import xyz.veiasai.neo4j.service.AuthorService;
import xyz.veiasai.neo4j.service.BuildingService;
import xyz.veiasai.neo4j.service.NodeService;

import javax.validation.Valid;
import java.util.*;

@Api(value="node-controller")
@RestController
@RequestMapping("/")
public class NodeController {
    @Autowired
    private NodeService nodeService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private AuthorService authorService;

    @ApiOperation(value = "上传点位", notes="上传点位信息")
    @PostMapping("/node")
    public NodeResult nodePost(@RequestBody @Valid Node node,
                               @RequestParam @ApiParam(name="buildingId", value = "点位所在建筑的id") String buildingId,
                               @RequestParam @ApiParam(name="author", value = "上传者的open-id") String author,
                               BindingResult bindingResult)
    {
        node.setBuilding(buildingService.findById(buildingId));
        node.setAuthor(authorService.findById(author));
        node = nodeService.addNode(node);
        NodeResult result = new NodeResult();
        result.setNode(node);
        return result;
    }

    @GetMapping("/nodes")
    public NodeResult nodeGet(@RequestParam(required = false,defaultValue = "") String name,
                              @RequestParam(required = false) String buildingId,
                              @RequestParam(required = false) String authorId,
                              @RequestParam(required = false) String originId,
                              @RequestParam(required = false) Integer skip,
                              @RequestParam(required = false) Integer limit)
    {
        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 5;
        }
        NodeResult result =new NodeResult();
        if(authorId != null && buildingId !=null){
            result.setNodes(nodeService.findByAuthorAndBuilding(authorId,buildingId,skip,limit));
            return result;
        }
        if(authorId !=null){
            result.setNodes(nodeService.findByAuthorAndName(authorId, name,skip,limit));
            return result;
        }
        if(buildingId !=null){
            result.setNodes(nodeService.findByBuildingAndName(buildingId,name,skip,limit));
            return result;
        }
        if (originId != null){
            result.setNodes(nodeService.findByOriginNode(originId, name,skip,limit));
            return result;
        }
        return result;
    }

    @GetMapping("/nodes/twonodes/v2")
    public Set<Map<String, Object>> pathGetByTwoNodes(@RequestParam String nId1, @RequestParam String nId2,
                                                      @RequestParam(required = false) Integer skip,
                                                      @RequestParam(required = false)Integer limit){
        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 5;
        }
        return nodeService.findAllPathsByTwoNodeId(nId1,nId2,skip,limit);
    }

    @DeleteMapping("/node")
    public Result nodeDeleteById(@RequestParam String authorId,@RequestParam String nodeId){
        Result result = new Result();
        if(authorService.getAuthorById(authorId)==null){
            result.setCode(404);
            result.setMessage("用户不存在");
        }
        else if(nodeService.findById(nodeId)==null){
            result.setCode(404);
            result.setMessage("点位不存在");
        }
        else {
            nodeService.deleteNodeById(authorId, nodeId);
            result.setCode(200);
            result.setMessage("删除点位成功");
        }
        return result;
    }

    /* 废弃接口
    @GetMapping("/nodes/building")
    public NodeResult nodeGetByBuilding(@RequestParam String buildingId,
                                        @RequestParam(required = false,defaultValue = "") String name,
                                        @RequestParam(required = false) Integer skip,
                                        @RequestParam(required = false)Integer limit){

        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 5;
        }
        NodeResult result =new NodeResult();
        result.setNodes(nodeService.findByBuildingAndName(buildingId,name,skip,limit));
        return result;

    }

    @GetMapping("/nodes/twonodes/v1")
    public Set<Map<String, Object>> ShorestpathGetByTwoNodes(@RequestParam String nId1, @RequestParam String nId2,
                                                             @RequestParam(required = false) Integer skip,
                                                             @RequestParam(required = false)Integer limit){
        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 5;
        }
        Set<Map<String, Object>> result = nodeService.findByTwoNodeId(nId1,nId2,skip,limit);
        return result;
    }
    */

}

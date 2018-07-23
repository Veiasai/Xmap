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
import xyz.veiasai.neo4j.service.DataSetService;
import xyz.veiasai.neo4j.service.NodeService;

import javax.validation.Valid;
import java.util.*;

@Api(value = "node-controller")
@RestController
@RequestMapping("/")
public class NodeController {
    @Autowired
    private NodeService nodeService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private DataSetService dataSetService;

    @ApiOperation(value = "上传点位", notes = "上传点位信息")
    @PostMapping("/node")
    public NodeResult nodePost(@RequestBody @Valid Node node,
                               @RequestParam @ApiParam(name = "buildingId", value = "点位所在建筑的id") String buildingId,
                               @RequestParam @ApiParam(name = "author", value = "上传者的open-id") String author,
                               BindingResult bindingResult) {

        NodeResult result = new NodeResult();
        if (buildingService.getBuildingById(buildingId) == null) {
            result.setMessage("建筑不存在");
            result.setCode(404);
        } else if (authorService.getAuthorById(author) == null) {
            result.setMessage("用户不存在");
            result.setCode(405);
        }
        node = nodeService.addNode(node, buildingId, author);
        result.setNode(node);
        return result;
    }

    @GetMapping("/nodes")
    public NodeResult nodeGet(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false) String buildingId,
                              @RequestParam(required = false) String authorId,
                              @RequestParam(required = false) String originId,
                              @RequestParam(required = false) String dataSetId,
                              @RequestParam(required = false, defaultValue = "0") Integer skip,
                              @RequestParam(required = false, defaultValue = "5") Integer limit)
    {
        NodeResult result =new NodeResult();
        result.setCode(200);
        if (dataSetId != null)
        {
            if (dataSetService.getDataSetById(dataSetId) == null) {
                result.setMessage("数据组不存在");
                result.setCode(404);
            }
            else{
                result.setNodes(dataSetService.findNodesByNameLike(dataSetId, name, skip, limit));
                result.setCode(404);
            }
        }
        else if(authorId != null && buildingId !=null){
            result.setNodes(nodeService.findByBuildingAndAuthor(buildingId,authorId,skip,limit));
        }
        else if(authorId !=null){
            result.setNodes(nodeService.findByAuthorAndName(authorId, name,skip,limit));
        }
        else if(buildingId !=null){
            result.setNodes(nodeService.findByBuildingAndName(buildingId,name,skip,limit));
        }
        else if (originId != null){
            result.setNodes(nodeService.findByOriginNode(originId, name,skip,limit));
        }
        else {
            result.setCode(404);
            result.setMessage("查询异常");
        }
        return result;
    }

    @GetMapping("/nodes/twonodes/v2")
    public Set<Map<String, Object>> pathGetByTwoNodes(@RequestParam String nId1, @RequestParam String nId2, //未做nId1，nId2有效性检验
                                                      @RequestParam(required = false, defaultValue = "0") Integer skip,
                                                      @RequestParam(required = false, defaultValue = "5") Integer limit) {
        return nodeService.findAllPathsByTwoNodeId(nId1, nId2, skip, limit);
    }

    @DeleteMapping("/node")
    public Result nodeDeleteById(@RequestParam String authorId, @RequestParam String nodeId) {
        Result result = new Result();
        if (authorService.getAuthorById(authorId) == null) {
            result.setCode(404);
            result.setMessage("用户不存在");
        } else if (nodeService.findById(nodeId) == null) {
            result.setCode(404);
            result.setMessage("点位不存在");
        } else {
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

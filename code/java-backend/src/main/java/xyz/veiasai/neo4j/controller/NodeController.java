package xyz.veiasai.neo4j.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.Building;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.result.NodeResult;
import xyz.veiasai.neo4j.service.AuthorService;
import xyz.veiasai.neo4j.service.BuildingService;
import xyz.veiasai.neo4j.service.NodeService;

import javax.validation.Valid;
import java.util.Collection;

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
    public NodeResult nodeGet(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String building,
                              @RequestParam(required = false) String author,
                              @RequestParam(required = false) String originId,
                              @RequestParam(required = false) String depth)
    {
        NodeResult result =new NodeResult();
        if (originId != null && name != null)
        {
            result.setNodes(nodeService.findByOriginNode(originId, name));
            return result;
        }
        if (building != null){
            result.setNodes(nodeService.findByBuilding(building));
            return result;
        }
        return result;
    }
    @GetMapping("/nodes/author")
    public NodeResult nodeGetByAuthor(@RequestParam String authorId,
                                            @RequestParam(required = false,defaultValue = "")String name,
                                            @RequestParam(required = false) Integer skip,
                                            @RequestParam(required = false)Integer limit) {
        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 100;
        }
        NodeResult result =new NodeResult();
        result.setNodes(nodeService.findByAuthorId(authorId, name,skip,limit));
        return result;
    }
    @GetMapping("/nodes/name")
    public NodeResult nodeGetByName(@RequestParam(required = false,defaultValue = "") String name,
                                          @RequestParam(required = false) Integer skip,
                                          @RequestParam(required = false)Integer limit){

        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 100;
        }
        NodeResult result =new NodeResult();
        result.setNodes(nodeService.findByName(name,skip,limit));
        return result;

    }
}

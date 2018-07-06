package xyz.veiasai.neo4j.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.Building;
import xyz.veiasai.neo4j.domain.Node;
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
    public Node nodePost(@RequestBody @Valid Node node,
                         @RequestParam @ApiParam(name="buildingId", value = "点位所在建筑的id") String buildingId,
                         @RequestParam @ApiParam(name="author", value = "上传者的open-id") String author,
                         BindingResult bindingResult)
    {
        node.setBuilding(buildingService.findById(buildingId));
        node.setAuthor(authorService.findById(author));
        return nodeService.addNode(node);
    }

    @GetMapping("/nodes")
    public Collection<Node> nodeGet(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String building,
                                    @RequestParam(required = false) String author,
                                    @RequestParam(required = false) String originId,
                                    @RequestParam(required = false) String depth)
    {
        if (originId != null && name != null)
        {
            return nodeService.findByOriginNode(originId, name);
        }
        if (building != null)
            return nodeService.findByBuilding(building);
        return null;
    }
    @GetMapping("/nodes/author")
    public Collection<Node> nodeGetByAuthor(@RequestParam String authorId, @RequestParam String name) {
        return nodeService.findByAuthorId(authorId, name);
    }
    @GetMapping("/nodes/name")
    public Collection<Node> nodeGetByName(@RequestParam String name,
                                          @RequestParam Integer skip,
                                          @RequestParam Integer limit){
        return nodeService.findByName(name,skip,limit);
    }
}

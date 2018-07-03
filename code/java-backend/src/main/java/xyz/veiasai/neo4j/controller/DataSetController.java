package xyz.veiasai.neo4j.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.DataSet;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;
import xyz.veiasai.neo4j.result.NodeResult;
import xyz.veiasai.neo4j.result.PathResult;
import xyz.veiasai.neo4j.result.Result;
import xyz.veiasai.neo4j.service.DataSetService;

import javax.validation.Valid;
import java.util.Collection;

@Api(value = "dataset-controller")
@RestController
@RequestMapping("/")
public class DataSetController {
    @Autowired
    private DataSetService dataSetService;

    @ApiOperation(value = "上传数据组", notes="上传数据组信息")
    @PostMapping("/dataset")
    public DataSet postDataSet(@RequestBody @Valid DataSet dataSet,
                               @RequestParam @ApiParam(name="buildingId", value = "数据组所在建筑物的id")String buildingId,
                               @RequestParam @ApiParam(name="authorId", value = "上传者的open-id")String authorId){
        dataSet = dataSetService.addDataSet(dataSet,authorId,buildingId);
        dataSetService.addRelationNodes(dataSet.getId(),dataSet.getNodes());
        return dataSet;
    }
    @ApiOperation(value="查找某个点位",notes="查找数据组中某个点位")
    @GetMapping("/dataset/searchnode")
    public Result searchNode(@RequestParam String dataSetId,
                             @RequestParam @ApiParam(name="nodeId",value="查找所需的点位名称")String nodeName){
        NodeResult result = new NodeResult();
        Node node = dataSetService.searchNode(dataSetId,nodeName);
        result.setNode(node);
        return result;
    }

    @ApiOperation(value = "查找所有点位",notes="查找数据组中所有点位")
    @GetMapping("/dataset/searchallnodes")
    public Result searchNode(@RequestParam String dataSetId){
        NodeResult result =new NodeResult();
        Collection<Node> Nodes = dataSetService.searchAllNodes(dataSetId);
        result.setNodes(Nodes);
        return result;
    }

    @ApiOperation(value="查找某个路线",notes="查找数据组中某个路线")
    @GetMapping("/dataset/searchpath")
    public Result searchPath(@RequestParam String dataSetId,@RequestParam String pathName){
        PathResult result = new PathResult();
        Path path = dataSetService.searchPath(dataSetId,pathName);
        result.setPath(path);
        return result;
    }

    @ApiOperation(value="查找所有路线",notes="查找数据组所有路线")
    @GetMapping("/dataset/searchallpaths")
    public Result searchAllPath(@RequestParam String dataSetId){
        PathResult result = new PathResult();
        Collection<Path> Paths = dataSetService.searchAllPaths(dataSetId);
        result.setPaths(Paths);
        return result;
    }

    @ApiOperation(value = "增加点位",notes="批量在数据组中增加点位")
    @PostMapping("/dataset/addnodes")
    public Result addNodes(@RequestParam Collection<Node>Nodes,@RequestParam String dataSetId){
        Result result =new Result();
        dataSetService.addRelationNodes(dataSetId,Nodes);
        return result;
    }

    @ApiOperation(value = "删除点位",notes = "删除数据组中点位")
    @PostMapping("/dataset/deletenodes")
    public Result deleteNodes(@RequestParam String dataSetId,@RequestParam Collection<Node>Nodes){
        Result result =new Result();
        dataSetService.deleteRelationNodes(dataSetId,Nodes);
        return result;
    }



}

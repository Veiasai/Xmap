package xyz.veiasai.neo4j.controller;

import com.sun.org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper;
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
import java.util.List;

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
                               @RequestParam @ApiParam(name="authorId", value = "上传者的open-id")String authorId,
                               @RequestParam @ApiParam(name="type",value="数据组的类型")String type){
        dataSet = dataSetService.addDataSet(dataSet,buildingId,authorId,type);
        return dataSet;
    }
    @ApiOperation(value = "删除数据组",notes="删除数据组及其相关联系")
    @DeleteMapping("/deletedataset")
    public Result deleteDataSet(@RequestParam String dataSetId){
        Result result = new Result();
        if(dataSetService.getDataSet(dataSetId)==null){
            result.setMessage("数据组不存在");
            return result;
        }
        dataSetService.deleteDataSet(dataSetId);
        result.setMessage("删除数据组成功");
        return result;
    }
    @ApiOperation(value="查找数据组",notes="查找某名字的数据组")
    @GetMapping("/getdataset")
    public Collection<DataSet> getDataSets(@RequestParam String dataSetName){
        return dataSetService.findDataSetNameLike(dataSetName);
    }
    @ApiOperation(value="模糊查询点位/路线",notes="查找数据组中包含某名字的点位/路线")
    @GetMapping("/dataset/some")
    public Result searchNode(@RequestParam String dataSetId,
                             @RequestParam @ApiParam(name="Name",value="查找所需的点位/名称")String Name){
        DataSet dataSet = dataSetService.getDataSet(dataSetId);
        if(dataSet==null){
            Result result = new NodeResult();
            result.setMessage("数据组不存在");
            return result;
        }
        if(dataSet.getType().equals("node")){
            NodeResult result = new NodeResult();
            Collection<Node> Nodes = dataSetService.searchNodesByNameLike(dataSetId,Name);
            result.setMessage("查找点位成功");
            result.setNodes(Nodes);
            return result;
        }
        PathResult result = new PathResult();
        Collection<Path> Paths = dataSetService.searchPathByNameLike(dataSetId,Name);
        result.setMessage("查找路线成功");
        result.setPaths(Paths);
        return result;
    }

    @ApiOperation(value = "查找所有点位/路线",notes="查找数据组中所有点位/路线")
    @GetMapping("/dataset/all")
    public Result searchNode(@RequestParam String dataSetId){
        DataSet dataSet =dataSetService.getDataSet(dataSetId);
        if(dataSet==null){
            Result result =new NodeResult();
            result.setMessage("数据组不存在");
            return result;
        }
        if(dataSet.getType().equals("node")){
            NodeResult result =new NodeResult();
            Collection<Node> Nodes = dataSetService.searchAllNodes(dataSetId);
            result.setMessage("查找点位成功");
            result.setNodes(Nodes);
            return result;
        }
        PathResult result =new PathResult();
        Collection<Path> Paths = dataSetService.searchAllPaths(dataSetId);
        result.setMessage("查找路线成功");
        result.setPaths(Paths);
        return result;
    }

    @ApiOperation(value = "增加点位/路线",notes="批量增加数据组中点位/路线")
    @PostMapping("/dataset/add")
    public Result addNodes(@RequestParam String dataSetId,@RequestBody @ApiParam(name="NodeIds",value="增加所需的点位/路线id List")List<String>NodeIds){
        Result result =new Result();
        DataSet dataSet = dataSetService.getDataSet(dataSetId);
        if(dataSet==null){
            result.setMessage("数据组不存在");
            return result;
        }
        if(dataSet.getType().equals("node")) {
            result.setMessage("添加成功");
            dataSetService.addRelationNodes(dataSetId, NodeIds);
            return result;
        }
        if(dataSet.getType().equals("path")) {
            result.setMessage("添加成功");
            dataSetService.addRelationPaths(dataSetId, NodeIds);
            return result;
        }
        return result;
    }

    @ApiOperation(value = "删除点位/路线",notes = "批量删除数据组中点位/路线")
    @PutMapping("/dataset")
    public Result deleteNodes(@RequestParam String dataSetId,@RequestBody @ApiParam(name="NodeIds",value="删除所需的点位id List") List<String>Ids){
        Result result =new Result();
        DataSet dataSet = dataSetService.getDataSet(dataSetId);
        if(dataSet==null){
            result.setMessage("数据组不存在");
            return result;
        }
        if(dataSet.getType().equals("node")) {
            result.setMessage("删除点位成功");
            dataSetService.deleteRelationNodes(dataSetId, Ids);
            return result;
        }
        if(dataSet.getType().equals("path")) {
            result.setMessage("删除路线成功");
            dataSetService.deleteRelationPaths(dataSetId, Ids);
            return result;
        }
        return result;
    }



}

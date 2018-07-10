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
import xyz.veiasai.neo4j.result.DataSetResult;
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
    @ApiOperation(value = "删除数据组",notes="删除数据组及其相关联系;\r\n404:不存在;\r\n200:删除成功")
    @DeleteMapping("/dataset")
    public Result deleteDataSet(@RequestParam String dataSetId){
        Result result = new Result();
        if(dataSetService.getDataSet(dataSetId)==null){
            result.setMessage("数据组不存在");
            result.setCode(404);
            return result;
        }
        dataSetService.deleteDataSet(dataSetId);
        result.setMessage("删除数据组成功");
        result.setCode(200);
        return result;
    }
    @ApiOperation(value="查找数据组",notes="根据建筑、作者、名称查找的数据组")
    @GetMapping("/dataset")
    public DataSetResult getDataSets(@RequestParam(required = false) String buildingId,
                                     @RequestParam(required = false) String authorId,
                                     @RequestParam(required = false) String dataSetName,
                                     @RequestParam(required = false) Integer skip,
                                     @RequestParam(required = false) Integer limit){
        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 5;
        }
        DataSetResult result =new DataSetResult();
        if(buildingId != null && authorId !=null){
            result.setCode(200);
            result.setMessage("查询成功");
            result.setDataSets(dataSetService.findDataSetByBuildingAndAuthor(buildingId,authorId,skip,limit));
        }
        if(buildingId != null && dataSetName !=null) {
            result.setCode(200);
            result.setMessage("查询成功");
            result.setDataSets(dataSetService.findDataSetByBuildingAndName(buildingId, dataSetName, skip, limit));
            return result;
        }
        if(authorId != null && dataSetName !=null){
            result.setCode(200);
            result.setMessage("查询成功");
            result.setDataSets(dataSetService.findDataSetByAuthorAndName(authorId,dataSetName,skip,limit));
            return result;
        }
        result.setCode(404);
        return result;
    }
    @ApiOperation(value="模糊查询点位/路线",notes="查找数据组中包含某名字的点位/路线;" +
            "通过Name设默认值为空的字符串，可以查询所有;" +
            "skip 0 limit 100;\r\n404:不存在;\r\n200:删除成功")
    @GetMapping("/dataset/some")
    public Result searchNodeOrPath(@RequestParam String dataSetId,
                             @RequestParam(required = false,defaultValue = "") @ApiParam(name="Name",value="查找所需的点位/名称")String Name,
                             @RequestParam(required = false)Integer skip,
                             @RequestParam(required = false)Integer limit){

        DataSet dataSet = dataSetService.getDataSet(dataSetId);
        if(dataSet==null){
            Result result = new Result();
            result.setMessage("数据组不存在");
            result.setCode(404);
            return result;
        }
        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 5;
        }
        if(dataSet.getType().equals("node")){
            NodeResult result = new NodeResult();
            Collection<Node> Nodes = dataSetService.searchNodesByNameLike(dataSetId,Name,skip,limit);
            result.setCode(200);
            result.setMessage("查找点位成功");
            result.setNodes(Nodes);
            return result;
        }
        PathResult result = new PathResult();
        Collection<Path> Paths = dataSetService.searchPathByNameLike(dataSetId,Name,skip,limit);
        result.setCode(200);
        result.setMessage("查找路线成功");
        result.setPaths(Paths);
        return result;
    }

    /*
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
    }*/

    @ApiOperation(value = "增加点位/路线",notes="批量增加数据组中点位/路线;\r\n404:不存在;\r\n200:添加成功")
    @PostMapping("/dataset/add")
    public Result addNodes(@RequestParam String dataSetId,@RequestBody @ApiParam(name="NodeIds",value="增加所需的点位/路线id List")List<String>NodeIds){
        Result result =new Result();
        DataSet dataSet = dataSetService.getDataSet(dataSetId);
        if(dataSet==null){
            result.setMessage("数据组不存在");
            result.setCode(404);
            return result;
        }
        if(dataSet.getType().equals("node")) {
            result.setMessage("添加成功");
            result.setCode(200);
            dataSetService.addRelationNodes(dataSetId, NodeIds);
            return result;
        }
        if(dataSet.getType().equals("path")) {
            result.setMessage("添加成功");
            result.setCode(200);
            dataSetService.addRelationPaths(dataSetId, NodeIds);
            return result;
        }
        return result;
    }

    @ApiOperation(value = "删除点位/路线",notes = "批量删除数据组中点位/路线;\r\n404:不存在;\r\n200:删除成功")
    @PutMapping("/dataset")
    public Result deleteNodes(@RequestParam String dataSetId,@RequestBody @ApiParam(name="NodeIds",value="删除所需的点位id List") List<String>Ids){
        Result result =new Result();
        DataSet dataSet = dataSetService.getDataSet(dataSetId);
        if(dataSet==null){
            result.setMessage("数据组不存在");
            result.setCode(404);
            return result;
        }
        if(dataSet.getType().equals("node")) {
            result.setMessage("删除点位成功");
            result.setCode(200);
            dataSetService.deleteRelationNodes(dataSetId, Ids);
            return result;
        }
        if(dataSet.getType().equals("path")) {
            result.setMessage("删除路线成功");
            result.setCode(200);
            dataSetService.deleteRelationPaths(dataSetId, Ids);
            return result;
        }
        return result;
    }



}

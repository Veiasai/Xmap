package xyz.veiasai.neo4j.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.DataSet;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;
import xyz.veiasai.neo4j.result.BuildingAdminResult;
import xyz.veiasai.neo4j.result.BuildingResult;
import xyz.veiasai.neo4j.result.CountSumResult;
import xyz.veiasai.neo4j.result.Result;
import xyz.veiasai.neo4j.service.*;

import java.util.List;

@Api(value = "buildingAdmin-controller")
@RestController
@RequestMapping("/")
public class BuildingAdminController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private BuildingAdminService buildingAdminService;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private PathService pathService;

    @Autowired
    private DataSetService dataSetService;

    @PostMapping("/building/admin/login")
    public Result loginBuildingAdmin(@RequestParam String authorId) {
        Result result = new Result();
        if (buildingAdminService.isBuildingAdmin(authorId)) {
            result.setCode(200);
            result.setMessage("登录成功");
        } else {
            result.setCode(403);
            result.setMessage("不是建筑管理员");       //authorId是否存在不应该判断。存在可能泄密
        }
        return result;
    }

    @GetMapping("/building/admin/admin")       //to be continued
    public BuildingAdminResult getAdminByBuildingId(@RequestParam String buildingId) {
        BuildingAdminResult result = new BuildingAdminResult();
        if (buildingService.getBuildingById(buildingId) == null) {
            result.setMessage("建筑不存在");
            result.setCode(404);
        } else {
            result.setBuildingAdmins(buildingAdminService.findAdminByBuildingId(buildingId));
            result.setCode(200);
            result.setMessage("查询成功");      //进一步判断查询结果是否为空 to be continued
        }
        return result;
    }

    @DeleteMapping("building/admin/node")
    public Result deleteNodeByAdmin(@RequestParam String buildingId, @RequestParam String adminId, @RequestParam String nodeId) {
        Result result = new Result();
        if (buildingService.getBuildingById(buildingId) == null) {
            result.setCode(404);
            result.setMessage("建筑不存在");
        } else if (authorService.getAuthorById(adminId) == null) {
            result.setCode(404);
            result.setMessage("用户不存在");
        } else if (nodeService.findById(nodeId) == null) {
            result.setCode(404);
            result.setMessage("点位不存在");
        } else if (!buildingAdminService.existValidBuildingAdmin(buildingId, adminId)) {
            result.setCode(403);
            result.setMessage("该用户不是该建筑管理员");
        } else if (!nodeService.existBuildingAndNode(buildingId, nodeId)) {
            result.setCode(403);
            result.setMessage("该点位不属于该建筑");
        } else {
            result.setCode(200);
            result.setMessage("删除成功");
            nodeService.deleteNodeByAdmin(nodeId);
        }
        return result;
    }

    @PutMapping("building/admin/node")
    public Result updateNodeByAdmin(@RequestParam String buildingId, @RequestParam String adminId, @RequestBody Node node) {
        Result result = new Result();
        if (buildingService.getBuildingById(buildingId) == null) {
            result.setCode(404);
            result.setMessage("建筑不存在");
        } else if (authorService.getAuthorById(adminId) == null) {
            result.setCode(404);
            result.setMessage("用户不存在");
        } else if (nodeService.findById(node.getId()) == null) {
            result.setCode(404);
            result.setMessage("点位不存在");
        } else if (!buildingAdminService.existValidBuildingAdmin(buildingId, adminId)) {
            result.setCode(403);
            result.setMessage("该用户不是该建筑管理员");
        } else if (!nodeService.existBuildingAndNode(buildingId, node.getId())) {
            result.setCode(403);
            result.setMessage("该点位不属于该建筑");
        } else {
            nodeService.updateNodeByAdmin(node);
            result.setCode(200);
            result.setMessage("更新成功");
        }
        return result;
    }

    @DeleteMapping("building/admin/path")
    public Result deletePathByAdmin(@RequestParam String buildingId, @RequestParam String adminId, @RequestParam String pathId) {
        Result result = new Result();
        if (buildingService.getBuildingById(buildingId) == null) {
            result.setCode(404);
            result.setMessage("建筑不存在");
        } else if (authorService.getAuthorById(adminId) == null) {
            result.setCode(404);
            result.setMessage("用户不存在");
        } else if (pathService.findById(pathId) == null) {
            result.setCode(404);
            result.setMessage("路线不存在");
        } else if (!buildingAdminService.existValidBuildingAdmin(buildingId, adminId)) {
            result.setCode(403);
            result.setMessage("该用户不是该建筑管理员");
        } else if (!pathService.existBuildingAndPath(buildingId, pathId)) {
            result.setCode(403);
            result.setMessage("该路线不属于该建筑");
        } else {
            result.setCode(200);
            result.setMessage("删除成功");
            pathService.deletePathByAdmin(pathId);
        }
        return result;
    }

    @PutMapping("building/admin/path")
    public Result updatePathByAdmin(@RequestParam String buildingId, @RequestParam String adminId, @RequestBody Path path) {
        Result result = new Result();
        if (buildingService.getBuildingById(buildingId) == null) {
            result.setCode(404);
            result.setMessage("建筑不存在");
        } else if (authorService.getAuthorById(adminId) == null) {
            result.setCode(404);
            result.setMessage("用户不存在");
        } else if (pathService.findById(path.getId()) == null) {
            result.setCode(404);
            result.setMessage("路线不存在");
        } else if (!buildingAdminService.existValidBuildingAdmin(buildingId, adminId)) {
            result.setCode(403);
            result.setMessage("该用户不是该建筑管理员");
        } else if (!pathService.existBuildingAndPath(buildingId, path.getId())) {
            result.setCode(403);
            result.setMessage("该路线不属于该建筑");
        } else {
            result.setCode(200);
            result.setMessage("更新成功");
            pathService.updatePathByAdmin(path);
        }
        return result;
    }

    @DeleteMapping("building/admin/dataset")
    public Result deleteDataSetByAdmin(@RequestParam String buildingId, @RequestParam String adminId, @RequestParam String dataSetId) {
        Result result = new Result();
        if (buildingService.getBuildingById(buildingId) == null) {
            result.setCode(404);
            result.setMessage("建筑不存在");
        } else if (authorService.getAuthorById(adminId) == null) {
            result.setCode(404);
            result.setMessage("用户不存在");
        } else if (dataSetService.findById(dataSetId) == null) {
            result.setCode(404);
            result.setMessage("数据组不存在");
        } else if (!buildingAdminService.existValidBuildingAdmin(buildingId, adminId)) {
            result.setCode(403);
            result.setMessage("该用户不是该建筑管理员");
        } else if (dataSetService.existBuildingAndDataSet(buildingId, dataSetId)) {
            result.setCode(403);
            result.setMessage("该数据组不属于该建筑");
        } else {
            result.setCode(200);
            result.setMessage("删除成功");
            dataSetService.deleteDataSetByAdmin(dataSetId);
        }
        return result;
    }

    @PutMapping("building/admin/dataset")
    public Result updateDataSetByadmin(@RequestParam String buildingId, @RequestParam String adminId, @RequestParam String dataSetId, @RequestParam List<String> Ids) {
        Result result = new Result();
        if (buildingService.getBuildingById(buildingId) == null) {
            result.setCode(404);
            result.setMessage("建筑不存在");
        } else if (authorService.getAuthorById(adminId) == null) {
            result.setCode(404);
            result.setMessage("用户不存在");
        } else if (dataSetService.findById(dataSetId) == null) {
            result.setCode(404);
            result.setMessage("数据组不存在");
        } else if (!buildingAdminService.existValidBuildingAdmin(buildingId, adminId)) {
            result.setCode(403);
            result.setMessage("该用户不是该建筑管理员");
        } else if (dataSetService.existBuildingAndDataSet(buildingId, dataSetId)) {
            result.setCode(403);
            result.setMessage("该数据组不属于该建筑");
        } else {
            result.setCode(200);
            result.setMessage("更新成功");
        }
        return result;
    }

    @DeleteMapping("building/admin/dataset/detail")
    public Result deleteDataSetNodeByAdmin(@RequestParam String buildingId, @RequestParam String adminId, @RequestParam String dataSetId, @RequestParam String Id) {
        Result result = new Result();
        DataSet dataSet = dataSetService.findById(dataSetId);
        if (buildingService.getBuildingById(buildingId) == null) {
            result.setCode(404);
            result.setMessage("建筑不存在");
        } else if (authorService.getAuthorById(adminId) == null) {
            result.setCode(404);
            result.setMessage("用户不存在");
        } else if (dataSet==null) {
            result.setCode(404);
            result.setMessage("数据组不存在");
        } else if (!buildingAdminService.existValidBuildingAdmin(buildingId, adminId)) {
            result.setCode(403);
            result.setMessage("该用户不是该建筑管理员");
        } else if (dataSetService.existBuildingAndDataSet(buildingId, dataSetId)) {
            result.setCode(403);
            result.setMessage("该数据组不属于该建筑");
        } else if(dataSet.getType().equals("node")) {
            dataSetService.deleteRelationNode(dataSetId,Id);
            result.setCode(200);
            result.setMessage("删除点位成功");
        }
        else{
            dataSetService.deleteRelationPath(dataSetId,Id);
            result.setCode(200);
            result.setMessage("删除路线成功");
        }
        return result;
    }

    @GetMapping("/building/admin/building")    //to be continued
    public BuildingResult getBuildingByAdminId(@RequestParam String adminId) {
        BuildingResult result = new BuildingResult();
        if (authorService.getAuthorById(adminId) == null) {
            result.setMessage("用户不存在");
            result.setCode(405);
        } else {
            result.setBuildings(buildingAdminService.findBuildingByAdmin(adminId));
            result.setCode(200);
            result.setMessage("查询成功");
        }
        return result;
    }

    @GetMapping("building/admin/buildingandcount")
    public CountSumResult getBuildingAndCountByAdminId(@RequestParam String adminId) {
        CountSumResult result = new CountSumResult();
        if (authorService.getAuthorById(adminId) == null) {
            result.setMessage("用户不存在");
            result.setCode(405);
        } else {
            result.setCountSums(buildingAdminService.findBuildingAndCountByAdmin(adminId));
            result.setCode(200);
            result.setMessage("查询成功");
        }
        return result;
    }
}

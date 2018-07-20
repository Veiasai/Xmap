package xyz.veiasai.neo4j.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.result.BuildingAdminResult;
import xyz.veiasai.neo4j.result.BuildingResult;
import xyz.veiasai.neo4j.result.Result;
import xyz.veiasai.neo4j.service.AuthorService;
import xyz.veiasai.neo4j.service.BuildingAdminService;
import xyz.veiasai.neo4j.service.BuildingService;

@Api(value = "buildingAdmin-controller")
@RestController
@RequestMapping("/")
public class BuildingAdminController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BuildingService buildingService;

    @Autowired BuildingAdminService buildingAdminService;

    @PostMapping("/buildingadmin")
    public Result postBuildingAdmin(@RequestParam String buildingId,@RequestParam String authorId){
        Result result =new Result();
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
        buildingAdminService.addBuildingAdmin(buildingId, authorId);
        result.setCode(200);
        result.setMessage("设置建筑管理员成功");
        return result;
    }

    @DeleteMapping("/buildingadmin")
    public Result deleteBuildingAdmin(@RequestParam String buildingId,@RequestParam String authorId){
        Result result = new Result();
        if(buildingService.getBuildingById(buildingId)==null){
            result.setMessage("建筑不存在");
            result.setCode(404);
        }
        else if(authorService.getAuthorById(authorId)==null){
            result.setMessage("用户不存在");
            result.setCode(405);
        }
        else if(!buildingAdminService.existBuildingAdmin(buildingId, authorId)){
            result.setMessage("该用户不是此建筑的管理员");
            result.setCode(403);
        }
        else{
            buildingAdminService.deleteBuildingAdmin(buildingId, authorId);
            result.setCode(200);
            result.setMessage("删除建筑管理员成功");
        }
        return result;
    }

    @GetMapping("/admin")
    public BuildingAdminResult getAdminByBuildingId(@RequestParam String buildingId) {
        BuildingAdminResult result = new BuildingAdminResult();
        if(buildingService.getBuildingById(buildingId)==null){
            result.setMessage("建筑不存在");
            result.setCode(404);
            return result;
        }
        result.setBuildingAdmins(buildingAdminService.findAdminByBuildingId(buildingId));
        result.setCode(200);
        result.setMessage("查询成功");      //进一步判断查询结果是否为空 to be continued
        return result;
    }

    @GetMapping("/building")
    public BuildingResult getBuildingByAdminId(@RequestParam String adminId){
        BuildingResult result =new BuildingResult();
        if(authorService.getAuthorById(adminId)==null){
            result.setMessage("用户不存在");
            result.setCode(405);
            return result;
        }
        result.setBuildings(buildingAdminService.findBuildingByAdmin(adminId));
        result.setCode(200);
        result.setMessage("查询成功");
        return result;
    }
}

package xyz.xmap.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.xmap.result.ApplyResult;
import xyz.xmap.result.Result;
import xyz.xmap.service.AuthorService;
import xyz.xmap.service.BuildingAdminService;
import xyz.xmap.service.BuildingService;

@Api(value = "system-admin-controller")
@RestController
@RequestMapping("/")
public class SystemAdminController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    BuildingAdminService buildingAdminService;


    @PostMapping("/systemadmin/handleapply")
    public Result handleApply(@RequestParam String buildingId, @RequestParam String authorId, @RequestParam(required=false, defaultValue = "") String _sign, @RequestParam boolean refuse) {
        Result result = new Result();
        if (!_sign.equals("123456")) {
            result.setMessage("无权限访问");
            result.setCode(403);
        } else if (buildingService.getBuildingById(buildingId) == null) {
            result.setMessage("建筑不存在");
            result.setCode(404);
        } else if (authorService.getAuthorById(authorId) == null) {
            result.setMessage("用户不存在");
            result.setCode(404);
        } else if (buildingAdminService.existValidBuildingAdmin(buildingId, authorId)) {
            result.setMessage("该用户已为该建筑管理员");
            result.setCode(405);
        } else if (!buildingAdminService.existApplyBuildingAdmin(buildingId, authorId)) {
            result.setMessage("此用户未申请该建筑管理员");
            result.setCode(406);
        } else if (refuse) {
            buildingAdminService.refuseBuildingAdmin(buildingId, authorId);
            result.setCode(201);
            result.setMessage("拒绝成功");
        } else {
            buildingAdminService.setBuildingAdmin(buildingId, authorId);
            
            result.setCode(200);
            result.setMessage("接受申请");
        }
        return result;
    }

    @DeleteMapping("/systemadmin/buildingadmin")
    public Result deleteBuildingAdmin(@RequestParam String buildingId, @RequestParam String authorId, @RequestParam(required=false, defaultValue = "") String _sign) {
        Result result = new Result();
        if (!_sign.equals("123456")) {
            result.setMessage("无权限访问");
            result.setCode(403);
        } else if (buildingService.getBuildingById(buildingId) == null) {
            result.setMessage("建筑不存在");
            result.setCode(404);
        } else if (authorService.getAuthorById(authorId) == null) {
            result.setMessage("用户不存在");
            result.setCode(404);
        } else if (!buildingAdminService.existValidBuildingAdmin(buildingId, authorId)) {
            result.setMessage("该用户不是此建筑的管理员");
            result.setCode(405);
        } else {
            buildingAdminService.deleteBuildingAdmin(buildingId, authorId);
            result.setCode(200);
            result.setMessage("删除建筑管理员成功");
        }
        return result;
    }

    @GetMapping("/systemadmin/apply")
    public ApplyResult getBuildingAdminApply(@RequestParam(required = false) String BuildingId, @RequestParam(required=false, defaultValue = "0") int skip, @RequestParam(required=false, defaultValue = "5") int limit, @RequestParam(required=false, defaultValue = "") String _sign){
        ApplyResult result = new ApplyResult();
        if (!_sign.equals("123456")){
            result.setMessage("无权限访问");
            result.setCode(403);
        } else {
            result.setApply(buildingAdminService.getApply(BuildingId ,skip, limit));
            result.setCode(200);
        }
        return result;
    }
}

package xyz.veiasai.neo4j.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.DataSet;
import xyz.veiasai.neo4j.result.*;
import xyz.veiasai.neo4j.service.AuthorService;
import xyz.veiasai.neo4j.service.BuildingAdminService;
import xyz.veiasai.neo4j.service.BuildingService;

import javax.validation.Valid;

@Api(value = "author-controller")
@RestController
@RequestMapping("/")
public class AuthorController {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BuildingAdminService buildingAdminService;

    @ApiOperation(value = "申请建筑管理员",notes = "申请成为建筑管理员")
    @GetMapping("/apply/buildingadmin")
    public Result applyBuildingAdmin(@RequestParam String buildingId,@RequestParam String authorId){
        Result result =new Result();
        if (buildingService.getBuildingById(buildingId) == null) {
            result.setMessage("建筑不存在");
            result.setCode(404);
        } else if (authorService.getAuthorById(authorId) == null) {
            result.setMessage("用户不存在");
            result.setCode(404);
        }else if(buildingAdminService.existValidBuildingAdmin(buildingId, authorId)){
            result.setMessage("该用户已为该建筑管理员");
            result.setCode(405);
        }else if(buildingAdminService.existApplyBuildingAdmin(buildingId, authorId)){
            result.setMessage("该用户已有待审核的申请");
            result.setCode(403);
        }else {
            buildingAdminService.applyBuildingAdmin(buildingId, authorId);
            result.setMessage("申请成功");
            result.setCode(200);
        }
        return result;
    }
    @GetMapping("/check/buildingadmin")
    public Result checkBuildingAdmin(@RequestParam String buildingId, @RequestParam String authorId){
        Result result = new Result();if (buildingService.getBuildingById(buildingId) == null) {
            result.setMessage("建筑不存在");
            result.setCode(404);
        } else if (authorService.getAuthorById(authorId) == null) {
            result.setMessage("用户不存在");
            result.setCode(404);
        }else if(buildingAdminService.existValidBuildingAdmin(buildingId, authorId)){
            result.setMessage("该用户已为该建筑管理员");
            result.setCode(405);
        }else if(buildingAdminService.existApplyBuildingAdmin(buildingId, authorId)){
            result.setMessage("该用户已有待审核的申请");
            result.setCode(403);
        }else {
            result.setMessage("尚未申请");
            result.setCode(200);
        }
        return result;
    }
    
    @ApiOperation(value = "收藏", notes = "通过favoriteId收藏（点位，路线，用户，建筑,数据组等;\r\n404:不存在;\r\n200:删除成功")
    @PostMapping("/favorite")
    public Result postFavorite(@RequestParam @ApiParam(name = "authorId", value = "用户的Id") String authorId,
                               @RequestParam @ApiParam(name = "favoriteId", value = "要收藏事物的Id") String favoriteId) {
        Result result = new Result();
        if (authorService.getAuthorById(authorId) == null) {
            result.setMessage("用户不存在");
            result.setCode(404);
        } else if (!authorService.FavoriteIsExistInDb(favoriteId)) {
            result.setMessage("收藏不存在");
            result.setCode(403);
        } else {
            authorService.addFavorite(authorId, favoriteId);
            result.setMessage("收藏成功");
            result.setCode(200);
        }
        return result;
    }

    @ApiOperation(value = "取消收藏", notes = "通过favoriteId取消收藏（点位，路线，用户，建筑,数据组等;\r\n404:不存在;\r\n200:删除成功")
    @DeleteMapping("/favorite")
    public Result cancelFavorite(@RequestParam @ApiParam(name = "authorId", value = "用户的Id") String authorId,
                                 @RequestParam @ApiParam(name = "favoriteId", value = "要取消收藏事物的Id") String favoriteId) {
        Result result = new Result();
        if (authorService.getAuthorById(authorId) == null) {
            result.setMessage("用户不存在");
            result.setCode(404);
        } else if (!authorService.FavoriteIsExistInDb(favoriteId)) {
            result.setMessage("收藏不存在");
            result.setCode(403);
        } else {
            authorService.deleteFavorite(authorId, favoriteId);
            result.setMessage("取消收藏成功");
            result.setCode(200);
        }
        return result;
    }

    @ApiOperation(value = "判断用户是否收藏", notes = "如果authorId和favoriteId无效也会返回false")
    @GetMapping("/favorexist")
    public Result favorIsexist(String authorId, String favoriteId) {
        Result result = new Result();
        if (authorService.getAuthorById(authorId) == null) {
            result.setMessage("用户不存在");
            result.setCode(404);
        } else if (!authorService.FavoriteIsExistInDb(favoriteId)) {
            result.setMessage("收藏不存在");
            result.setCode(404);
        } else if (authorService.FavoriteIsExistInAuthor(authorId, favoriteId)) {
            result.setMessage("1");
            result.setCode(200);        //200代表查询成功
        } else {
            result.setMessage("0");
            result.setCode(200);
        }
        return result;
    }

    @ApiOperation(value = "查询收藏中所有", notes = "通过名字模糊查询所有收藏相关")      //如何判断查询结果是否为空。to be continued
    @GetMapping("/favorite/some")
    public FavoriteResult getFavorite(@RequestParam @ApiParam(name = "authorId", value = "用户的Id") String authorId,
                                      @RequestParam(required = false, defaultValue = "") @ApiParam(name = "favoriteName", value = "收藏事物的名称") String favoriteName,
                                      @RequestParam(required = false, defaultValue = "0") @ApiParam(name = "skip", value = "指定查询收藏偏移量") Integer skip,
                                      @RequestParam(required = false, defaultValue = "5") @ApiParam(name = "limit", value = "指定查询收藏结果数") Integer limit) {
        FavoriteResult result = new FavoriteResult();
        if (authorService.getAuthorById(authorId) == null) {
            result.setMessage("用户不存在");
            result.setCode(404);
        }
        result = authorService.findFavoriteByNameLike(authorId, favoriteName, skip, limit);
        return result;
    }

    @ApiOperation(value = "查询收藏中点位", notes = "通过名字模糊查询收藏里的点位")
    @GetMapping("/favorite/nodes")
    public NodeResult getFavoriteNodes(@RequestParam @ApiParam(name = "authorId", value = "用户的Id") String authorId,
                                       @RequestParam(required = false, defaultValue = "") @ApiParam(name = "nodeName", value = "收藏事物的名称") String nodeName,
                                       @RequestParam(required = false, defaultValue = "0") @ApiParam(name = "skip", value = "指定查询收藏偏移量") Integer skip,
                                       @RequestParam(required = false, defaultValue = "5") @ApiParam(name = "limit", value = "指定查询收藏结果数") Integer limit) {

        return authorService.findfavorNodeByNameLike(authorId, nodeName, skip, limit);
    }

    @ApiOperation(value = "查询收藏中路线", notes = "通过名字模糊查询收藏里的路线")
    @GetMapping("/favorite/path")
    public PathResult getFavoritePaths(@RequestParam @ApiParam(name = "authorId", value = "用户的Id") String authorId,
                                       @RequestParam(required = false, defaultValue = "") @ApiParam(name = "pathName", value = "收藏事物的名称") String pathName,
                                       @RequestParam(required = false, defaultValue = "0") @ApiParam(name = "skip", value = "指定查询收藏偏移量") Integer skip,
                                       @RequestParam(required = false, defaultValue = "5") @ApiParam(name = "limit", value = "指定查询收藏结果数") Integer limit) {

        return authorService.findfavorPathByNameLike(authorId, pathName, skip, limit);
    }

    @ApiOperation(value = "查询收藏中数据组", notes = "通过名字模糊查询收藏的数据组")
    @GetMapping("/favorite/dataset")
    public DataSetResult getFavoriteDataSets(@RequestParam @ApiParam(name = "authorId", value = "用户的Id") String authorId,
                                             @RequestParam(required = false, defaultValue = "") @ApiParam(name = "dataSetName", value = "收藏事物的名称") String dataSetName,
                                             @RequestParam(required = false, defaultValue = "0") @ApiParam(name = "skip", value = "指定查询收藏偏移量") Integer skip,
                                             @RequestParam(required = false, defaultValue = "5") @ApiParam(name = "limit", value = "指定查询收藏结果数") Integer limit) {

        return authorService.findfavorDataSetByNameLike(authorId, dataSetName, skip, limit);
    }

    /* 废弃接口
    @ApiOperation(value = "保存点位制作者" )
    @PostMapping("/author")
    public Author postAuthor(@RequestBody @Valid Author author, BindingResult bindingResult)
    {
        return authorService.addAuthor(author);
    }


     */

}

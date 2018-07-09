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

import javax.validation.Valid;

@Api(value="author-controller")
@RestController
@RequestMapping("/")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @ApiOperation(value = "保存点位制作者" )
    @PostMapping("/author")
    public Author postAuthor(@RequestBody @Valid Author author, BindingResult bindingResult)
    {
        return authorService.addAuthor(author);
    }
    @ApiOperation(value = "收藏/取消收藏",notes = "通过favoriteId收藏（点位，路线，用户，建筑,数据组等,已存在则取消收藏;/n404:不存在;/n200:删除成功")
    @PutMapping("/favorite")
    public Result postFavorite(@RequestParam @ApiParam(name = "authorId",value = "用户的Id") String authorId,
                               @RequestParam @ApiParam(name = "favoriteId",value = "收藏事物的Id") String favoriteId){
        Result result =new Result();
        if(authorService.getAuthorById(authorId)==null){
            result.setMessage("用户不存在");
            result.setCode(404);
            return result;
        }
        if(authorService.FavoriteIsExistInAuthor(authorId,favoriteId)==false){
            authorService.addFavorite(authorId,favoriteId);
            result.setMessage("收藏成功（可能favoriteId不存在");
            result.setCode(200); //to be continued
            return result;
        }
        result.setMessage("取消收藏");
        authorService.deleteFavorite(authorId,favoriteId);
        return result;
    }
    @ApiOperation(value = "判断用户是否收藏",notes = "如果authorId和favoriteId无效也会返回false")
    @GetMapping("/favorexist")
    public boolean favorIsexist(String authorId,String favoriteId){
        if(authorService.getAuthorById(authorId)==null || authorService.FavoriteIsExistInDb(favoriteId)==false) {
            return false;
        }
        return authorService.FavoriteIsExistInAuthor(authorId, favoriteId);
    }

    @ApiOperation(value="查询收藏中所有",notes="通过名字模糊查询所有收藏相关")
    @GetMapping("/favorite/some")
    public FavoriteResult getFavorite(@RequestParam @ApiParam(name = "authorId",value = "用户的Id") String authorId,
                                       @RequestParam(required = false,defaultValue = "") @ApiParam(name = "favoriteName",value = "收藏事物的名称") String favoriteName,
                                       @RequestParam(required = false) @ApiParam(name = "skip",value = "指定查询收藏偏移量") Integer skip,
                                       @RequestParam(required = false) @ApiParam(name = "limit",value = "指定查询收藏结果数")Integer limit){

        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 100;
        }
        FavoriteResult result = authorService.findFavoriteByNameLike(authorId,favoriteName,skip,limit);
        return result;
    }

    @ApiOperation(value="查询收藏中点位",notes="通过名字模糊查询收藏里的点位")
    @GetMapping("/favorite/nodes")
    public NodeResult getFavoriteNodes(@RequestParam @ApiParam(name = "authorId",value = "用户的Id") String authorId,
                              @RequestParam(required = false,defaultValue = "") @ApiParam(name = "nodeName",value = "收藏事物的名称") String nodeName,
                              @RequestParam(required = false) @ApiParam(name = "skip",value = "指定查询收藏偏移量") Integer skip,
                              @RequestParam(required = false) @ApiParam(name = "limit",value = "指定查询收藏结果数")Integer limit){
        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 100;
        }

        NodeResult result = authorService.findfavorNodeByNameLike(authorId,nodeName,skip,limit);
        return result;
    }

    @ApiOperation(value="查询收藏中路线",notes="通过名字模糊查询收藏里的路线")
    @GetMapping("/favorite/path")
    public PathResult getFavoritePaths(@RequestParam @ApiParam(name = "authorId",value = "用户的Id") String authorId,
                              @RequestParam(required = false,defaultValue = "") @ApiParam(name = "pathName",value = "收藏事物的名称") String pathName,
                              @RequestParam(required = false) @ApiParam(name = "skip",value = "指定查询收藏偏移量") Integer skip,
                              @RequestParam(required = false) @ApiParam(name = "limit",value = "指定查询收藏结果数")Integer limit){

        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 100;
        }
        PathResult result = authorService.findfavorPathByNameLike(authorId,pathName,skip,limit);
        return result;
    }

    @ApiOperation(value="查询收藏中数据组",notes="通过名字模糊查询收藏的数据组")
    @GetMapping("/favorite/dataset")
    public DataSetResult getFavoriteDataSets(@RequestParam @ApiParam(name = "authorId",value = "用户的Id") String authorId,
                              @RequestParam(required = false,defaultValue = "") @ApiParam(name = "dataSetName",value = "收藏事物的名称") String dataSetName,
                              @RequestParam(required = false) @ApiParam(name = "skip",value = "指定查询收藏偏移量") Integer skip,
                              @RequestParam(required = false) @ApiParam(name = "limit",value = "指定查询收藏结果数")Integer limit){
        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 100;
        }

        DataSetResult result = authorService.findfavorDataSetByNameLike(authorId,dataSetName,skip,limit);
        return result;
    }

}

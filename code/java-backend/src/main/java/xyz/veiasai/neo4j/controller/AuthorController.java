package xyz.veiasai.neo4j.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.Favorite;
import xyz.veiasai.neo4j.result.FavoriteResult;
import xyz.veiasai.neo4j.result.Result;
import xyz.veiasai.neo4j.service.AuthorService;
import xyz.veiasai.neo4j.service.FavoriteService;

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
    @ApiOperation(value = "收藏/取消收藏",notes = "通过favoriteId收藏（点位，路线，用户，建筑,数据组等,已存在则取消收藏")
    @PutMapping("/favorite")
    public Result postFavorite(@RequestParam @ApiParam(name = "authorId",value = "用户的Id") String authorId,
                               @RequestParam @ApiParam(name = "favoriteId",value = "收藏事物的Id") String favoriteId){
        Result result =new Result();
        if(authorService.getAuthorById(authorId)==null){
            result.setMessage("用户不存在");
            return result;
        }
        if(authorService.FavoriteIsExist(authorId,favoriteId)==false){
            authorService.addFavorite(authorId,favoriteId);
            result.setMessage("收藏成功（可能favoriteId不存在");
            return result;
        }
        result.setMessage("取消收藏");
        authorService.deleteFavorite(authorId,favoriteId);
        return result;
    }

    @ApiOperation(value="查询收藏",notes="通过名字模糊查询收藏")
    @GetMapping("/favorite/some")
    public Result getFavorite(@RequestParam @ApiParam(name = "authorId",value = "用户的Id") String authorId,
                                    @RequestParam @ApiParam(name = "favoriteName",value = "收藏事物的名称") String favoriteName){

        FavoriteResult result = authorService.findFavoriteByNameLike(authorId,favoriteName);
        return result;
    }

}

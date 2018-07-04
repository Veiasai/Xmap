package xyz.veiasai.neo4j.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.Favorite;
import xyz.veiasai.neo4j.service.AuthorService;
import xyz.veiasai.neo4j.service.FavoriteService;

import javax.validation.Valid;

@Api(value="author-controller")
@RestController
@RequestMapping("/")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private FavoriteService favoriteService;

    @ApiOperation(value = "保存点位制作者" )
    @PostMapping("/author")
    public Author postAuthor(@RequestBody @Valid Author author, BindingResult bindingResult)
    {
        return authorService.addAuthor(author);
    }
    @ApiOperation(value = "收藏",notes = "收藏点位和路线")
    @PostMapping("/favorite")
    public Favorite postFavorite(@RequestBody @Valid Author author,@RequestBody @Valid Favorite favorite){
        return favoriteService.addFavorite(author,favorite);
    }
}

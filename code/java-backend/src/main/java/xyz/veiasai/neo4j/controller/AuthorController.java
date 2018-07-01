package xyz.veiasai.neo4j.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.service.AuthorService;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("/author")
    public Author postAuthor(@RequestBody @Valid Author author, BindingResult bindingResult)
    {
        return authorService.addAuthor(author);
    }
}

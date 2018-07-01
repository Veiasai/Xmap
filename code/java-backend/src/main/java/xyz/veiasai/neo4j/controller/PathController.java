package xyz.veiasai.neo4j.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;
import xyz.veiasai.neo4j.result.PathResult;
import xyz.veiasai.neo4j.result.Result;
import xyz.veiasai.neo4j.service.AuthorService;
import xyz.veiasai.neo4j.service.BuildingService;
import xyz.veiasai.neo4j.service.NodeService;
import xyz.veiasai.neo4j.service.PathService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/")
public class PathController {
    @Autowired
    private PathService pathService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private AuthorService authorService;

    @PostMapping("/path")
    public Path pathPost(@RequestBody Path path, @RequestParam String buildingId, @RequestParam String author,
                         @RequestParam(required = false) String origin, @RequestParam(required = false) String end, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors())
            return null;
        path.setState(1);
        path = pathService.addPath(path);
        String id = path.getId();
        if (id == null)
            return null;
        pathService.addRelation(id, buildingId, author, origin, end);
        return path;
    }

    @GetMapping("/paths")
    public Collection<Path> pathGet(@RequestParam String name, @RequestParam String buildingId) {
        return pathService.findByBuildingAndName(buildingId, name);
    }

    @GetMapping("/paths/origin")
    public Collection<Path> pathGetByOrigin(@RequestParam String originId, @RequestParam String endId) {
        if (originId.equals(endId))
            return null;
        return pathService.findByOriginAndEnd(originId, endId);
    }

    @GetMapping("/paths/author")
    public Collection<Path> pathGetByAuthor(@RequestParam String authorId, @RequestParam String name) {
        return pathService.findByAuthorId(authorId, name);
    }
}

package xyz.veiasai.neo4j.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.veiasai.neo4j.result.Result;
import xyz.veiasai.neo4j.service.AuthorService;
import xyz.veiasai.neo4j.service.BuildingService;

@Api(value = "buildingAdmin-controller")
@RestController
@RequestMapping("/")
public class BuildingAdminController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BuildingService buildingService;

    @PostMapping("/buildingadmin")
    public Result postBuildingAdmin(@RequestParam String buildingId,@RequestParam String authorId){

        Result result =new Result();
        return result;
    }

}

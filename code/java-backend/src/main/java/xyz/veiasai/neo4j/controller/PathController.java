package xyz.veiasai.neo4j.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.DataSet;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;
import xyz.veiasai.neo4j.result.PathResult;
import xyz.veiasai.neo4j.result.Result;
import xyz.veiasai.neo4j.service.*;

import javax.validation.Valid;
import java.util.Collection;

@Api(value = "path-controller")
@RestController
@RequestMapping("/")
public class PathController {
    @Autowired
    private PathService pathService;
    @Autowired
    private DataSetService dataSetService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BuildingService buildingService;

    @PostMapping("/path")
    public PathResult pathPost(@RequestBody Path path, @RequestParam String buildingId, @RequestParam String author,
                               @RequestParam(required = false) String origin, @RequestParam(required = false) String end) throws Exception {
        path = pathService.addPath(path);
        String id = path.getId();
        pathService.addRelation(id, buildingId, author, origin, end);
        PathResult result = new PathResult();
        result.setPath(path);
        return result;
    }

    @GetMapping("/paths")
    public PathResult pathGet(@RequestParam(required = false) String buildingId,
                              @RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false) String dataSetId,
                              @RequestParam(required = false, defaultValue = "0") Integer skip,
                              @RequestParam(required = false, defaultValue = "5") Integer limit) {
        PathResult result = new PathResult();
        if (dataSetId != null) {
            if (dataSetService.getDataSetById(dataSetId) == null) {
                result.setMessage("数据组不存在");
                result.setCode(404);
            } else {
                result.setPaths(dataSetService.findPathByNameLike(dataSetId, name, skip, limit));
                result.setCode(200);
            }
        }
        else if (buildingId == null)
        {
            result.setCode(404);
            result.setMessage("参数错误");
        }
        else if (buildingService.getBuildingById(buildingId) == null) {
            result.setCode(404);
            result.setMessage("建筑不存在");
        } else {
            result.setCode(200);    //对查询结果做判断，是否为空 to be continued
            result.setPaths(pathService.findByBuildingAndName(buildingId, name, skip, limit));
        }
        return result;
    }


    @DeleteMapping("/path")
    public Result pathDeleteById(@RequestParam String authorId, @RequestParam String pathId) {
        Result result = new Result();
        if (authorService.getAuthorById(authorId) == null) {
            result.setCode(404);
            result.setMessage("用户不存在");
        } else if (pathService.findById(pathId) == null) {
            result.setCode(404);
            result.setMessage("路线不存在");
        } else if (!pathService.existPathAndAuthor(authorId, pathId)) {
            result.setCode(403);
            result.setMessage("该用户无权限删除");
        } else {
            pathService.deletePathById(authorId, pathId);
            result.setCode(200);
            result.setMessage("删除路线成功");
        }
        return result;
    }

    @GetMapping("/paths/author")
    public PathResult pathGetByAuthor(@RequestParam String authorId,
                                      @RequestParam(required = false, defaultValue = "") String name,
                                      @RequestParam(required = false, defaultValue = "0") Integer skip,
                                      @RequestParam(required = false, defaultValue = "5") Integer limit) {

        PathResult result = new PathResult();
        if (authorService.getAuthorById(authorId) == null) {
            result.setCode(404);
            result.setMessage("用户不存在");
        } else {
            result.setCode(200);        //对查询结果做判断，是否为空 to be continued
            result.setPaths(pathService.findByAuthorId(authorId, name, skip, limit));
        }
        return result;
    }

    /* 废弃接口


    @GetMapping("/paths/origin")
    public PathResult pathGetByOrigin(@RequestParam String originId, @RequestParam String endId) {
        PathResult result =new PathResult();
        if (originId.equals(endId))
        {
            result.setCode(HttpStatus.FORBIDDEN.value());
            return result;
        }
        result.setPaths(pathService.findByOriginAndEnd(originId, endId));
        result.setCode(HttpStatus.OK.value());
        return result;
    }



    @ApiOperation(value = "查询路线",notes="name设为空，则可查询所有")
    @GetMapping("/paths/name")
    public  PathResult pathGetByName(@RequestParam(required = false,defaultValue = "") String name,
                                            @RequestParam(required = false) Integer skip,
                                            @RequestParam(required = false) Integer limit){
        if(skip == null){
            skip = 0;
        }
        if(limit == null){
            limit = 5;
        }
        PathResult result =new PathResult();
        result.setPaths(pathService.findByName(name,skip,limit));
        return result;
    }

    */
}

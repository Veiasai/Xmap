package xyz.veiasai.neo4j.controller;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.TestDefault;
import xyz.veiasai.neo4j.domain.Path;
import xyz.veiasai.neo4j.pojo.Content;

import java.util.ArrayList;
import java.util.List;


public class PathControllerTest extends TestDefault {

    @Test
    public void pathPost() throws Exception{
        Path pathTemp = new Path();
        pathTemp.setId(null);
        pathTemp.setName("test1");
        pathTemp.setCurves(10);
        pathTemp.setImg("test1");
        pathTemp.setSteps(10);
        pathTemp.setState(1);
        List<Content> contents = new ArrayList<>();
        Content temp = new Content();
        temp.setMessage("test1");
        temp.setType("actionr");
        contents.add(temp);
        pathTemp.setContents(contents);

        mvc.perform(MockMvcRequestBuilders.post("/path")
                .param("buildingId", building.getId())
                .param("author", author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(pathTemp)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path.name").value("test1"));
    }

    @Test
    public void pathGet() throws Exception{
        // invalid param
        mvc.perform(MockMvcRequestBuilders.get("/paths"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("404"));

        // ok
        mvc.perform(MockMvcRequestBuilders.get("/paths")
                .param("buildingId", building.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths[0].name").value("test"));

        // by name, ok
        mvc.perform(MockMvcRequestBuilders.get("/paths")
                .param("buildingId", building.getId())
                .param("name", path.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths[0].id").value(path.getId()));

        // invalid Id
        mvc.perform(MockMvcRequestBuilders.get("/paths")
                .param("buildingId", "NotExist")
                .param("name", path.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths").isEmpty());

        // invalid name
        mvc.perform(MockMvcRequestBuilders.get("/paths")
                .param("buildingId", building.getId())
                .param("name", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths").isEmpty());
    }

    @Test
    public void pathGetByAuthor() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/paths/author")
                .param("authorId", author.getId())
                .param("name", path.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths[0].id").value(path.getId()));

        mvc.perform(MockMvcRequestBuilders.get("/paths/author")
                .param("authorId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths[0].id").value(path.getId()));

        mvc.perform(MockMvcRequestBuilders.get("/paths/author")
                .param("authorId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths").isEmpty());
    }

    @Test
    public void pathGetByDataSet() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/paths")
                .param("dataSetId", dataSetPath.getId())
                .param("name", path.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths[0].id").value(path.getId()));

        mvc.perform(MockMvcRequestBuilders.get("/paths")
                .param("dataSetId", dataSetPath.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths[0].id").value(path.getId()));

        // invalid dataSetId
        mvc.perform(MockMvcRequestBuilders.get("/paths")
                .param("dataSetId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths").isEmpty());

        // invalid name
        mvc.perform(MockMvcRequestBuilders.get("/paths")
                .param("dataSetId", dataSetPath.getId())
                .param("name", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths").isEmpty());
    }

    @Test
    public void pathDeleteById() throws Exception{
//        Path path2 = new Path();
//        path2.setId(null);
//        path2.setName("test1");
//        path2.setCurves(10);
//        path2.setImg("test1");
//        path2.setSteps(10);
//        path2.setState(1);
//        ArrayList<Content> contents = new ArrayList<>();
//        Content temp = new Content();
//        temp.setMessage("test1");
//        temp.setType("actionr");
//        contents.add(temp);
//        path2.setContents(contents);
//        path2 = pathRepository.save(path2);

        assertTrue(pathRepository.findById(path.getId()).isPresent());

        // invalid pathId
        mvc.perform(MockMvcRequestBuilders.delete("/path")
                .param("authorId", author.getId())
                .param("pathId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        assertTrue(pathRepository.findById(path.getId()).isPresent());

        // invalid authorId
        mvc.perform(MockMvcRequestBuilders.delete("/path")
                .param("authorId",  "NotExist")
                .param("pathId", path.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        assertTrue(pathRepository.findById(path.getId()).isPresent());

        // invalid author-[r]-path
        mvc.perform(MockMvcRequestBuilders.delete("/path")
                .param("authorId", author2.getId())
                .param("pathId", path.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        assertTrue(pathRepository.findById(path.getId()).isPresent());
        // ok
        mvc.perform(MockMvcRequestBuilders.delete("/path")
                .param("authorId", author.getId())
                .param("pathId", path.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));

        assertFalse("delete failed", pathRepository.findById(path.getId()).isPresent());
    }
}
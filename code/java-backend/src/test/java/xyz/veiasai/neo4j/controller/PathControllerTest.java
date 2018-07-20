package xyz.veiasai.neo4j.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.veiasai.neo4j.TestDefault;
import xyz.veiasai.neo4j.domain.Path;
import xyz.veiasai.neo4j.pojo.Content;

import java.util.ArrayList;


public class PathControllerTest extends TestDefault {

    @Test
    public void pathPost() throws Exception{
        Path path1 = new Path();
        path1.setId(null);
        path1.setName("test1");
        path1.setCurves(10);
        path1.setImg("test1");
        path1.setSteps(10);
        path1.setState(1);
        ArrayList<Content> contents = new ArrayList<>();
        Content temp = new Content();
        temp.setMessage("test1");
        temp.setType("actionr");
        contents.add(temp);
        path1.setContents(contents);

        gson.toJson(path1);
        mvc.perform(MockMvcRequestBuilders.post("/path")
                .param("buildingId", building.getId())
                .param("author", author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(path1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.path.name").value("test1"));

    }

    @Test
    public void pathGet() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/paths")
                .param("buildingId", building.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths[0].name").value("test"));

        mvc.perform(MockMvcRequestBuilders.get("/paths")
                .param("buildingId", building.getId())
                .param("name", path.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths[0].id").value(path.getId()));

        mvc.perform(MockMvcRequestBuilders.get("/paths")
                .param("buildingId", "NotExist")
                .param("name", path.getName()))
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
    public void pathDeleteById() throws Exception{
        Path path2 = new Path();

        path2.setId(null);
        path2.setName("test1");
        path2.setCurves(10);
        path2.setImg("test1");
        path2.setSteps(10);
        path2.setState(1);
        ArrayList<Content> contents = new ArrayList<>();
        Content temp = new Content();
        temp.setMessage("test1");
        temp.setType("actionr");
        contents.add(temp);
        path2.setContents(contents);
        path2 = pathRepository.save(path2);

        Assert.assertEquals(path2.getId(), pathRepository.findById(path2.getId()).orElse(new Path()).getId());

        // invalid pathId
        mvc.perform(MockMvcRequestBuilders.delete("/path")
                .param("authorId", author.getId())
                .param("pathId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid authorId
        mvc.perform(MockMvcRequestBuilders.delete("/path")
                .param("authorId",  "NotExist")
                .param("pathId", path2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid author-[r]-path
        mvc.perform(MockMvcRequestBuilders.delete("/path")
                .param("authorId", author.getId())
                .param("pathId", path2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        Assert.assertNotNull("delete failed", pathRepository.findById(path2.getId()).orElse(null));

        pathRepository.addRelationAuthorAndBuilding(path2.getId(), author.getId(), building.getId());

        // ok
        mvc.perform(MockMvcRequestBuilders.delete("/path")
                .param("authorId", author.getId())
                .param("pathId", path2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));

        Assert.assertNull("delete failed", pathRepository.findById(path2.getId()).orElse(null));
    }
}
package xyz.veiasai.neo4j.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class NodeControllerTest extends TestDefault{

    @Test
    public void nodePost() throws  Exception{
        // invalid author
        mvc.perform(MockMvcRequestBuilders.post("/node")
                .param("buildingId", building.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // invalid buildingId
        mvc.perform(MockMvcRequestBuilders.post("/node")
                .param("author", author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // ok
        mvc.perform(MockMvcRequestBuilders.post("/node")
                .param("buildingId", building.getId())
                .param("author", author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void nodeGetByBuilding() throws  Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("buildingId", building.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes[0].id").value(node.getId()));

        //invalid buildingId
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("buildingId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("nodes").isEmpty());
    }

    @Test
    public void nodeGetByAuthor() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("authorId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes[0].id").value(node.getId()));

        // invalid authorId
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("authorId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("nodes").isEmpty());
    }

    @Test
    public void nodeGetByAuthorAndBuilding() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("authorId", author.getId())
                .param("buildingId", building.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes[0].id").value(node.getId()));

        // invalid authorId
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("authorId", "NotExist")
                .param("buildingId", building.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes").isEmpty());
    }

    @Test
    public void nodeGetByBuildingAndName() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("buildingId", building.getId())
                .param("name", node.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes[0].id").value(node.getId()));

        // invalid buildingId
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("buildingId", "NotExist")
                .param("name", node.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("node").isEmpty());

        // invalid name
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("buildingId", building.getId())
                .param("name","NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("node").isEmpty());
    }

    @Test
    public void nodeGetByOrigin() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("originId", node.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes[0].id").value(node2.getId()));

        // invalid originId
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("originId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes").isEmpty());
    }
}
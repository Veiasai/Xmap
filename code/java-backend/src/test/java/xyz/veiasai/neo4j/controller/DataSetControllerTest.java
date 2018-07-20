package xyz.veiasai.neo4j.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.veiasai.neo4j.TestDefault;

import static org.junit.Assert.*;

public class DataSetControllerTest extends TestDefault {

    @Test
    public void postDataSet() {
    }

    @Test
    public void deleteDataSet() {

    }

    @Test
    public void getDataSets() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/dataset")
                .param("buildingId", building.getId())
                .param("authorId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataSets").isNotEmpty());

        // ok
        mvc.perform(MockMvcRequestBuilders.get("/dataset")
                .param("buildingId", building.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataSets").isNotEmpty());

        // ok
        mvc.perform(MockMvcRequestBuilders.get("/dataset")
                .param("authorId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataSets").isNotEmpty());

        // invalid building
        mvc.perform(MockMvcRequestBuilders.get("/dataset")
                .param("buildingId", "NotExist")
                .param("authorId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataSets").isEmpty());

        // invalid author
        mvc.perform(MockMvcRequestBuilders.get("/dataset")
                .param("buildingId", building.getId())
                .param("authorId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataSets").isEmpty());
    }

    @Test
    public void addNodes() {
    }

    @Test
    public void deleteNodes() {
    }
}
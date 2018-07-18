package xyz.veiasai.neo4j.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.veiasai.neo4j.domain.Building;

import static org.junit.Assert.*;

public class BuildingControllerTest extends TestDefault {

    @Test
    public void postBuilding() throws Exception{
        // invalid address
        mvc.perform(MockMvcRequestBuilders.post("/building"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // post exist
        mvc.perform(MockMvcRequestBuilders.post("/building")
                .param("address", building.getAddress().getAddress())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(building)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(building.getId()));

        // post new
        Building buildingTemp = new Building();
        buildingTemp.setName("new");

        mvc.perform(MockMvcRequestBuilders.post("/building")
                .param("address", "new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(buildingTemp)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(buildingTemp.getName()));

        assertEquals("new", buildingRepository.findByNameAndAddress("new", "new").getName());
    }
}
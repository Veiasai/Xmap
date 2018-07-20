package xyz.veiasai.neo4j.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.veiasai.neo4j.TestDefault;

public class BuildingAdminControllerTest extends TestDefault {

    @Test
    public void postBuildingAdmin() {

    }

    @Test
    public void deleteBuildingAdmin() {

    }

    @Test
    public void getAdminByBuildingId() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/building/admin")
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.buildings[0].id").value(building.getId()));
    }

    @Test
    public void getBuildingByAdminId() {

    }
}
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
        mvc.perform(MockMvcRequestBuilders.get("/building/admin/building")
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.buildings[0].id").value(building.getId()));

        // invalid author id
        mvc.perform(MockMvcRequestBuilders.get("/building/admin/building")
                .param("adminId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(405));

        // invalid admin
        mvc.perform(MockMvcRequestBuilders.get("/building/admin/building")
                .param("adminId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buildings").isEmpty());
    }

    @Test
    public void getBuildingByAdminId() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/building/admin/admin")
                .param("buildingId", building.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.buildingAdmins[0].id").value(buildingAdmin.getId()));

        // invalid building id
        mvc.perform(MockMvcRequestBuilders.get("/building/admin/admin")
                .param("buildingId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));
    }
}
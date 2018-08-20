package xyz.veiasai.neo4j.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.veiasai.neo4j.TestDefault;

import static org.junit.Assert.*;

public class SystemAdminControllerTest extends TestDefault {
    private final String mm = "123456";
    @Test
    public void handleApply() throws Exception{
        // auth
        mvc.perform(MockMvcRequestBuilders.post("/systemadmin/handleapply")
                .param("buildingId", building.getId())
                .param("authorId", buildingAdmin.getId())
                .param("_sign", "xx")
                .param("refuse", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // has been admin
        mvc.perform(MockMvcRequestBuilders.post("/systemadmin/handleapply")
                .param("buildingId", building.getId())
                .param("authorId", buildingAdmin.getId())
                .param("_sign", mm)
                .param("refuse", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(405));

        // not apply
        mvc.perform(MockMvcRequestBuilders.post("/systemadmin/handleapply")
                .param("buildingId", building2.getId())
                .param("authorId", author.getId())
                .param("_sign", mm)
                .param("refuse", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(406));

        // invalid building
        mvc.perform(MockMvcRequestBuilders.post("/systemadmin/handleapply")
                .param("buildingId", "NotExist")
                .param("authorId", buildingAdmin.getId())
                .param("_sign", mm)
                .param("refuse", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid author
        mvc.perform(MockMvcRequestBuilders.post("/systemadmin/handleapply")
                .param("buildingId", building.getId())
                .param("authorId", "NotExist")
                .param("_sign", mm)
                .param("refuse", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // accept
        assertNotEquals(1, buildingAdminRepository.countValidBuildingAdmin(building.getId(), author.getId()));
        mvc.perform(MockMvcRequestBuilders.post("/systemadmin/handleapply")
                .param("buildingId", building.getId())
                .param("authorId", author.getId())
                .param("_sign", mm)
                .param("refuse", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
        assertEquals(1, buildingAdminRepository.countValidBuildingAdmin(building.getId(), author.getId()));

        // refuse
        assertNotEquals(1, buildingAdminRepository.countValidBuildingAdmin(building2.getId(), author2.getId()));
        mvc.perform(MockMvcRequestBuilders.post("/systemadmin/handleapply")
                .param("buildingId", building2.getId())
                .param("authorId", author2.getId())
                .param("_sign", mm)
                .param("refuse", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(201));
        assertNotEquals(1, buildingAdminRepository.countApplyBuildingAdmin(building2.getId(), author2.getId()));
        assertNotEquals(1, buildingAdminRepository.countValidBuildingAdmin(building2.getId(), author2.getId()));
    }

    @Test
    public void deleteBuildingAdmin() throws Exception{
        // auth
        mvc.perform(MockMvcRequestBuilders.delete("/systemadmin/buildingadmin")
                .param("buildingId", building.getId())
                .param("authorId", buildingAdmin.getId())
                .param("_sign", "xx"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // not admin
        mvc.perform(MockMvcRequestBuilders.delete("/systemadmin/buildingadmin")
                .param("buildingId", building.getId())
                .param("authorId", author2.getId())
                .param("_sign", mm))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(405));


        // invalid building
        mvc.perform(MockMvcRequestBuilders.delete("/systemadmin/buildingadmin")
                .param("buildingId", "NotExist")
                .param("authorId", buildingAdmin.getId())
                .param("_sign", mm))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid author
        mvc.perform(MockMvcRequestBuilders.delete("/systemadmin/buildingadmin")
                .param("buildingId", building.getId())
                .param("authorId", "NotExist")
                .param("_sign", mm))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // ok
        assertEquals(1, buildingAdminRepository.countValidBuildingAdmin(building.getId(), buildingAdmin.getId()));
        mvc.perform(MockMvcRequestBuilders.delete("/systemadmin/buildingadmin")
                .param("buildingId", building.getId())
                .param("authorId", buildingAdmin.getId())
                .param("_sign", mm))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
        assertNotEquals(1, buildingAdminRepository.countValidBuildingAdmin(building.getId(), author.getId()));
    }

    @Test
    public void getApply() throws Exception{
        // auth
        mvc.perform(MockMvcRequestBuilders.get("/systemadmin/apply")
                .param("buildingId", building.getId())
                .param("_sign", "xx"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // by building
        mvc.perform(MockMvcRequestBuilders.get("/systemadmin/apply")
                .param("buildingId", building.getId())
                .param("_sign", mm))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apply").isNotEmpty());

        // all building
        mvc.perform(MockMvcRequestBuilders.get("/systemadmin/apply")
                .param("skip", "0")
                .param("limit", "5")
                .param("_sign", mm))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apply").isNotEmpty());

    }
}
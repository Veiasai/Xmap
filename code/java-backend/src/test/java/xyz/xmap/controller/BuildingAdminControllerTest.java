package xyz.xmap.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.xmap.TestDefault;
import static org.junit.Assert.*;

public class BuildingAdminControllerTest extends TestDefault {

    @Test
    public void loginBuildingAdmin() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.post("/building/admin/login")
                .param("authorId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // ok
        mvc.perform(MockMvcRequestBuilders.post("/building/admin/login")
                .param("authorId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
    }

    @Test
    public void deletePathByAdmin() throws Exception{
        assertTrue(pathRepository.findById(path.getId()).isPresent());
        // invalid path
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/path")
                .param("pathId", "NotExist")
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        assertTrue(pathRepository.findById(path.getId()).isPresent());
        // invalid building
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/path")
                .param("pathId", path.getId())
                .param("buildingId", "NotExist")
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        assertTrue(pathRepository.findById(path.getId()).isPresent());
        // invalid building
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/path")
                .param("pathId", path.getId())
                .param("buildingId", building.getId())
                .param("adminId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        assertTrue(pathRepository.findById(path.getId()).isPresent());
        // invalid admin
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/path")
                .param("pathId", path.getId())
                .param("buildingId", building.getId())
                .param("adminId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        assertTrue(pathRepository.findById(path.getId()).isPresent());
        // invalid building-relation-path
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/path")
                .param("pathId", path2.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        assertTrue(pathRepository.findById(path.getId()).isPresent());
        // ok
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/path")
                .param("pathId", path.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
        assertFalse(pathRepository.findById(path.getId()).isPresent());
    }

    @Test
    public void deleteNodeByAdmin() throws Exception{
        assertTrue(nodeRepository.findById(node.getId()).isPresent());
        // invalid node
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/node")
                .param("nodeId", "NotExist")
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        assertTrue(nodeRepository.findById(node.getId()).isPresent());
        // invalid building
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/node")
                .param("nodeId", node.getId())
                .param("buildingId", "NotExist")
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        assertTrue(nodeRepository.findById(node.getId()).isPresent());
        // invalid author
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/node")
                .param("nodeId", node.getId())
                .param("buildingId", building.getId())
                .param("adminId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        assertTrue(nodeRepository.findById(node.getId()).isPresent());
        // invalid admin
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/node")
                .param("nodeId", node.getId())
                .param("buildingId", building.getId())
                .param("adminId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));
        assertTrue(nodeRepository.findById(node.getId()).isPresent());

        // invalid building-relation-node
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/node")
                .param("nodeId", node2.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));
        assertTrue(nodeRepository.findById(node.getId()).isPresent());

        // ok
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/node")
                .param("nodeId", node.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
        assertFalse(nodeRepository.findById(node.getId()).isPresent());
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid admin
        mvc.perform(MockMvcRequestBuilders.get("/building/admin/building")
                .param("adminId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buildings").isEmpty());
    }

    @Test
    public void countAdminByBuildingId() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/building/admin/buildingandcount")
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.countSums").isNotEmpty());

        // invalid author id
        mvc.perform(MockMvcRequestBuilders.get("/building/admin/buildingandcount")
                .param("adminId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid admin
        mvc.perform(MockMvcRequestBuilders.get("/building/admin/buildingandcount")
                .param("adminId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countSums").isEmpty());
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

    @Test
    public void deleteDataSetByAdmin() throws Exception {
        assertTrue(dataSetRepository.findById(dataSetNode.getId()).isPresent());
        // invalid dataSet
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset")
                .param("dataSetId", "NotExist")
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));
        assertTrue(dataSetRepository.findById(dataSetNode.getId()).isPresent());
        // invalid building
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", "NotExist")
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));
        assertTrue(dataSetRepository.findById(dataSetNode.getId()).isPresent());
        // invalid user
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));
        assertTrue(dataSetRepository.findById(dataSetNode.getId()).isPresent());
        // invalid admin
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));
        assertTrue(dataSetRepository.findById(dataSetNode.getId()).isPresent());
        // invalid building-relation-dataSet
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset")
                .param("dataSetId", dataSetPath.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));
        assertTrue(dataSetRepository.findById(dataSetPath.getId()).isPresent());
        // ok
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));

        assertFalse(dataSetRepository.findById(dataSetNode.getId()).isPresent());
    }

    @Test
    public void updateDataSetByAdmin() throws Exception {

        // invalid dataSet
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset")
                .param("dataSetId", "NotExist")
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId())
                .param("Ids", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid building
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", "NotExist")
                .param("adminId", buildingAdmin.getId())
                .param("Ids", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid user
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", "NotExist")
                .param("Ids", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid admin
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", author.getId())
                .param("Ids", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // invalid building-relation-dataSet
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset")
                .param("dataSetId", dataSetPath.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId())
                .param("Ids", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // ok
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId())
                .param("Ids", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));


    }

    @Test
    public void addDataSetDetailByAdmin() throws Exception {

        // invalid dataSet
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset/detail")
                .param("dataSetId", "NotExist")
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId())
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid building
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset/detail")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", "NotExist")
                .param("adminId", buildingAdmin.getId())
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid user
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset/detail")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", "NotExist")
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid admin
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset/detail")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", author.getId())
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // invalid building-relation-dataSet
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset/detail")
                .param("dataSetId", dataSetPath.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId())
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // ok
        mvc.perform(MockMvcRequestBuilders.put("/building/admin/dataset/detail")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId())
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));

    }

    @Test
    public void deleteDataSetDetailByAdmin() throws Exception {
        // invalid dataSet
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset/detail")
                .param("dataSetId", "NotExist")
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId())
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid building
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset/detail")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", "NotExist")
                .param("adminId", buildingAdmin.getId())
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid user
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset/detail")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", "NotExist")
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid admin
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset/detail")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", author.getId())
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // invalid building-relation-dataSet
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset/detail")
                .param("dataSetId", dataSetPath.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId())
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // ok
        mvc.perform(MockMvcRequestBuilders.delete("/building/admin/dataset/detail")
                .param("dataSetId", dataSetNode.getId())
                .param("buildingId", building.getId())
                .param("adminId", buildingAdmin.getId())
                .param("Id", "[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));

    }
}
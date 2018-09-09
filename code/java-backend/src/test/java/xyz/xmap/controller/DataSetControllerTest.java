package xyz.xmap.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.xmap.domain.DataSet;
import xyz.xmap.TestDefault;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataSetControllerTest extends TestDefault {

    @Test
    public void postDataSet() throws Exception{
        DataSet dataSetTemp = new DataSet();
        dataSetTemp.setType("node");
        dataSetTemp.setName("test");
        // ok
        mvc.perform(MockMvcRequestBuilders.post("/dataset")
                .param("buildingId", building.getId())
                .param("authorId", author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(dataSetTemp)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                "$.code").value(200));
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
    public void addNodes() throws Exception{
        List<String> ids = new ArrayList<>();
        ids.add(node2.getId());
        // ok
        mvc.perform(MockMvcRequestBuilders.post("/dataset/add")
                .param("authorId",author.getId())
                .param("dataSetId", dataSetNode.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));

        assertEquals(node2.getId(), dataSetRepository.findNodesByNameLike(dataSetNode.getId(), node2.getName(), 0, 5).iterator().next().getId());
        dataSetRepository.deleteRelationDataSetAndNode(dataSetNode.getId(), node2.getId());

        ids.clear();
        ids.add(path.getId());
        // ok
        mvc.perform(MockMvcRequestBuilders.post("/dataset/add")
                .param("authorId",author.getId())
                .param("dataSetId", dataSetPath.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));


        // invalid id
        mvc.perform(MockMvcRequestBuilders.post("/dataset/add")
                .param("authorId",author.getId())
                .param("dataSetId", "NotExist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        /*
        // invalid id
        ids.clear();
        mvc.perform(MockMvcRequestBuilders.post("/dataset/add")
                .param("dataSetId", dataSetPath.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));*/
    }

    @Test
    public void deleteNodes() throws Exception{
        // ok
        assertFalse(dataSetRepository.findAllNodes(dataSetNode.getId()).isEmpty());
        List<String> ids = new ArrayList<>();
        ids.add(node.getId());
        mvc.perform(MockMvcRequestBuilders.put("/dataset")
                .param("authorId",author.getId())
                .param("dataSetId", dataSetNode.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
        assertTrue(dataSetRepository.findAllNodes(dataSetNode.getId()).isEmpty());

        // ok
        ids.clear();
        ids.add(path.getId());
        assertFalse(dataSetRepository.findAllPaths(dataSetPath.getId()).isEmpty());
        mvc.perform(MockMvcRequestBuilders.put("/dataset")
                .param("authorId",author.getId())
                .param("dataSetId", dataSetPath.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
        assertTrue(dataSetRepository.findAllPaths(dataSetPath.getId()).isEmpty());

        // invalid dataSet
        mvc.perform(MockMvcRequestBuilders.put("/dataset")
                .param("authorId",author.getId())
                .param("dataSetId", "NotExist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));
    }

    @Test
    public void deleteDataSet() throws Exception{
        // invalid dataSet
        mvc.perform(MockMvcRequestBuilders.delete("/dataset")
                .param("authorId", author.getId())
                .param("dataSetId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid authorId
        mvc.perform(MockMvcRequestBuilders.delete("/dataset")
                .param("authorId", "NotExist")
                .param("dataSetId", dataSetNode.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid auth
        mvc.perform(MockMvcRequestBuilders.delete("/dataset")
                .param("authorId", author2.getId())
                .param("dataSetId", dataSetNode.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // ok
        mvc.perform(MockMvcRequestBuilders.delete("/dataset")
                .param("authorId", author.getId())
                .param("dataSetId", dataSetNode.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));

        assertNull(dataSetRepository.findDataSetById(dataSetNode.getId()));
    }
}
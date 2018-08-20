package xyz.veiasai.neo4j.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.veiasai.neo4j.TestDefault;

import static org.junit.Assert.*;

public class AuthorControllerTest extends TestDefault {

    @Test
    public void postFavorite() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.post("/favorite")
                .param("authorId", author.getId())
                .param("favoriteId", node2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));

        authorRepository.deleteFavorite(author.getId(), node2.getId());

        // invalid author
        mvc.perform(MockMvcRequestBuilders.post("/favorite")
                .param("authorId", "NotExist")
                .param("favoriteId", node2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid node
        mvc.perform(MockMvcRequestBuilders.post("/favorite")
                .param("authorId", author.getId())
                .param("favoriteId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));
    }

    @Test
    public void cancelFavorite() throws  Exception{
        authorRepository.addFavorite(author.getId(), node2.getId());

        // ok
        mvc.perform(MockMvcRequestBuilders.delete("/favorite")
                .param("authorId", author.getId())
                .param("favoriteId", node2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));

        assertEquals(0, authorRepository.findFavoriteById(author.getId(), node2.getId()));

        // invalid author
        mvc.perform(MockMvcRequestBuilders.delete("/favorite")
                .param("authorId", "NotExist")
                .param("favoriteId", node2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid node
        mvc.perform(MockMvcRequestBuilders.delete("/favorite")
                .param("authorId", author.getId())
                .param("favoriteId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));
    }

    @Test
    public void favorIsexist () throws Exception {
        // not exist
        mvc.perform(MockMvcRequestBuilders.get("/favorexist")
                .param("authorId", "NotExist")
                .param("favoriteId", node.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        mvc.perform(MockMvcRequestBuilders.get("/favorexist")
                .param("authorId", author.getId())
                .param("favoriteId","NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // ok
        mvc.perform(MockMvcRequestBuilders.get("/favorexist")
                .param("authorId", author.getId())
                .param("favoriteId", node.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("1"));
    }

    @Test
    public void getFavorite () throws Exception
    {
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/favorite/some")
                .param("authorId", author.getId())
                .param("favoriteName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes[0].id").value(node.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths[0].id").value(path.getId()));

        // invalid author
        mvc.perform(MockMvcRequestBuilders.get("/favorite/some")
                .param("authorId", "NotExist")
                .param("favoriteName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths").isEmpty());

        // invalid name
        mvc.perform(MockMvcRequestBuilders.get("/favorite/some")
                .param("authorId", author.getId())
                .param("favoriteName", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths").isEmpty());
    }

    @Test
    public void getFavoriteNodes() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/favorite/nodes")
                .param("authorId", author.getId())
                .param("nodeName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes[0].id").value(node.getId()));

        // invalid author
        mvc.perform(MockMvcRequestBuilders.get("/favorite/nodes")
                .param("authorId", "NotExist")
                .param("nodeName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes").isEmpty());

        // invalid name
        mvc.perform(MockMvcRequestBuilders.get("/favorite/nodes")
                .param("authorId", author.getId())
                .param("nodeName", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes").isEmpty());
    }

    @Test
    public void getFavoritePaths() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/favorite/path")
                .param("authorId", author.getId())
                .param("pathName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths[0].id").value(path.getId()));

        // invalid author
        mvc.perform(MockMvcRequestBuilders.get("/favorite/path")
                .param("authorId", "NotExist")
                .param("pathName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths").isEmpty());

        // invalid name
        mvc.perform(MockMvcRequestBuilders.get("/favorite/path")
                .param("authorId", author.getId())
                .param("pathName", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.paths").isEmpty());

    }

    @Test
    public void getFavoriteDataSets() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/favorite/dataset")
                .param("authorId", author.getId())
                .param("dataSetName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataSets[0].id").value(dataSetNode.getId()));

        // invalid author
        mvc.perform(MockMvcRequestBuilders.get("/favorite/dataset")
                .param("authorId", "NotExist")
                .param("dataSetName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataSets").isEmpty());

        // invalid name
        mvc.perform(MockMvcRequestBuilders.get("/favorite/dataset")
                .param("authorId", author.getId())
                .param("dataSetName", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataSets").isEmpty());
    }

    @Test
    public void applyBuildingAdmin() throws Exception{
        // not exist
        mvc.perform(MockMvcRequestBuilders.get("/apply/buildingadmin")
                .param("buildingId", building.getId())
                .param("authorId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        mvc.perform(MockMvcRequestBuilders.get("/apply/buildingadmin")
                .param("buildingId", "NotExist")
                .param("authorId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // has been admin
        mvc.perform(MockMvcRequestBuilders.get("/apply/buildingadmin")
                .param("buildingId", building.getId())
                .param("authorId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(405));

        // has applied
        mvc.perform(MockMvcRequestBuilders.get("/apply/buildingadmin")
                .param("buildingId", building.getId())
                .param("authorId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // ok
        mvc.perform(MockMvcRequestBuilders.get("/apply/buildingadmin")
                .param("buildingId", building2.getId())
                .param("authorId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));

    }
}
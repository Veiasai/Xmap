package xyz.veiasai.neo4j.controller;

import org.junit.Test;
import org.neo4j.cypher.internal.frontend.v2_3.ast.functions.E;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));
    }

    @Test
    public void favorIsexist () throws Exception {
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.Nodes[0].id").value(node.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Paths[0].id").value(path.getId()));

        // invalid author
        mvc.perform(MockMvcRequestBuilders.get("/favorite/some")
                .param("authorId", "NotExist")
                .param("favoriteName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Nodes").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Paths").isEmpty());

        // invalid name
        mvc.perform(MockMvcRequestBuilders.get("/favorite/some")
                .param("authorId", author.getId())
                .param("favoriteName", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Nodes").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Paths").isEmpty());
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
                .param("datasetName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.DataSets").value(node.getId()));

        // invalid author
        mvc.perform(MockMvcRequestBuilders.get("/favorite/dataset")
                .param("authorId", "NotExist")
                .param("datasetName", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.DataSets").isEmpty());

        // invalid name
        mvc.perform(MockMvcRequestBuilders.get("/favorite/dataset")
                .param("authorId", author.getId())
                .param("datasetName", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.DataSets").isEmpty());
    }
}
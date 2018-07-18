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
        mvc.perform(MockMvcRequestBuilders.delete("/favorexist")
                .param("authorId", author.getId())
                .param("favoriteId", node.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("1"));
    }

    @Test
    public void getFavorite() {
    }

    @Test
    public void getFavoriteNodes() {
    }

    @Test
    public void getFavoritePaths() {
    }

    @Test
    public void getFavoriteDataSets() {
    }
}
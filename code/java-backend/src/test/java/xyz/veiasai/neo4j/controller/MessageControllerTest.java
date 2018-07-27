package xyz.veiasai.neo4j.controller;

import org.junit.Test;
import org.neo4j.cypher.internal.frontend.v2_3.ast.functions.E;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.veiasai.neo4j.TestDefault;
import xyz.veiasai.neo4j.domain.Message;

import static org.junit.Assert.*;

public class MessageControllerTest extends TestDefault {

    @Test
    public void postMessage() throws Exception{
        Message messageTemp = new Message();
        messageTemp.setState(1);
        messageTemp.setTitle("test");
        messageTemp.setContent("test");

        // not exist
        mvc.perform(MockMvcRequestBuilders.post("/message")
                .param("buildingId", building.getId())
                .param("authorId", "NotExist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(messageTemp)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // not exist
        mvc.perform(MockMvcRequestBuilders.post("/message")
                .param("buildingId", "NotExist")
                .param("authorId", buildingAdmin.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(messageTemp)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));

        // invalid admin
        mvc.perform(MockMvcRequestBuilders.post("/message")
                .param("buildingId", building.getId())
                .param("authorId", author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(messageTemp)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));

        // ok
        mvc.perform(MockMvcRequestBuilders.post("/message")
                .param("buildingId", building.getId())
                .param("authorId", buildingAdmin.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(messageTemp)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
    }

    @Test
    public void deleteMessage() throws Exception{
        assertTrue(messageRepository.findById(message.getId()).isPresent());
        // invalid message
        mvc.perform(MockMvcRequestBuilders.delete("/message")
                .param("messageId", "NotExist")
                .param("authorId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404));
        assertTrue(messageRepository.findById(message.getId()).isPresent());
        // invalid author
        mvc.perform(MockMvcRequestBuilders.delete("/message")
                .param("messageId", message.getId())
                .param("authorId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(405));
        assertTrue(messageRepository.findById(message.getId()).isPresent());
        // invalid admin
        mvc.perform(MockMvcRequestBuilders.delete("/message")
                .param("messageId", message.getId())
                .param("authorId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(403));
        assertTrue(messageRepository.findById(message.getId()).isPresent());
        // ok
        mvc.perform(MockMvcRequestBuilders.delete("/message")
                .param("messageId", message.getId())
                .param("authorId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
        assertFalse(messageRepository.findById(message.getId()).isPresent());
    }

    @Test
    public void getMessage() throws Exception{
        // ok
        mvc.perform(MockMvcRequestBuilders.get("/message")
                .param("buildingId", building.getId())
                .param("authorId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].id").value(message.getId()));

        // ok
        mvc.perform(MockMvcRequestBuilders.get("/message")
                .param("buildingId", building.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].id").value(message.getId()));

        // ok
        mvc.perform(MockMvcRequestBuilders.get("/message")
                .param("authorId", buildingAdmin.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages[0].id").value(message.getId()));
    }
}
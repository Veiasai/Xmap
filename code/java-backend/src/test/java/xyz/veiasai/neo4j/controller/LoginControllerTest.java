package xyz.veiasai.neo4j.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.veiasai.neo4j.TestDefault;

public class LoginControllerTest extends TestDefault {

    @Test
    public void login() throws Exception{
        // error code
        mvc.perform(MockMvcRequestBuilders.get("/login")
                .param("code", "error"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.openid").doesNotExist());
    }
}
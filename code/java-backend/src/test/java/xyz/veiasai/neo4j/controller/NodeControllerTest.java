package xyz.veiasai.neo4j.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.veiasai.neo4j.domain.Address;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.Building;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.repositories.AuthorRepository;
import xyz.veiasai.neo4j.repositories.BuildingRepository;
import xyz.veiasai.neo4j.repositories.NodeRepository;
import xyz.veiasai.neo4j.repositories.TestRepository;
import xyz.veiasai.neo4j.service.BuildingService;

import java.net.URL;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NodeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private TestRepository testRepository;

    private static Building building = new Building();
    private static Address address = new Address();
    private static Author author = new Author();
    private static Node node = new Node();

    @Before
    public void setup() {
        testRepository.clean();
        // 初始化building数据
        building.setId("testBuilding");
        building.setName("test");
        address.setAddress("test");
        building.setAddress(address);
        building = buildingRepository.save(building);

        //初始化author
        author.setId("testUser");
        author = authorRepository.save(author);

        //初始化node
        node.setName("testNode");
        node.setAuthor(author);
        node.setBuilding(building);
        nodeRepository.save(node);
    }

    @Test
    public void nodePost() throws  Exception{
        mvc.perform(MockMvcRequestBuilders.post("/node")
                .param("buildingId", building.getId())
                .param("author", author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.node.name").value("test"));
    }

    @Test
    public void nodeGetByBuilding() throws  Exception{
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("buildingId", building.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes[0].id").value(node.getId()));

        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("buildingId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("nodes").isEmpty());
    }

    @Test
    public void nodeGetByAuthor() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("authorId", author.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes[0].id").value(node.getId()));

        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("authorId", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("nodes").isEmpty());
    }

    @Test
    public void nodeGetByBuildingAndName() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("buildingId", building.getId())
                .param("name", node.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nodes[0].id").value(node.getId()));

        mvc.perform(MockMvcRequestBuilders.get("/nodes")
                .param("buildingId", "NotExist")
                .param("name", "NotExist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("node").isEmpty());
    }

    @After
    public void clean() {
        testRepository.clean();
    }
}
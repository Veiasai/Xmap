package xyz.veiasai.neo4j;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import xyz.veiasai.neo4j.domain.*;
import xyz.veiasai.neo4j.pojo.Content;
import xyz.veiasai.neo4j.repositories.*;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestDefault {

    @Autowired
    public MockMvc mvc;

    @Autowired
    public BuildingRepository buildingRepository;

    @Autowired
    public AuthorRepository authorRepository;

    @Autowired
    public NodeRepository nodeRepository;

    @Autowired
    public TestRepository testRepository;

    @Autowired
    public PathRepository pathRepository;

    @Autowired
    public DataSetRepository dataSetRepository;

    @Autowired
    public BuildingAdminRepository buildingAdminRepository;

    protected static Building building = new Building();
    protected static Address address = new Address();
    protected static Author author = new Author();
    protected static Node node = new Node();
    protected static Node node2 = new Node();
    protected static Path path = new Path();
    protected static DataSet dataSet = new DataSet();
    protected static Gson gson = new Gson();
    protected static Author buildingAdmin = new Author();

    @Before
    public void setup() {
        testRepository.clean();
        // 初始化building数据
        building.setId("testBuilding");
        building.setName("test");
        address.setAddress("test");
        building.setAddress(address);
        building = buildingRepository.save(building);

        // 初始化author
        author.setId("testUser");
        author = authorRepository.save(author);

        // 初始化node
        node.setId(null);
        node.setName("testNode");
        node.setAuthor(author);
        node.setBuilding(building);
        node = nodeRepository.save(node);

        node2.setName("node2");
        node2.setId(null);
        node2 = nodeRepository.save(node2);

        // 初始化path
        path.setId(null);
        path.setName("test");
        path.setCurves(10);
        path.setImg("test");
        path.setSteps(10);
        path.setState(1);
        ArrayList<Content> contents = new ArrayList<>();
        Content temp = new Content();
        temp.setMessage("test");
        temp.setType("actionr");
        contents.add(temp);
        path.setContents(contents);
        path = pathRepository.save(path);
        pathRepository.addRelationAuthorAndBuilding(path.getId(), building.getId(), author.getId());
        pathRepository.addRelationOriginAndEnd(path.getId(), node.getId(), node2.getId());

        // 初始化dataSet
        dataSet.setName("test");
        dataSet.setState(1);
        dataSet.setType("node");
        dataSet = dataSetRepository.save(dataSet);
        dataSetRepository.addRelationNodeAndDataSet(dataSet.getId(), node.getId());

        // 初始化favorite
        authorRepository.addFavorite(author.getId(), path.getId());
        authorRepository.addFavorite(author.getId(), node.getId());
        authorRepository.addFavorite(author.getId(), dataSet.getId());

        // 初始化building admin
        buildingAdmin.setId("bAdmin");
        authorRepository.save(buildingAdmin);

        buildingAdminRepository.addBuildingAdmin(building.getId(), buildingAdmin.getId());
    }

    @After
    public void clean() {
        testRepository.clean();
    }
}

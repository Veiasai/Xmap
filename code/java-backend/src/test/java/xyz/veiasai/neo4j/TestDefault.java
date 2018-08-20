package xyz.veiasai.neo4j;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.*;
import xyz.veiasai.neo4j.pojo.Content;
import xyz.veiasai.neo4j.pojo.Location;
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
    @Autowired
    public MessageRepository messageRepository;

    protected static Location location = new Location();
    protected static Building building = new Building();
    protected static Building building2 = new Building();
    protected static Address address = new Address();
    protected static Author author = new Author();
    protected static Author author2 = new Author();
    protected static Node node = new Node();
    protected static Node node2 = new Node();
    protected static Path path = new Path();
    protected static Path path2 = new Path();
    protected static DataSet dataSetNode = new DataSet();
    protected static DataSet dataSetPath = new DataSet();
    protected static Gson gson = new Gson();
    protected static Author buildingAdmin = new Author();
    protected static Message message = new Message();

    @Before
    public void setup() {
        testRepository.clean();
        // 初始化building数据
        building.setId("testBuilding");
        building.setName("test");
        address.setAddress("test");
        building.setAddress(address);

        location.setLatitude(110.1);
        location.setLongitude(110.1);
        building.setLocation(location);
        building = buildingRepository.save(building);

        building2.setId("testBuilding2");
        building2.setName("test2");
        address.setAddress("test2");
        building.setAddress(address);
        building2 = buildingRepository.save(building2);

        // 初始化author
        author.setId("testUser");
        author = authorRepository.save(author);
        author2.setId("testUser2");
        author2 = authorRepository.save(author2);

        // 初始化node
        node.setId(null);
        node.setName("testNode");
        node.setAuthor(author);
        node.setBuilding(building);
        node = nodeRepository.save(node);

        node2.setName("node2");
        node2.setId(null);
        node2.setBuilding(building2);
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
        pathRepository.addRelationBuildingAndAuthor(path.getId(), building.getId(), author.getId());
        pathRepository.addRelationOriginAndEnd(path.getId(), node.getId(), node2.getId());

        path2.setId(null);
        path2.setName("test");
        path2.setCurves(10);
        path2.setImg("test");
        path2.setSteps(10);
        path2.setState(1);
        path2.setContents(contents);
        path2 = pathRepository.save(path2);
        pathRepository.addRelationBuildingAndAuthor(path2.getId(), building2.getId(), author2.getId());



        // 初始化dataSet
        dataSetNode.setName("test");
        dataSetNode.setState(1);
        dataSetNode.setType("node");
        dataSetNode = dataSetRepository.save(dataSetNode);
        dataSetRepository.addRelationDataSetAndNode(dataSetNode.getId(), node.getId());
        dataSetRepository.addRelationBuildingAndAuthor(dataSetNode.getId(), building.getId(), author.getId());

        dataSetPath.setName("test");
        dataSetPath.setState(1);
        dataSetPath.setType("path");
        dataSetPath = dataSetRepository.save(dataSetPath);
        dataSetRepository.addRelationDataSetAndPath(dataSetPath.getId(), path.getId());
        dataSetRepository.addRelationBuildingAndAuthor(dataSetPath.getId(), building2.getId(), author.getId());

        // 初始化favorite
        authorRepository.addFavorite(author.getId(), path.getId());
        authorRepository.addFavorite(author.getId(), node.getId());
        authorRepository.addFavorite(author.getId(), dataSetNode.getId());

        // 初始化building admin
        buildingAdmin.setId("bAdmin");
        buildingAdmin = authorRepository.save(buildingAdmin);
        buildingAdminRepository.applyBuildingAdmin(building.getId(), buildingAdmin.getId());
        buildingAdminRepository.applyBuildingAdmin(building2.getId(), buildingAdmin.getId());
        buildingAdminRepository.applyBuildingAdmin(building.getId(), author.getId());
        buildingAdminRepository.applyBuildingAdmin(building2.getId(), author2.getId());
        buildingAdminRepository.setBuildingAdmin(building.getId(), buildingAdmin.getId());

        // 初始化message
        message.setState(1);
        message.setTitle("test");
        message.setContent("test");
        message.setBuilding(building);
        message.setAuthor(buildingAdmin);
        message = messageRepository.save(message);
    }

    @After
    public void clean() {
        testRepository.clean();
    }
}

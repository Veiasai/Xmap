package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.repositories.NodeRepository;
import xyz.veiasai.neo4j.repositories.TestRepository;

import java.util.*;

@Service
@Transactional
public class NodeService {
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private TestRepository testRepository;

    public Node addNode(Node node)
    {
        node.setId(null);
        return nodeRepository.save(node);
    }

    @Transactional(readOnly = true)
    public Collection<Node> findByBuildingAndName(String buildingId,String name,Integer skip,Integer limit)
    {
        return nodeRepository.findByBuildingAndNameLike(buildingId,name,skip,limit);
    }

    @Transactional(readOnly = true)
    public Node findById(String id) {
        return nodeRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Collection<Node> findByBuilding(String building,Integer skip,Integer limit)
    {
        return nodeRepository.findByBuildingId(building,skip,limit);
    }


    public Node update(Node node) {return nodeRepository.save(node);};

    @Transactional(readOnly = true)
    public Collection<Node> findByTwoNodeId(String nId1,String nId2, String depth)
    {
        return nodeRepository.findByTwoNodeId(nId1, nId2, depth);
    }
    @Transactional(readOnly = true)
    public TestRepository.RelaData findAllPathsByTwoNodeId(String nId1, String nId2){
        return testRepository.findAllPathsByTwoNodeId(nId1,nId2);
    }

    @Transactional(readOnly = true)
    public Collection<Node> findByOriginNode(String originId, String name,Integer skip,Integer limit) {
        return nodeRepository.findByOriginNode(originId,name,skip,limit);
    }

    @Transactional(readOnly = true)
    public Collection<Node> findByAuthorAndBuilding(String authorId,String buildingId,Integer skip,Integer limit){
        return nodeRepository.findByAuthorAndBuilding(authorId,buildingId,skip,limit);

    }
    @Transactional(readOnly = true)
    public Collection<Node> findByAuthorAndName(String authorId, String name,Integer skip,Integer limit) {
        return nodeRepository.findByAuthorId(authorId,name,skip,limit);
    }
}

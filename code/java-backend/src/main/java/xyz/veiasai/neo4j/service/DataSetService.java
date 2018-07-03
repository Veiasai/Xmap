package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.DataSet;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;
import xyz.veiasai.neo4j.repositories.DataSetRepository;

import java.util.Collection;
import java.util.Iterator;

@Service
@Transactional
public class DataSetService {
    @Autowired
    private DataSetRepository dataSetRepository;

    public DataSet addDataSet(DataSet dataSet, String authorId, String buildingId){
        dataSet.setId(null);
        dataSet = dataSetRepository.save(dataSet);
        String dataSetId = dataSet.getId();
        dataSetRepository.addRelationAuthorAndBuilding(dataSetId, authorId, buildingId);
        return dataSet;
    }

    public void addRelationNodes(String dataSetId, Collection<Node>nodes){
        Iterator it = nodes.iterator();
        while (it.hasNext()){
            Node node = (Node)it.next();
            addRelationNode(dataSetId,node.getId());
        }

    }
    public void addRelationNode(String dataSetId, String nodeId){
        dataSetRepository.addRelationNodeAndDataSet(dataSetId,nodeId);
    }

    public void deleteRelationNodes(String dataSetId,Collection<Node>nodes){
        Iterator it = nodes.iterator();
        while (it.hasNext()){
            Node node = (Node)it.next();
            deleteRelationNode(dataSetId,node.getId());
        }
    }
    public void deleteRelationNode(String dataSetId,String nodeId){
        dataSetRepository.deleteRelationNodeAndDataSet(dataSetId,nodeId);
    }
    public Node searchNode(String dataSetId ,String nodeName){
        return dataSetRepository.SearchNode(dataSetId,nodeName);
    }

    public Collection<Node> searchAllNodes(String dataSetId){
        return dataSetRepository.SearchAllNode(dataSetId);
    }

    public Path searchPath(String dataSetId,String pathName){
        return dataSetRepository.SearchPath(dataSetId,pathName);
    }
    public Collection<Path> searchAllPaths(String dataSetId){
        return dataSetRepository.SearchAllPaths(dataSetId);
    }



}

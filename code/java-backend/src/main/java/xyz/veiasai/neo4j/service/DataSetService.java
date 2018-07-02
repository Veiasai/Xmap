package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.Building;
import xyz.veiasai.neo4j.domain.DataSet;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.repositories.DataSetRepository;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

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

    public void addNodes(DataSet dataSet,Collection<Node>nodes){
        Iterator it = nodes.iterator();
        while (it.hasNext()){
            Node node = (Node)it.next();
            addNode(dataSet,node);
        }
    }
    public void addNode(DataSet dataSet,Node node){
        dataSetRepository.addRelationNodeAndDataSet(dataSet.getId(),node.getId());
    }

}

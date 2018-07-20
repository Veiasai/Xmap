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
import java.util.List;

@Service
@Transactional
public class DataSetService {
    @Autowired
    private DataSetRepository dataSetRepository;

    @Transactional
    public DataSet addDataSet(DataSet dataSet,String buildingId,String authorId){
        dataSet.setId(null);
        dataSet = dataSetRepository.save(dataSet);
        String dataSetId = dataSet.getId();
        dataSetRepository.addRelationAuthorAndBuilding(dataSetId,buildingId,authorId);
        return dataSet;
    }

    @Transactional
    public DataSet getDataSetById(String dataSetId){
        return dataSetRepository.getDataSetById(dataSetId);
    }

    @Transactional
    public Collection<DataSet> getDataSetByName(String dataSetName,Integer skip,Integer limit){
        return dataSetRepository.getDataSetByName(dataSetName, skip, limit);
    }
    @Transactional
    public void deleteDataSet(String dataSetId){
        dataSetRepository.deleteDataSetById(dataSetId);
    }
    @Transactional
    public void addRelationNodes(String dataSetId, List<String>NodeIds){
        Iterator it = NodeIds.iterator();
        while (it.hasNext()){
            String nodeId = it.next().toString();
            addRelationNode(dataSetId,nodeId);
        }

    }
    @Transactional
    public void addRelationNode(String dataSetId, String nodeId){
        dataSetRepository.addRelationNodeAndDataSet(dataSetId,nodeId);
    }
    @Transactional
    public void deleteRelationNode(String dataSetId,String nodeId){
        dataSetRepository.deleteRelationNodeAndDataSet(dataSetId,nodeId);
    }
    @Transactional
    public void deleteRelationNodes(String dataSetId,List<String>NodeIds){
        Iterator it = NodeIds.iterator();
        while (it.hasNext()){
            String nodeId = it.next().toString();
            deleteRelationNode(dataSetId,nodeId);
        }
    }
    @Transactional
    public Collection<Node> findNodesByNameLike(String dataSetId, String nodeName, Integer skip, Integer limit){
        return dataSetRepository.findNodesByNameLike(dataSetId,nodeName,skip,limit);
    }
    @Transactional
    public Collection<Node> searchAllNodes(String dataSetId){
        return dataSetRepository.findAllNodes(dataSetId);
    }
    @Transactional
    public void deleteRelationPath(String dataSetId,String pathId){
        dataSetRepository.deleteRelationPathAndDataSet(dataSetId,pathId);
    }
    @Transactional
    public void addRelationPaths(String dataSetId, List<String>PathIds){
        Iterator it = PathIds.iterator();
        while (it.hasNext()){
            String pathId = it.next().toString();
            addRelationPath(dataSetId,pathId);
        }

    }
    @Transactional
    public void addRelationPath(String dataSetId, String pathId){
        dataSetRepository.addRelationPathAndDataSet(dataSetId,pathId);
    }
    @Transactional
    public void deleteRelationPaths(String dataSetId,List<String>PathIds){
        Iterator it = PathIds.iterator();
        while (it.hasNext()){
            String pathId = it.next().toString();
            deleteRelationPath(dataSetId,pathId);
        }
    }
    @Transactional
    public Collection<Path> findPathByNameLike(String dataSetId, String pathName, Integer skip, Integer limit){
        return dataSetRepository.findPathsByNameLike(dataSetId,pathName,skip,limit);
    }

    @Transactional
    public Collection<DataSet> findDataSetByBuildingAndName(String buildingId,String dataSetName,Integer skip,Integer limit){
        return dataSetRepository.findByBuildingAndName(buildingId,dataSetName,skip,limit);
    }
    @Transactional
    public Collection<DataSet> findDataSetByBuildingAndAuthor(String buildingId,String authorId,Integer skip,Integer limit){
        return dataSetRepository.findByBuildingAndAuthor(buildingId,authorId,skip,limit);
    }
    @Transactional
    public Collection<DataSet> findDataSetByAuthorAndName(String authorId,String dataSetName,Integer skip,Integer limit){
        return dataSetRepository.findByAuthorAndName(authorId,dataSetName,skip,limit);
    }

}

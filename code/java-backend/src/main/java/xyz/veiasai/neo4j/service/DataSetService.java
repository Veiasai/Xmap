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

    @Transactional(readOnly = true)
    public DataSet addDataSet(DataSet dataSet, String buildingId, String authorId) {
        dataSet.setId(null);
        dataSet = dataSetRepository.save(dataSet);
        String dataSetId = dataSet.getId();
        dataSetRepository.addRelationBuildingAndAuthor(dataSetId, buildingId, authorId);
        return dataSet;
    }

    @Transactional
    public DataSet findById(String dataSetId){
        return dataSetRepository.findById(dataSetId).orElse(null);
    }
    @Transactional(readOnly = true)
    public DataSet getDataSetById(String dataSetId) {
        return dataSetRepository.findDataSetById(dataSetId);
    }

    @Transactional(readOnly = true)
    public Collection<DataSet> getDataSetByName(String dataSetName, Integer skip, Integer limit) {
        return dataSetRepository.findDataSetByName(dataSetName, skip, limit);
    }

    @Transactional
    public void deleteDataSetByAuthor(String authorId,String dataSetId) {
        dataSetRepository.deleteDataSetByAuthor(authorId,dataSetId);
    }
    @Transactional
    public void deleteDataSetByAdmin(String dataSetId){
        dataSetRepository.deleteDataSetByAdmin(dataSetId);
    }

    @Transactional(readOnly = true)
    public void addRelationNodes(String dataSetId, List<String> NodeIds) {
        Iterator it = NodeIds.iterator();
        while (it.hasNext()) {
            String nodeId = it.next().toString();
            addRelationNode(dataSetId, nodeId);
        }

    }

    @Transactional(readOnly = true)
    public void addRelationNode(String dataSetId, String nodeId) {
        dataSetRepository.addRelationDataSetAndNode(dataSetId, nodeId);
    }

    @Transactional()
    public void deleteRelationNode(String dataSetId, String nodeId) {
        dataSetRepository.deleteRelationDataSetAndNode(dataSetId, nodeId);
    }

    @Transactional()
    public void deleteRelationNodes(String dataSetId, List<String> NodeIds) {
        for (Object NodeId : NodeIds) {
            String nodeId = NodeId.toString();
            deleteRelationNode(dataSetId, nodeId);
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

    @Transactional()
    public void deleteRelationPath(String dataSetId, String pathId) {
        dataSetRepository.deleteRelationDataSetAndPath(dataSetId, pathId);
    }

    @Transactional(readOnly = true)
    public void addRelationPaths(String dataSetId, List<String> PathIds) {
        for (Object PathId : PathIds) {
            String pathId = PathId.toString();
            addRelationPath(dataSetId, pathId);
        }

    }

    @Transactional(readOnly = true)
    public void addRelationPath(String dataSetId, String pathId) {
        dataSetRepository.addRelationDataSetAndPath(dataSetId, pathId);
    }

    @Transactional(readOnly = true)
    public void deleteRelationPaths(String dataSetId, List<String> PathIds) {
        for (Object PathId : PathIds) {
            String pathId = PathId.toString();
            deleteRelationPath(dataSetId, pathId);
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

    @Transactional(readOnly = true)
    public Collection<DataSet> findDataSetByBuildingAndAuthor(String buildingId, String authorId, Integer skip, Integer limit) {
        return dataSetRepository.findByBuildingAndAuthor(buildingId, authorId, skip, limit);
    }

    @Transactional(readOnly = true)
    public Collection<DataSet> findDataSetByAuthorAndName(String authorId, String dataSetName, Integer skip, Integer limit) {
        return dataSetRepository.findByAuthorAndName(authorId, dataSetName, skip, limit);
    }

    @Transactional
    public boolean existBuildingAndDataSet(String buildingId,String dataSetId){
        return dataSetRepository.countBuildingAndDataSet(buildingId, dataSetId) == 0;
    }

    @Transactional
    public boolean existAuthorAndDataSet(String authorId,String dataSetId){
        return dataSetRepository.countAuthorAndDataSet(authorId, dataSetId)!=0;
    }
}

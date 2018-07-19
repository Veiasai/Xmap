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
        dataSetRepository.addRelationAuthorAndBuilding(dataSetId, buildingId, authorId);
        return dataSet;
    }

    @Transactional(readOnly = true)
    public DataSet getDataSetById(String dataSetId) {
        return dataSetRepository.getDataSetById(dataSetId);
    }

    @Transactional(readOnly = true)
    public Collection<DataSet> getDataSetByName(String dataSetName, Integer skip, Integer limit) {
        return dataSetRepository.getDataSetByName(dataSetName, skip, limit);
    }

    @Transactional(readOnly = true)
    public void deleteDataSet(String dataSetId) {
        dataSetRepository.deleteDataSetById(dataSetId);
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
        dataSetRepository.addRelationNodeAndDataSet(dataSetId, nodeId);
    }

    @Transactional(readOnly = true)
    public void deleteRelationNode(String dataSetId, String nodeId) {
        dataSetRepository.deleteRelationNodeAndDataSet(dataSetId, nodeId);
    }

    @Transactional(readOnly = true)
    public void deleteRelationNodes(String dataSetId, List<String> NodeIds) {
        Iterator it = NodeIds.iterator();
        while (it.hasNext()) {
            String nodeId = it.next().toString();
            deleteRelationNode(dataSetId, nodeId);
        }
    }

    @Transactional(readOnly = true)
    public Collection<Node> searchNodesByNameLike(String dataSetId, String nodeName, Integer skip, Integer limit) {
        return dataSetRepository.SearchNodesByNameLike(dataSetId, nodeName, skip, limit);
    }

    @Transactional(readOnly = true)
    public Collection<Node> searchAllNodes(String dataSetId) {
        return dataSetRepository.SearchAllNodes(dataSetId);
    }

    @Transactional(readOnly = true)
    public void deleteRelationPath(String dataSetId, String pathId) {
        dataSetRepository.deleteRelationPathAndDataSet(dataSetId, pathId);
    }

    @Transactional(readOnly = true)
    public void addRelationPaths(String dataSetId, List<String> PathIds) {
        Iterator it = PathIds.iterator();
        while (it.hasNext()) {
            String pathId = it.next().toString();
            addRelationPath(dataSetId, pathId);
        }

    }

    @Transactional(readOnly = true)
    public void addRelationPath(String dataSetId, String pathId) {
        dataSetRepository.addRelationPathAndDataSet(dataSetId, pathId);
    }

    @Transactional(readOnly = true)
    public void deleteRelationPaths(String dataSetId, List<String> PathIds) {
        Iterator it = PathIds.iterator();
        while (it.hasNext()) {
            String pathId = it.next().toString();
            deleteRelationPath(dataSetId, pathId);
        }
    }

    @Transactional(readOnly = true)
    public Collection<Path> searchPathByNameLike(String dataSetId, String pathName, Integer skip, Integer limit) {
        return dataSetRepository.SearchPathsByNameLike(dataSetId, pathName, skip, limit);
    }

    @Transactional(readOnly = true)
    public Collection<Path> searchAllPaths(String dataSetId) {
        return dataSetRepository.SearchAllPaths(dataSetId);
    }

    @Transactional(readOnly = true)
    public Collection<DataSet> findDataSetByBuildingAndName(String buildingId, String dataSetName, Integer skip, Integer limit) {
        return dataSetRepository.findByBuildingAndName(buildingId, dataSetName, skip, limit);
    }

    @Transactional(readOnly = true)
    public Collection<DataSet> findDataSetByBuildingAndAuthor(String buildingId, String authorId, Integer skip, Integer limit) {
        return dataSetRepository.findByBuildingAndAuthor(buildingId, authorId, skip, limit);
    }

    @Transactional(readOnly = true)
    public Collection<DataSet> findDataSetByAuthorAndName(String authorId, String dataSetName, Integer skip, Integer limit) {
        return dataSetRepository.findByAuthorAndName(authorId, dataSetName, skip, limit);
    }

}

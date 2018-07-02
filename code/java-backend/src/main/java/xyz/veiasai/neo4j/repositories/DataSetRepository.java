package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.DataSet;

public interface DataSetRepository extends Neo4jRepository<DataSet,String> {
    @Query("match (building:Building {id:{buildingId}}), (author:Author {id:{authorId}}), (dataset: DataSet {id:{dataSetId}})" +
            "create (author)-[:AUTHOR]->(dataset), (building)-[:BUILDING]->(dataset)"
    )
    public void addRelationAuthorAndBuilding(@Param("dataSetId") String dataSetId, @Param("buildingId") String buildingId, @Param("authorId") String authorId);

    @Query("Match (node:Node {id:{nodeId}}),(dataset:DataSet {id:{dataSetId}})" +
            "create (node)-[:NODE]->(dataset)"
    )
    public void addRelationNodeAndDataSet(@Param("dataSetId") String dataSetId,@Param("nodeId") String nodeId);


}

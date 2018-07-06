package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.DataSet;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;

import java.util.Collection;

public interface DataSetRepository extends Neo4jRepository<DataSet,String> {

    @Query("Match (d:DataSet{id:{dataSetId}}) return d")
    public DataSet getDataSetById(@Param("dataSetId") String dataSetId);

    @Query("match (building:Building {id:{buildingId}}),(author:Author {id:{authorId}}),(dataset:DataSet {id:{dataSetId}}) " +
            "merge (author)-[:AUTHOR]->(dataset)<-[:BUILDING]-(building)"
    )
    public void addRelationAuthorAndBuilding(@Param("dataSetId") String dataSetId, @Param("buildingId") String buildingId, @Param("authorId") String authorId);

    @Query("Match (node:Node {id:{nodeId}}),(dataset:DataSet {id:{dataSetId}})" +
            "merge (node)-[:NODE]->(dataset)"
    )
    public void addRelationNodeAndDataSet(@Param("dataSetId") String dataSetId, @Param("nodeId") String nodeId);

    @Query("Match (node:Node {id:{nodeId}})-[r:NODE]->(dataset:DataSet {id:{dataSetId}})" +
            "Delete r"
    )
    public void deleteRelationNodeAndDataSet(@Param("dataSetId") String dataSetId, @Param("nodeId") String nodeId);

    @Query("Match (node:Node {name:{nodeName}})-[:NODE]->(dataset:DataSet {id:{dataSetId}})" +
            "return node"
    )
    public Node SearchNode(@Param("dataSetId") String dataSetId, @Param("nodeName") String nodeName);

    @Query("Match (node:Node)-[:NODE]->(dataset:DataSet {id:{dataSetId}}) where node.name =~ ('.*'+{nodeName}+'.*')" +
            "return node"
    )
    public Collection<Node> SearchNodesByNameLike(@Param("dataSetId") String dataSetId, @Param("nodeName") String nodeName);

    @Query("Match (node:Node)-[:NODE]->(dataset:DataSet {id:{dataSetId}})" +
            "return node"
    )
    public Collection<Node> SearchAllNodes(@Param("dataSetId") String dataSetId);


    @Query("Match (p:Path {id:{pathId}}),(dataset:DataSet {id:{dataSetId}})" +
            "merge (p)-[:PATH]->(dataset)"
    )
    public void addRelationPathAndDataSet(@Param("dataSetId") String dataSetId, @Param("pathId") String pathId);

    @Query("Match (p:Path {id:{pathId}})-[r:PATH]->(dataset:DataSet {id:{dataSetId}})" +
            "Delete r"
    )
    public void deleteRelationPathAndDataSet(@Param("dataSetId") String dataSetId, @Param("pathId") String pathId);

    @Query("Match (p:Path)-[:PATH]->(dataset:DataSet {id:{dataSetId}}) where p.name =~('.*'+{pathName}+'.*')" +
            "return p"
    )
    public Collection<Path> SearchPathsByNameLike(@Param("dataSetId") String dataSetId, @Param("pathName") String pathName);

    @Query("Match (p:Path)-[:PATH]->(dataset:DataSet {id:{dataSetId}})" +
            "return p"
    )
    public Collection<Path> SearchAllPaths(@Param("dataSetId") String dataSetId);

    @Query("Match (d:DataSet {id:{dataSetId}}) Detach Delete d")
    public void deleteDataSetById(@Param("dataSetId") String dataSetId);

    @Query("Match (d:DataSet) where d.name =~ ('.*'+{Name}+'.*')"+
            "RETURN d ORDER BY d.name SKIP {skip} LIMIT {limit}")
    public Collection<DataSet> findByNameLike(@Param("Name") String Name,@Param("skip")Integer skip,@Param("limit") Integer limit);
}
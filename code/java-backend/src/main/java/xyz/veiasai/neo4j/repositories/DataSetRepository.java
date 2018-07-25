package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.DataSet;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;

import javax.xml.crypto.Data;
import java.util.Collection;

public interface DataSetRepository extends Neo4jRepository<DataSet, String> {

    @Query("Match (d:DataSet{id:{dataSetId}}) return d")
    public DataSet findDataSetById(@Param("dataSetId") String dataSetId);

    @Query("Match (d:DataSet) where d.name =~ ('.*'+Name+'.*') return d " +
            "order by d.name SKIP{skip} LIMIT{limit} ")
    public Collection<DataSet> findDataSetByName(@Param("Name") String Name, @Param("skip") Integer skip,
                                                 @Param("limit") Integer limit);

    @Query("match (building:Building {id:{buildingId}}),(author:Author {id:{authorId}}),(dataset:DataSet {id:{dataSetId}}) " +
            "merge (author)-[:AUTHOR]->(dataset)<-[:BUILDING]-(building)"
    )
    public void addRelationBuildingAndAuthor(@Param("dataSetId") String dataSetId, @Param("buildingId") String buildingId, @Param("authorId") String authorId);

    @Query("Match (node:Node {id:{nodeId}}),(dataset:DataSet {id:{dataSetId}})" +
            "merge (node)-[:NODE]->(dataset)"
    )
    public void addRelationDataSetAndNode(@Param("dataSetId") String dataSetId, @Param("nodeId") String nodeId);

    @Query("Match (node:Node {id:{nodeId}})-[r:NODE]->(dataset:DataSet {id:{dataSetId}})" +
            "Delete r"
    )
    public void deleteRelationDataSetAndNode(@Param("dataSetId") String dataSetId, @Param("nodeId") String nodeId);

    @Query("Match (node:Node)-[:NODE]->(dataset:DataSet {id:{dataSetId}}) where node.name =~ ('.*'+{nodeName}+'.*')" +
            "RETURN node ORDER BY node.name SKIP {skip} LIMIT {limit}"
    )
    public Collection<Node> findNodesByNameLike(@Param("dataSetId") String dataSetId,
                                                @Param("nodeName") String nodeName,
                                                @Param("skip")Integer skip,
                                                @Param("limit")Integer limit);


    @Query("Match (node:Node)-[:NODE]->(dataset:DataSet {id:{dataSetId}})" +
            "return node"
    )
    public Collection<Node> findAllNodes(@Param("dataSetId") String dataSetId);


    @Query("Match (p:Path {id:{pathId}}),(dataset:DataSet {id:{dataSetId}})" +
            "merge (p)-[:PATH]->(dataset)"
    )
    public void addRelationDataSetAndPath(@Param("dataSetId") String dataSetId, @Param("pathId") String pathId);

    @Query("Match (p:Path {id:{pathId}})-[r:PATH]->(dataset:DataSet {id:{dataSetId}})" +
            "Delete r"
    )
    public void deleteRelationDataSetAndPath(@Param("dataSetId") String dataSetId, @Param("pathId") String pathId);

    @Query("Match (p:Path)-[:PATH]->(dataset:DataSet {id:{dataSetId}}) where p.name =~('.*'+{pathName}+'.*')" +
            "RETURN p ORDER BY p.name SKIP {skip} LIMIT {limit}"
    )
    public Collection<Path> findPathsByNameLike(@Param("dataSetId") String dataSetId,
                                                @Param("pathName") String pathName,
                                                @Param("skip")Integer skip,
                                                @Param("limit")Integer limit);

    @Query("Match (a:Author {id:{authorId}})-[:AUTHOR]-(d:DataSet {id:{dataSetId}}) Detach Delete d")
    public void deleteDataSetByAuthor(@Param("authorId")String authorId,@Param("dataSetId") String dataSetId);

    @Query("Match (d:DataSet)-[:BUILDING]-(b:Building {id:{buildingId}}) where d.name =~ ('.*'+{Name}+'.*')" +
            "RETURN d ORDER BY d.name SKIP {skip} LIMIT {limit}")
    public Collection<DataSet> findByBuildingAndName(@Param("buildingId") String buildingId, @Param("Name") String Name, @Param("skip") Integer skip, @Param("limit") Integer limit);

    @Query("Match (d:DataSet)-[:BUILDING]-(b:Building {id:{buildingId}}),(d:DataSet)-[:AUTHOR]-(a:Author {id:{authorId}})" +
            "RETURN d ORDER BY d.name SKIP {skip} LIMIT {limit}")
    public Collection<DataSet> findByBuildingAndAuthor(@Param("buildingId") String buildingId, @Param("authorId") String authorId, @Param("skip") Integer skip, @Param("limit") Integer limit);

    @Query("Match (d:DataSet)-[:AUTHOR]-(a:Author {id:{authorId}}) where d.name =~ ('.*'+{Name}+'.*')" +
            "RETURN d ORDER BY d.name SKIP {skip} LIMIT {limit}")
    public Collection<DataSet> findByAuthorAndName(@Param("authorId") String authorId, @Param("Name") String Name, @Param("skip") Integer skip, @Param("limit") Integer limit);

    @Query("Match (b:Building{id:{buildingId}})-[r:BUILDING]-(d:DataSet {id:{dataSetId}}) return count(r)")
    public int countBuildingAndDataSet(@Param("buildingId")String buildingId, @Param("pathId") String dataSetId);

    @Query("Match (d:DataSet {id:{dataSetId}}) Detach Delete d")
    public void deleteDataSetByAdmin(@Param("dataSetId") String dataSetId);

    /* 废弃接口
    @Query("Match (p:Path)-[:PATH]->(dataset:DataSet {id:{dataSetId}})" +
            "return p"
    )
    public Collection<Path> SearchAllPaths(@Param("dataSetId") String dataSetId);
     */

}
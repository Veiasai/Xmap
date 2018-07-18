package xyz.veiasai.neo4j.repositories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;
import xyz.veiasai.neo4j.domain.relation.PATH;

import java.util.*;

public interface NodeRepository extends Neo4jRepository<Node, String> {
    @Query("MATCH (b:Building {id:{buildingId}})-[:BUILDING]-(n:Node) where n.name =~ ('.*'+{Name}+'.*')"+
    "RETURN n ORDER BY n.name SKIP {skip} LIMIT {limit}")
    public Collection<Node> findByBuildingAndNameLike(@Param("buildingId")String buildingId,@Param("Name") String Name,@Param("skip") Integer skip,
                                                      @Param("limit")Integer limit);

    @Query("MATCH (:Building {id:{b}})-[BUILDING]-(n:Node) RETURN n" +
            "ORDER BY n.name SKIP {skip} LIMIT {limit}")
    public Collection<Node> findByBuildingId(@Param("b") String building,@Param("skip")Integer skip,@Param("limit")Integer limit);

    @Query("match (n1:Node {id:{nId1}}),(n2:Node {id:{nId2}}), p=allshortestpaths((n1)-[:PATH*..10]-(n2)) return nodes(p) SKIP{skip} LIMIT{limit}; ")
    public Set<Map<String,Object>> findByTwoNodeId(@Param("nId1") String nId1, @Param("nId2") String nId2,@Param("skip")Integer skip,
                                            @Param("limit")Integer limit);

    @Query("  Match (n1:Node {id:{nId1}}),(n2:Node {id:{nId2}}), p=((n1)-[:PATH*..10]->(n2))\n" +
            " UNWIND NODES(p) AS n\n" +
            "    WITH p, \n" +
            "         SIZE(COLLECT(DISTINCT n)) AS testLength \n" +
            "    WHERE testLength = LENGTH(p)+1\n" +
            " RETURN nodes(p) as paths SKIP{skip} LIMIT{limit} ")  //
    public Set<Map<String, Object>> findAllPathsByTwoNodeId(@Param("nId1") String nId1, @Param("nId2") String nId2, @Param("skip")Integer skip,
                                                            @Param("limit")Integer limit);


    @Query("match (n1:Node {id:{nodeId}}),(n2:Node) where n1.id<>n2.id AND n2.name CONTAINS {nodeName}, p=allshortestpaths((n1)-[:PATH*..10]-(n2)) return p; ")
    public Collection<Node> findByTwoNodeIdAndName(@Param("nodeId") String nId1, @Param("nodeName") String nId2, @Param("depth") String depth);

    @Query("match (n:Node {id:{originId}})-[:PATH*..25]->(r:Node) where r.name=~('.*'+{name}+'.*') " +
            "RETURN r ORDER BY r.name SKIP {skip} LIMIT {limit}")
    public Collection<Node> findByOriginNode(@Param("originId") String originId,@Param("name") String name,@Param("skip")Integer skip,@Param("limit")Integer limit);

    @Query("match (n:Author {id:{authorId}})-[:AUTHOR]-(r:Node) where r.name=~('.*'+{name}+'.*')"+
            "RETURN r ORDER BY r.name SKIP {skip} LIMIT {limit}")
    public Collection<Node> findByAuthorId(@Param("authorId") String authorId, @Param("name") String name, @Param("skip")Integer skip,
                                           @Param("limit")Integer limit);

    @Query("Match (n:Node)-[:AUTHOR]-(a:Author{id:{authorId}}), (n:Node)-[:BUILDING]-(b:Building{id:{buildingId}})" +
            "RETURN n ORDER BY n.name SKIP {skip} LIMIT{limit}")
    public Collection<Node> findByAuthorAndBuilding(@Param("authorId")String authorId,@Param("buildingId")String buildingId,@Param("skip")Integer skip,@Param("limit")Integer limit);

    @Query("Match (n:Node {id:{nodeId}})-[:AUTHOR]-(a:Author {id:{authorId}}) detach delete n")
    public void deleteNodeById(@Param("authorId")String authorId,@Param("nodeId")String nodeId);

}

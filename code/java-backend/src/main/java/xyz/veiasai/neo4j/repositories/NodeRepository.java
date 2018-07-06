package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.Node;

import java.util.Collection;

public interface NodeRepository extends Neo4jRepository<Node, String> {
    @Query("MATCH (n:Node) where n.name =~ ('.*'+{Name}+'.*')"+
    "RETURN n ORDER BY n.name SKIP {skip} LIMIT {limit}")
    public Collection<Node> findByNameLike(@Param("Name") String Name,@Param("skip") Integer skip,@Param("limit")Integer limit);

    @Query("MATCH (:Building {id:{b}})-[BUILDING]->(n:Node) RETURN n")
    public Collection<Node> findByBuildingId(@Param("b") String building);

    @Query("match (n1:Node {id:{nId1}}),(n2:Node {id:{nId2}}), p=allshortestpaths((n1)-[:PATH*..10]-(n2)) return p; ")
    public Collection<Node> findByTwoNodeId(@Param("nId1") String nId1, @Param("nId2") String nId2, @Param("depth") String depth);

    @Query("match (n1:Node {id:{nodeId}}),(n2:Node) where n1.id<>n2.id AND n2.name CONTAINS {nodeName}, p=allshortestpaths((n1)-[:PATH*..10]-(n2)) return p; ")
    public Collection<Node> findByTwoNodeIdAndName(@Param("nodeId") String nId1, @Param("nodeName") String nId2, @Param("depth") String depth);

    @Query("match (n:Node {id:{originId}})-[:PATH*..25]->(r:Node) where r.name=~{name} return r")
    public Collection<Node> findByOriginNode(@Param("originId") String originId,@Param("name") String name);

    @Query("match (n:Author {id:{authorId}})-[:AUTHOR]->(r:Node) where r.name=~{name} return r")
    public Collection<Node> findByAuthorId(@Param("authorId") String authorId, @Param("name") String name);
}

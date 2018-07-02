package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;

import java.util.Collection;

public interface PathRepository extends Neo4jRepository<Path, String> {
    public Collection<Path> findByNameLike(String name);

    @Query("MATCH (:Node {id: {nodeId}})-[PATH]->(n:Path) RETURN n")
    public Collection<Path> findByOrigin(@Param("nodeId") String nodeId);

    @Query("MATCH (:Building {id:{buildingId}})-[BUILDING]->(p:Path) WHERE p.name=~{name} RETURN p")
    public Collection<Path> findByBuildingAndName(@Param("buildingId") String buildingId, @Param("name") String name);

    @Query("match (building:Building {id:{buildingId}}), (author:Author {id:{author}}), (path: Path {id:{pathId}})" +
            "create (author)-[:AUTHOR]->(path), (building)-[:BUILDING]->(path)"
    )
    public void addRelationAuthorAndBuilding(@Param("pathId") String pathId, @Param("buildingId") String buildingId, @Param("author") String author);

    @Query("match (origin:Node {id:{origin}}), (end:Node {id:{end}}), (path: Path {id:{pathId}})" +
            "create (origin)-[:PATH]->(path)-[:PATH]->(end)"
    )
    public void addRelationOriginAndEnd(@Param("pathId") String pathId, @Param("origin") String origin, @Param("end") String end);

    @Query("MATCH (n1:Node {id:{originId}}),(n2:Node {id:{endId}}) ," +
            "p = shortestpath((n1)-[:PATH*..25]->(n2)) RETURN p")
    public Collection<Path> findByOriginAndEnd(@Param("originId") String originId, @Param("endId") String endId);

    @Query("match (n:Author {id:{authorId}})-[:AUTHOR]->(r:Path) where r.name=~{name} return r")
    public Collection<Path> findByAuthorId(@Param("authorId") String authorId, @Param("name") String name);
}

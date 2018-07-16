package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;

import java.util.Collection;

public interface PathRepository extends Neo4jRepository<Path, String> {
    @Query("MATCH (p:Path) where p.name =~ ('.*'+{Name}+'.*')"+
            "RETURN p ORDER BY p.name SKIP {skip} LIMIT {limit}")
    public Collection<Path> findByNameLike(@Param("Name") String Name,@Param("skip") Integer skip,@Param("limit")Integer limit);

    @Query("MATCH (:Node {id: {nodeId}})-[PATH]->(n:Path) RETURN n")
    public Collection<Path> findByOrigin(@Param("nodeId") String nodeId);

    @Query("MATCH (:Building {id:{buildingId}})-[:BUILDING]-(p:Path) WHERE p.name=~('.*'+{name}+'.*')"+
            "RETURN p ORDER BY p.name SKIP {skip} LIMIT {limit}")
    public Collection<Path> findByBuildingAndName(@Param("buildingId") String buildingId,
                                                  @Param("name") String name,
                                                  @Param("skip")Integer skip,
                                                  @Param("limit")Integer limit);

    @Query("match (building:Building {id:{buildingId}}), (author:Author {id:{author}}), (path: Path {id:{pathId}})" +
            "merge (author)-[:AUTHOR]-(path)-[:BUILDING]-(building)"
    )
    public void addRelationAuthorAndBuilding(@Param("pathId") String pathId, @Param("buildingId") String buildingId, @Param("author") String author);

    @Query("match (origin:Node {id:{origin}}), (end:Node {id:{end}}), (path: Path {id:{pathId}})" +
            "merge (origin)-[:PATH {node:{origin}, path:{pathId}}]->(path)-[:PATH {node:{end},path:{pathId}}]->(end)"
    )
    public void addRelationOriginAndEnd(@Param("pathId") String pathId, @Param("origin") String origin, @Param("end") String end);

    @Query("MATCH (n1:Node {id:{originId}}),(n2:Node {id:{endId}}) ," +
            "p = shortestpath((n1)-[:PATH*..25]->(n2)) RETURN p")
    public Collection<Path> findByOriginAndEnd(@Param("originId") String originId, @Param("endId") String endId);

    @Query("match (n:Author {id:{authorId}})-[:AUTHOR]->(r:Path) where r.name=~('.*'+{name}+'.*')"+
            "RETURN r ORDER BY r.name SKIP {skip} LIMIT {limit}")
    public Collection<Path> findByAuthorId(@Param("authorId") String authorId,
                                           @Param("name") String name,
                                           @Param("skip")Integer skip,
                                           @Param("limit")Integer limit);

    @Query("Match (p:Path {id:{pathId}})-[:AUTHOR]-(a:Author {id:{authorId}}) detach delete p")
    public void deletePathById(@Param("authorId")String authorId,@Param("pathId")String pathId);
}

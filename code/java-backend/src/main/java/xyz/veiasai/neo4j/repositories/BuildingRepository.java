package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.Building;

public interface BuildingRepository extends Neo4jRepository<Building, String> {
    public Building findByName(String  name);

    @Query("MATCH (:Address {address : {a}})-[`IN-ADDRESS`]-> (res:Building {name: {n}}) RETURN res LIMIT 1")
    public Building findByNameAndAddress(@Param("n") String name, @Param("a") String address);
}


package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.Building;

import java.util.Collection;

public interface BuildingAdminRepository extends Neo4jRepository<Author,String> {
    @Query("Match (a:Author {id:{authorId}}),(b:Building {id:{buildingId}})" +
            " merge (a)-[:BUILDINGADMIN]-(b)")
    public void addBuildingAdmin(@Param("buildingId")String buildingId,@Param("authorId")String authorId);

    @Query("Match (a:Author {id:{authorId}}),(b:Building {id:{buildingId}})" +
            " delete (a)-[:BUILDINGADMIN]-(b)")
    public void deleteBuildingAdmin(@Param("buildingId")String buildingId,@Param("authorId")String authorId);

    @Query("Match (a:Author {id:{authorId}}),(b:Building {id:{buildingId}}),(a)-[r:BUILDINGADMIN]-(b)" +
            " return count(r)")
    public int countBuildingAdmin(@Param("buildingId")String buildingId,@Param("authorId")String authorId);

    @Query("Match (a:Author{id:{authorId}})-[r:BUILDINGADMIN]-(b:Building {id:{buildingId}}) return b")
    public Collection<Building> findBuildingByAdmin(@Param("authorId")String authorId);

    @Query("Match (a:Author)-[:BUILDINGADMIN]-(b:Building {id:{buildingId}}) return a")
    public Collection<Author> findAdminByBuildingId(@Param("buildingId")String buildingId);
}

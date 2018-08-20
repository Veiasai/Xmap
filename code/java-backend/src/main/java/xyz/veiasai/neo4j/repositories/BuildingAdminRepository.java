package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.Building;
import xyz.veiasai.neo4j.domain.CountSum;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface BuildingAdminRepository extends Neo4jRepository<Author, String> {
    @Query("Match (a:Author {id:{authorId}}),(b:Building {id:{buildingId}})" +
            " merge (a)-[:BUILDINGADMIN {state:0}]-(b)")
    public void applyBuildingAdmin(@Param("buildingId") String buildingId, @Param("authorId") String authorId);

    @Query("Match (a:Author {id:{authorId}}),(b:Building {id:{buildingId}}),(a)-[r:BUILDINGADMIN]-(b)" +
            " delete r")
    public void deleteBuildingAdmin(@Param("buildingId") String buildingId, @Param("authorId") String authorId);

    @Query("Match (a:Author {id:{authorId}}),(b:Building {id:{buildingId}}),(a)-[r:BUILDINGADMIN]-(b)" +
            " return count(r)")
    public int countBuildingAdmin(@Param("buildingId") String buildingId, @Param("authorId") String authorId);

    @Query("Match (a:Author {id:{authorId}}),(b:Building {id:{buildingId}}),(a)-[r:BUILDINGADMIN {state:1}]-(b)" +
            " return count(r)")
    public int countValidBuildingAdmin(@Param("buildingId") String buildingId, @Param("authorId") String authorId);

    @Query("Match (a:Author {id:{authorId}}),(b:Building {id:{buildingId}}),(a)-[r:BUILDINGADMIN {state:0}]-(b)" +
            " return count(r)")
    public int countApplyBuildingAdmin(@Param("buildingId") String buildingId, @Param("authorId") String authorId);

    @Query("Match (a:Author{id:{authorId}})-[r:BUILDINGADMIN {state:1}]-(b:Building)  return b")
    public Collection<Building> findBuildingByAdmin(@Param("authorId") String authorId);

    @Query("Match (a:Author{id:{authorId}})-[r:BUILDINGADMIN {state:1}]-(b:Building) " +
            "return b as building," +
            "size((:Node)-[:BUILDING]-(b)) as nodeSum,size((:Path)-[:BUILDING]-(b)) as pathSum, size((:Message)-[:BUILDING]-(b)) as MessageSum ")
    public Collection<CountSum> findBuildingAndCountByAdmin(@Param("authorId") String authorId);

    @Query("Match (a:Author)-[r:BUILDINGADMIN {state:1}]-(b:Building {id:{buildingId}}) return a")
    public Collection<Author> findAdminByBuildingId(@Param("buildingId") String buildingId);

    @Query("Match (a:Author {id:{authorId}}),(b:Building {id:{buildingId}}),(a)-[r:BUILDINGADMIN {state:0}]-(b)" +
            "set r.state = 2 ") //2 means refused
    public void refuseBuildingAdmin(@Param("buildingId") String buildingId, @Param("authorId") String authorId);

    @Query("Match (a:Author {id:{authorId}}),(b:Building {id:{buildingId}}),(a)-[r:BUILDINGADMIN {state:0}]-(b)" +
            "set r.state = 1 ") //1 means success
    public void setBuildingAdmin(@Param("buildingId") String buildingId, @Param("authorId") String authorId);

    @Query("Match (a:Author {id:{authorId}})-[r:BUILDINGADMIN {state: 1}]-(b:Building) return count(r)")
    public int countBuildingByAdmin(@Param("authorId")String authorId);

    @Query("Match r = (:Building)-[:BUILDINGADMIN {state: 0}]-(:Author) return nodes(r) skip{skip} limit{limit}")  // 0 means apply
    public Set<Map<String, Object>> getApply(@Param("skip") int skip,@Param("limit") int limit);

    @Query("Match r = (:Building {id: {bId}})-[:BUILDINGADMIN {state: 0}]-(:Author) return nodes(r) skip{skip} limit{limit}")  // 0 means apply
    public Set<Map<String, Object>> getApplyByBuilding(@Param("bId") String BuildingId, @Param("skip") int skip,@Param("limit") int limit);

    @Query("Match r = (:Building)-[:BUILDINGADMIN {state: 1}]-(:Author) return nodes(r) skip{skip} limit{limit}")  // 1 means success
    public Set<Map<String, Object>> getBuildingAdmin(@Param("skip") int skip,@Param("limit") int limit);

    @Query("Match r = (:Building {id: {bId}})-[:BUILDINGADMIN {state: 1}]-(:Author) return nodes(r) skip{skip} limit{limit}")  // 1 means success
    public Set<Map<String, Object>> getBuildingAdminByBuilding(@Param("bId") String buildingId, @Param("skip") int skip,@Param("limit") int limit);
}

package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.relation.PATH;

import java.util.ArrayList;
import java.util.Collection;

public interface TestRepository extends Neo4jRepository<PATH, Long> {
    @Query("Match (n1:Node {id:{nId1}}),(n2:Node {id:{nId2}}), p=((n1)-[n:PATH*..10]->(n2)) return n; ")
    public RelaData findAllPathsByTwoNodeId(@Param("nId1") String nId1, @Param("nId2") String nId2);

    @QueryResult
    public class RelaData{
        Collection<Collection<PATH>> n;

        public Collection<Collection<PATH>> getN() {
            return n;
        }

        public void setN(Collection<Collection<PATH>> n) {
            this.n = n;
        }
    }
}

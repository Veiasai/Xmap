package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface TestRepository extends Neo4jRepository<Long, Long> {
    @Query("Match (n) detach delete n")
    public Set<Map<String, Object>> clean();
}

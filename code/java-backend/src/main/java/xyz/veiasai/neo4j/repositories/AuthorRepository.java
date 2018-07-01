package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import xyz.veiasai.neo4j.domain.Author;

public interface AuthorRepository extends Neo4jRepository<Author, String> {

}

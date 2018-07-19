package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.Message;

public interface MessageRepository extends Neo4jRepository<Message,String> {
    @Query("Match (m:Message {id:{messageId}})-[:AUTHOR]-(a:Author {id:{authorId}})" +
            "detach delete m")
    public void deleteMessage(@Param("authorId")String authorId,@Param("messageId")String messageId);

    @Query("Match (m:Message {id:{messageId}})-[r:AUTHOR]-(a:Author {id:{authorId}})" +
            "return count(r)")
    public int countMessageAndAuthor(@Param("authorId") String authorId,@Param("messageId") String messageId);
}

package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.Message;

import java.util.Collection;

public interface MessageRepository extends Neo4jRepository<Message, String> {
    @Query("Match (m:Message {id:{messageId}})-[:AUTHOR]-(a:Author {id:{authorId}})" +
            "detach delete m")
    public void deleteMessage(@Param("authorId") String authorId, @Param("messageId") String messageId);

    @Query("Match (m:Message {id:{messageId}})-[r:AUTHOR]-(a:Author {id:{authorId}})" +
            "return count(r)")
    public int countMessageAndAuthor(@Param("authorId") String authorId, @Param("messageId") String messageId);

    @Query("Match (a:Author {id:{authorId}})-[:AUTHOR]-(m:Message)-[:BUILDING]-(b:Building {id:{buildingId}}) return m " +
            "order by m.Date SKIP{skip} LIMIT{limit}") // Date!!
    public Collection<Message> findMessageByAuthorAndBuilding(@Param("buildingId") String buildingId, @Param("authorId") String authorId, @Param("skip") Integer skip,
                                                              @Param("limit") Integer limit);

    @Query("Match (a:Author {id:{authorId}})-[:AUTHOR]-(m:Message) where m.title =~('.*'+{title}+'.*') return m " +
            "order by m.Date SKIP{skip} LIMIT{limit}") // Date!!
    public Collection<Message> findMessageByAuthorAndTitle(@Param("authorId") String authorId,@Param("title") String title, @Param("skip") Integer skip,
                                                   @Param("limit") Integer limit);

    @Query("Match (m:Message)-[:BUILDING]-(b:Building {id:{buildingId}}) where m.title =~('.*'+{title}+'.*') return m " +
            "order by m.Date SKIP{skip} LIMIT{limit}") // Date!!
    public Collection<Message> findMessageByBuildingAndTitle(@Param("buildingId") String buildingId,@Param("title")String title, @Param("skip") Integer skip,
                                                     @Param("limit") Integer limit);
}

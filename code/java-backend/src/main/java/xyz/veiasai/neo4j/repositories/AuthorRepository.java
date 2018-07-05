package xyz.veiasai.neo4j.repositories;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.DataSet;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;

import java.util.Collection;
import java.util.List;

public interface AuthorRepository extends Neo4jRepository<Author, String> {
    @Query("Match (n),(a:Author {id:{authorId}}) where n.id={favoriteId} merge (a)-[:COLLECT]->(n)")
    public void addFavorite(@Param("authorId")String authorId,@Param("favoriteId")String favoriteId);

    @Query("Match (a:Author {id:{authorId}})-[:COLLECT]->(n) where n.id={favoriteId} return count(n) ")
    public int findFavoriteById(@Param("authorId")String authorId,@Param("favoriteId")String favoriteId);

    @Query("Match (a:Author {id:{authorId}})-[r:COLLECT]->(n) where n.id={favoriteId} delete r")
    public void deleteFavorite(@Param("authorId")String authorId,@Param("favoriteId")String favoriteId);

    @Query("Match (a:Author{id:{authorId}}) return a")
    public Author getAuthorById(@Param("authorId")String authorId);

    @Query("Match (a:Author {id:{authorId}})-[:COLLECT]->(n:Node) where n.name =~ ('.*'+{nodeName}+'.*') return n")
    public Collection<Node> findNodeByNameLike(@Param("authorId")String authorId, @Param("nodeName") String nodeName);

    @Query("Match (a:Author {id:{authorId}})-[:COLLECT]->(n:Path) where n.name =~ ('.*'+{pathName}+'.*') return n")
    public Collection<Path> findPathByNameLike(@Param("authorId")String authorId, @Param("pathName") String pathName);

    @Query("Match (a:Author {id:{authorId}})-[:COLLECT]->(n:DataSet) where n.name =~ ('.*'+{datasetName}+'.*') return n")
    public Collection<DataSet> findDataSetByNameLike(@Param("authorId")String authorId, @Param("datasetName") String datasetName);


}

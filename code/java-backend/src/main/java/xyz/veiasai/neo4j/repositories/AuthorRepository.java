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
    @Query("Match (n {id:{favoriteId}}),(a:Author {id:{authorId}}) merge (a)-[:COLLECT]->(n)")
    public void addFavorite(@Param("authorId") String authorId, @Param("favoriteId") String favoriteId);

    @Query("Match (n {id:{favoriteId}}) where labels(n)[0] in ['Node','Path','DataSet'] return count(n)")
    public int FavorExistInDb(@Param("favoriteId") String favoriteId);

    @Query("Match (a:Author {id:{authorId}})-[:COLLECT]->(n {id:{favoriteId}}) return count(n) ")
    public int findFavoriteById(@Param("authorId") String authorId, @Param("favoriteId") String favoriteId);

    @Query("Match (a:Author {id:{authorId}})-[r:COLLECT]->(n {id:{favoriteId}}) delete r")
    public void deleteFavorite(@Param("authorId") String authorId, @Param("favoriteId") String favoriteId);

    @Query("MATCH (a:Author {id:{authorId}})-[:COLLECT]->(n:Node) WHERE n.name =~ ('.*'+{nodeName}+'.*')" +
            "RETURN n ORDER BY n.name SKIP {skip} LIMIT {limit}")
    public Collection<Node> findNodeByNameLike(@Param("authorId") String authorId,
                                               @Param("nodeName") String nodeName,
                                               @Param("skip") Integer skip,
                                               @Param("limit") Integer limit);

    @Query("MATCH (a:Author {id:{authorId}})-[:COLLECT]->(n:Path) WHERE n.name =~ ('.*'+{pathName}+'.*') " +
            "RETURN n ORDER BY n.name SKIP {skip} LIMIT {limit}")
    public Collection<Path> findPathByNameLike(@Param("authorId") String authorId,
                                               @Param("pathName") String pathName,
                                               @Param("skip") Integer skip,
                                               @Param("limit") Integer limit);

    @Query("MATCH (a:Author {id:{authorId}})-[:COLLECT]->(n:DataSet) WHERE n.name =~ ('.*'+{datasetName}+'.*')" +
            "RETURN n ORDER BY n.name SKIP {skip} LIMIT {limit}")
    public Collection<DataSet> findDataSetByNameLike(@Param("authorId") String authorId,
                                                     @Param("datasetName") String datasetName,
                                                     @Param("skip") Integer skip,
                                                     @Param("limit") Integer limit);


}

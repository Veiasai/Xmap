package xyz.veiasai.neo4j.service;

import org.neo4j.driver.v1.types.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.DataSet;
import xyz.veiasai.neo4j.domain.Favorite;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.repositories.AuthorRepository;
import xyz.veiasai.neo4j.result.DataSetResult;
import xyz.veiasai.neo4j.result.FavoriteResult;
import xyz.veiasai.neo4j.result.NodeResult;
import xyz.veiasai.neo4j.result.PathResult;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Author addAuthor(Author author)
    {
        Optional<Author> optionalAuthor = authorRepository.findById(author.getId());
        return optionalAuthor.orElse(authorRepository.save(author));
    }

    @Transactional(readOnly = true)
    public Author findById(String id)
    {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        return optionalAuthor.orElse(new Author(id));
    }

    @Transactional
    public Author getAuthorById(String authorId){
        return authorRepository.getAuthorById(authorId);
    }

    @Transactional
    public boolean FavoriteIsExistInDb(String favoriteId){
        if(authorRepository.FavorExistInDb(favoriteId)!=0){
            return true;
        }
        return false;
    }
    @Transactional
    public boolean FavoriteIsExistInAuthor(String authorId,String favoriteId){
        if(authorRepository.findFavoriteById(authorId,favoriteId)!=0){
            return true;
        }
        return false;
    }
    @Transactional
    public void addFavorite(String authorId,String favoriteId){
        authorRepository.addFavorite(authorId,favoriteId);
    }
    @Transactional
    public void deleteFavorite(String auhtorId,String favoriteId){
        authorRepository.deleteFavorite(auhtorId,favoriteId);
    }
    @Transactional
    public FavoriteResult findFavoriteByNameLike(String authorId, String favoriteName,Integer skip,Integer limit){
        FavoriteResult favoriteResult= new FavoriteResult();
        favoriteResult.setDataSets(authorRepository.findDataSetByNameLike(authorId,favoriteName,skip,limit));
        favoriteResult.setNodes(authorRepository.findNodeByNameLike(authorId,favoriteName,skip,limit));
        favoriteResult.setPaths(authorRepository.findPathByNameLike(authorId,favoriteName,skip,limit));
        return favoriteResult;
    }
    @Transactional
    public NodeResult findfavorNodeByNameLike(String authorId, String nodeName,Integer skip,Integer limit){
        NodeResult nodeResult = new NodeResult();
        nodeResult.setNodes(authorRepository.findNodeByNameLike(authorId,nodeName,skip,limit));
        return nodeResult;
    }

    @Transactional
    public PathResult findfavorPathByNameLike(String authorId, String pathName, Integer skip, Integer limit){
        PathResult pathResult = new PathResult();
        pathResult.setPaths(authorRepository.findPathByNameLike(authorId,pathName,skip,limit));
        return pathResult;
    }
    @Transactional
    public DataSetResult findfavorDataSetByNameLike(String authorId, String dataSetName, Integer skip, Integer limit){
        DataSetResult dataSetResult = new DataSetResult();
        dataSetResult.setDataSets(authorRepository.findDataSetByNameLike(authorId,dataSetName,skip,limit));
        return dataSetResult;
    }
}

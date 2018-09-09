package xyz.xmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xmap.domain.Author;
import xyz.xmap.repositories.AuthorRepository;
import xyz.xmap.repositories.DataSetRepository;
import xyz.xmap.repositories.NodeRepository;
import xyz.xmap.repositories.PathRepository;
import xyz.xmap.result.DataSetResult;
import xyz.xmap.result.FavoriteResult;
import xyz.xmap.result.NodeResult;
import xyz.xmap.result.PathResult;

import java.util.Optional;

@Service
@Transactional
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private DataSetRepository dataSetRepository;

    @Transactional(readOnly = true)
    public Author addAuthor(Author author) {
        Optional<Author> optionalAuthor = authorRepository.findById(author.getId());
        return optionalAuthor.orElseGet(()->authorRepository.save(author));
    }

    @Transactional(readOnly = true)
    public Author findById(String id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        return optionalAuthor.orElse(new Author(id));
    }

    @Transactional(readOnly = true)
    public Author getAuthorById(String authorId) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        return optionalAuthor.orElse(null);
    }

    @Transactional(readOnly = true)
    public boolean FavoriteIsExistInDb(String favoriteId) {          //判断favorite是否存在
        if(authorRepository.FavorExistInDb(favoriteId)!=0){
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public boolean FavoriteIsExistInAuthor(String authorId, String favoriteId) {      //判断用户是否收藏
        if (authorRepository.findFavoriteById(authorId, favoriteId) != 0) {
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public void addFavorite(String authorId, String favoriteId) {
        authorRepository.addFavorite(authorId, favoriteId);
    }

    @Transactional(readOnly = true)
    public void deleteFavorite(String auhtorId, String favoriteId) {
        authorRepository.deleteFavorite(auhtorId, favoriteId);
    }

    @Transactional(readOnly = true)
    public FavoriteResult findFavoriteByNameLike(String authorId, String favoriteName, Integer skip, Integer limit) {
        FavoriteResult favoriteResult = new FavoriteResult();
        favoriteResult.setDataSets(authorRepository.findDataSetByNameLike(authorId, favoriteName, skip, limit));
        favoriteResult.setNodes(authorRepository.findNodeByNameLike(authorId, favoriteName, skip, limit));
        favoriteResult.setPaths(authorRepository.findPathByNameLike(authorId, favoriteName, skip, limit));
        return favoriteResult;
    }

    @Transactional(readOnly = true)
    public NodeResult findfavorNodeByNameLike(String authorId, String nodeName, Integer skip, Integer limit) {
        NodeResult nodeResult = new NodeResult();
        nodeResult.setNodes(authorRepository.findNodeByNameLike(authorId, nodeName, skip, limit));
        return nodeResult;
    }

    @Transactional(readOnly = true)
    public PathResult findfavorPathByNameLike(String authorId, String pathName, Integer skip, Integer limit) {
        PathResult pathResult = new PathResult();
        pathResult.setPaths(authorRepository.findPathByNameLike(authorId, pathName, skip, limit));
        return pathResult;
    }

    @Transactional(readOnly = true)
    public DataSetResult findfavorDataSetByNameLike(String authorId, String dataSetName, Integer skip, Integer limit) {
        DataSetResult dataSetResult = new DataSetResult();
        dataSetResult.setDataSets(authorRepository.findDataSetByNameLike(authorId, dataSetName, skip, limit));
        return dataSetResult;
    }
}

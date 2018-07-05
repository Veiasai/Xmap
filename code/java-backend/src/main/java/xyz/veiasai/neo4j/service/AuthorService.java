package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.domain.Favorite;
import xyz.veiasai.neo4j.repositories.AuthorRepository;
import xyz.veiasai.neo4j.result.FavoriteResult;

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
    public  boolean FavoriteIsExist(String authorId,String favouriteId){
        if(authorRepository.findFavoriteById(authorId,favouriteId)!=0){
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
    public FavoriteResult findFavoriteByNameLike(String authorId, String favoriteName){
        FavoriteResult favoriteResult= new FavoriteResult();
        favoriteResult.setDataSets(authorRepository.findDataSetByNameLike(authorId,favoriteName));
        favoriteResult.setNodes(authorRepository.findNodeByNameLike(authorId,favoriteName));
        favoriteResult.setPaths(authorRepository.findPathByNameLike(authorId,favoriteName));
        return favoriteResult;
    }
}

package xyz.veiasai.neo4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.veiasai.neo4j.domain.Author;
import xyz.veiasai.neo4j.repositories.AuthorRepository;

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
}

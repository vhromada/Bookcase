package cz.vhromada.book.service;

import cz.vhromada.book.domain.Author;
import cz.vhromada.book.repository.AuthorRepository;
import cz.vhromada.common.service.AbstractMovableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

/**
 * A class represents implementation of service for authors.
 *
 * @author Vladimir Hromada
 */
@Component("authorService")
public class AuthorService extends AbstractMovableService<Author> {

    /**
     * Creates a new instance of AuthorService.
     *
     * @param authorRepository repository for authors
     * @param cache            cache
     * @throws IllegalArgumentException if repository for authors is null
     *                                  or cache is null
     */
    @Autowired
    public AuthorService(final AuthorRepository authorRepository, @Value("#{cacheManager.getCache('bookcaseCache')}") final Cache cache) {
        super(authorRepository, cache, "authors");
    }

    @Override
    protected Author getCopy(final Author data) {
        final Author newAuthor = new Author();
        newAuthor.setFirstName(data.getFirstName());
        newAuthor.setMiddleName(data.getMiddleName());
        newAuthor.setLastName(data.getLastName());
        newAuthor.setPosition(data.getPosition());

        return newAuthor;
    }

}

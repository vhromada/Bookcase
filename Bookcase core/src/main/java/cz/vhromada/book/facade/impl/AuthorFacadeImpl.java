package cz.vhromada.book.facade.impl;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.facade.AuthorFacade;
import cz.vhromada.common.facade.AbstractMovableParentFacade;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.validator.MovableValidator;
import cz.vhromada.converter.Converter;

import org.springframework.stereotype.Component;

/**
 * A facade represents implementation of facade for authors.
 *
 * @author Vladimir Hromada
 */
@Component("authorFacade")
public class AuthorFacadeImpl extends AbstractMovableParentFacade<Author, cz.vhromada.book.domain.Author> implements AuthorFacade {

    /**
     * Creates a new instance of AuthorFacadeImpl.
     *
     * @param authorService   service for authors
     * @param converter       converter
     * @param authorValidator validator for author
     * @throws IllegalArgumentException if service for authors is null
     *                                  or converter is null
     *                                  or validator for author is null
     */
    public AuthorFacadeImpl(final MovableService<cz.vhromada.book.domain.Author> authorService, final Converter converter,
        final MovableValidator<Author> authorValidator) {
        super(authorService, converter, authorValidator);
    }

    @Override
    protected Class<Author> getEntityClass() {
        return Author.class;
    }

    @Override
    protected Class<cz.vhromada.book.domain.Author> getDomainClass() {
        return cz.vhromada.book.domain.Author.class;
    }

}

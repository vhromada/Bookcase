package cz.vhromada.book.facade.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.utils.AuthorUtils;
import cz.vhromada.common.facade.MovableParentFacade;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.test.facade.MovableParentFacadeTest;
import cz.vhromada.common.validator.MovableValidator;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;

/**
 * A class represents test for class {@link AuthorFacadeImpl}.
 *
 * @author Vladimir Hromada
 */
class AuthorFacadeImplTest extends MovableParentFacadeTest<Author, cz.vhromada.book.domain.Author> {

    /**
     * Test method for {@link AuthorFacadeImpl#AuthorFacadeImpl(MovableService, Converter, MovableValidator)} with null service for authors.
     */
    @Test
    void constructor_NullAuthorService() {
        assertThatThrownBy(() -> new AuthorFacadeImpl(null, getConverter(), getMovableValidator())).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link AuthorFacadeImpl#AuthorFacadeImpl(MovableService, Converter, MovableValidator)} with null converter.
     */
    @Test
    void constructor_NullConverter() {
        assertThatThrownBy(() -> new AuthorFacadeImpl(getMovableService(), null, getMovableValidator())).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link AuthorFacadeImpl#AuthorFacadeImpl(MovableService, Converter, MovableValidator)} with null validator for author.
     */
    @Test
    void constructor_NullAuthorValidator() {
        assertThatThrownBy(() -> new AuthorFacadeImpl(getMovableService(), getConverter(), null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Override
    protected MovableParentFacade<Author> getMovableParentFacade() {
        return new AuthorFacadeImpl(getMovableService(), getConverter(), getMovableValidator());
    }

    @Override
    protected Author newEntity(final Integer id) {
        return AuthorUtils.newAuthor(id);
    }

    @Override
    protected cz.vhromada.book.domain.Author newDomain(final Integer id) {
        return AuthorUtils.newAuthorDomain(id);
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

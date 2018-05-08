package cz.vhromada.book.facade.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cz.vhromada.book.entity.Book;
import cz.vhromada.book.utils.BookUtils;
import cz.vhromada.common.facade.MovableParentFacade;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.test.facade.MovableParentFacadeTest;
import cz.vhromada.common.validator.MovableValidator;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;

/**
 * A class represents test for class {@link BookFacadeImpl}.
 *
 * @author Vladimir Hromada
 */
class BookFacadeImplTest extends MovableParentFacadeTest<Book, cz.vhromada.book.domain.Book> {

    /**
     * Test method for {@link BookFacadeImpl#BookFacadeImpl(MovableService, Converter, MovableValidator)} with null service for books.
     */
    @Test
    void constructor_NullBookService() {
        assertThatThrownBy(() -> new BookFacadeImpl(null, getConverter(), getMovableValidator())).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link BookFacadeImpl#BookFacadeImpl(MovableService, Converter, MovableValidator)} with null converter.
     */
    @Test
    void constructor_NullConverter() {
        assertThatThrownBy(() -> new BookFacadeImpl(getMovableService(), null, getMovableValidator())).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link BookFacadeImpl#BookFacadeImpl(MovableService, Converter, MovableValidator)} with null validator for book.
     */
    @Test
    void constructor_NullBookValidator() {
        assertThatThrownBy(() -> new BookFacadeImpl(getMovableService(), getConverter(), null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Override
    protected MovableParentFacade<Book> getMovableParentFacade() {
        return new BookFacadeImpl(getMovableService(), getConverter(), getMovableValidator());
    }

    @Override
    protected Book newEntity(final Integer id) {
        return BookUtils.newBook(id);
    }

    @Override
    protected cz.vhromada.book.domain.Book newDomain(final Integer id) {
        return BookUtils.newBookDomain(id);
    }

    @Override
    protected Class<Book> getEntityClass() {
        return Book.class;
    }

    @Override
    protected Class<cz.vhromada.book.domain.Book> getDomainClass() {
        return cz.vhromada.book.domain.Book.class;
    }

}

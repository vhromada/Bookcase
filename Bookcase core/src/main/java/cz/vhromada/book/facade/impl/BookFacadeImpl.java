package cz.vhromada.book.facade.impl;

import cz.vhromada.book.entity.Book;
import cz.vhromada.book.facade.BookFacade;
import cz.vhromada.common.facade.AbstractMovableParentFacade;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.validator.MovableValidator;
import cz.vhromada.converter.Converter;

import org.springframework.stereotype.Component;

/**
 * A facade represents implementation of facade for books.
 *
 * @author Vladimir Hromada
 */
@Component("bookFacade")
public class BookFacadeImpl extends AbstractMovableParentFacade<Book, cz.vhromada.book.domain.Book> implements BookFacade {

    /**
     * Creates a new instance of BookFacadeImpl.
     *
     * @param bookService   service for books
     * @param converter     converter
     * @param bookValidator validator for book
     * @throws IllegalArgumentException if service for books is null
     *                                  or converter is null
     *                                  or validator for book is null
     */
    public BookFacadeImpl(final MovableService<cz.vhromada.book.domain.Book> bookService, final Converter converter,
        final MovableValidator<Book> bookValidator) {
        super(bookService, converter, bookValidator);
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

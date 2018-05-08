package cz.vhromada.book.service;

import java.util.ArrayList;

import cz.vhromada.book.domain.Book;
import cz.vhromada.book.repository.BookRepository;
import cz.vhromada.common.service.AbstractMovableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

/**
 * A class represents implementation of service for books.
 *
 * @author Vladimir Hromada
 */
@Component("bookService")
public class BookService extends AbstractMovableService<Book> {

    /**
     * Creates a new instance of BookService.
     *
     * @param bookRepository repository for books
     * @param cache          cache
     * @throws IllegalArgumentException if repository for books is null
     *                                  or cache is null
     */
    @Autowired
    public BookService(final BookRepository bookRepository, @Value("#{cacheManager.getCache('bookcaseCache')}") final Cache cache) {
        super(bookRepository, cache, "books");
    }

    @Override
    protected Book getCopy(final Book data) {
        final Book newBook = new Book();
        newBook.setCzechName(data.getCzechName());
        newBook.setOriginalName(data.getOriginalName());
        newBook.setLanguages(new ArrayList<>(data.getLanguages()));
        newBook.setIsbn(data.getIsbn());
        newBook.setIssueYear(data.getIssueYear());
        newBook.setDescription(data.getDescription());
        newBook.setElectronic(data.getElectronic());
        newBook.setPaper(data.getPaper());
        newBook.setNote(data.getNote());
        newBook.setPosition(data.getPosition());
        newBook.setAuthors(new ArrayList<>(data.getAuthors()));
        newBook.setCategories(new ArrayList<>(data.getCategories()));

        return newBook;
    }

}

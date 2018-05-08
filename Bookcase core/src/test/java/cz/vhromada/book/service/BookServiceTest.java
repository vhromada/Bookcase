package cz.vhromada.book.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cz.vhromada.book.domain.Book;
import cz.vhromada.book.repository.BookRepository;
import cz.vhromada.book.utils.BookUtils;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.test.service.MovableServiceTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.cache.Cache;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A class represents test for class {@link BookService}.
 *
 * @author Vladimir Hromada
 */
class BookServiceTest extends MovableServiceTest<Book> {

    /**
     * Instance of {@link BookRepository}
     */
    @Mock
    private BookRepository bookRepository;

    /**
     * Test method for {@link BookService#BookService(BookRepository, Cache)} with null repository for books.
     */
    @Test
    void constructor_NullBookRepository() {
        assertThatThrownBy(() -> new BookService(null, getCache())).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link BookService#BookService(BookRepository, Cache)} with null cache.
     */
    @Test
    void constructor_NullCache() {
        assertThatThrownBy(() -> new BookService(bookRepository, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Override
    protected JpaRepository<Book, Integer> getRepository() {
        return bookRepository;
    }

    @Override
    protected MovableService<Book> getMovableService() {
        return new BookService(bookRepository, getCache());
    }

    @Override
    protected String getCacheKey() {
        return "books";
    }

    @Override
    protected Book getItem1() {
        return BookUtils.newBookDomain(1);
    }

    @Override
    protected Book getItem2() {
        return BookUtils.newBookDomain(2);
    }

    @Override
    protected Book getAddItem() {
        return BookUtils.newBookDomain(null);
    }

    @Override
    protected Book getCopyItem() {
        final Book book = BookUtils.newBookDomain(1);
        book.setId(null);

        return book;
    }

    @Override
    protected Class<Book> getItemClass() {
        return Book.class;
    }

    @Override
    protected void assertDataDeepEquals(final Book expected, final Book actual) {
        BookUtils.assertBookDeepEquals(expected, actual);
    }

}

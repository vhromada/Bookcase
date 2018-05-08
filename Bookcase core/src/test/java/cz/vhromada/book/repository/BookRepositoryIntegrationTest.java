package cz.vhromada.book.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;

import cz.vhromada.book.CoreTestConfiguration;
import cz.vhromada.book.domain.Book;
import cz.vhromada.book.utils.AuthorUtils;
import cz.vhromada.book.utils.BookUtils;
import cz.vhromada.book.utils.CategoryUtils;
import cz.vhromada.common.utils.CollectionUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * A class represents integration test for class {@link BookRepository}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreTestConfiguration.class)
@Transactional
@Rollback
class BookRepositoryIntegrationTest {

    /**
     * Instance of {@link EntityManager}
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private EntityManager entityManager;

    /**
     * Instance of {@link BookRepository}
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * Test method for get books.
     */
    @Test
    void getBooks() {
        final List<Book> books = bookRepository.findAll();

        BookUtils.assertBooksDeepEquals(BookUtils.getBooks(), books);

        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT);
    }

    /**
     * Test method for get book.
     */
    @Test
    void getBook() {
        for (int i = 1; i < BookUtils.BOOKS_COUNT; i++) {
            final Book book = bookRepository.findById(i).orElse(null);

            BookUtils.assertBookDeepEquals(BookUtils.getBook(i), book);
        }

        assertThat(bookRepository.findById(Integer.MAX_VALUE).isPresent()).isFalse();

        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT);
    }

    /**
     * Test method for add book.
     */
    @Test
    void add() {
        final Book book = BookUtils.newBookDomain(null);
        book.setPosition(BookUtils.BOOKS_COUNT);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.getAuthor(entityManager, 1)));
        book.setCategories(CollectionUtils.newList(CategoryUtils.getCategory(entityManager, 1)));

        bookRepository.save(book);

        assertThat(book.getId()).isEqualTo(BookUtils.BOOKS_COUNT + 1);

        final Book addedBook = BookUtils.getBook(entityManager, BookUtils.BOOKS_COUNT + 1);
        final Book expectedAddBook = BookUtils.newBookDomain(null);
        expectedAddBook.setId(BookUtils.BOOKS_COUNT + 1);
        expectedAddBook.setPosition(BookUtils.BOOKS_COUNT);
        expectedAddBook.setAuthors(CollectionUtils.newList(AuthorUtils.getAuthorDomain(1)));
        expectedAddBook.setCategories(CollectionUtils.newList(CategoryUtils.getCategoryDomain(1)));
        BookUtils.assertBookDeepEquals(expectedAddBook, addedBook);

        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT + 1);
    }

    /**
     * Test method for update book.
     */
    @Test
    void update() {
        final Book book = BookUtils.updateBook(entityManager, 1);

        bookRepository.save(book);

        final Book updatedBook = BookUtils.getBook(entityManager, 1);
        final Book expectedUpdatedBook = BookUtils.getBook(1);
        BookUtils.updateBook(expectedUpdatedBook);
        expectedUpdatedBook.setPosition(BookUtils.POSITION);
        BookUtils.assertBookDeepEquals(expectedUpdatedBook, updatedBook);

        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT);
    }

    /**
     * Test method for remove book.
     */
    @Test
    void remove() {
        bookRepository.delete(BookUtils.getBook(entityManager, 1));

        assertThat(BookUtils.getBook(entityManager, 1)).isNull();

        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT - 1);
    }

    /**
     * Test method for remove all books.
     */
    @Test
    void removeAll() {
        bookRepository.deleteAll();

        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(0);
    }

}

package cz.vhromada.book.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.book.CoreTestConfiguration;
import cz.vhromada.book.entity.Book;
import cz.vhromada.book.utils.BookUtils;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter between {@link cz.vhromada.book.domain.Book} and {@link Book}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreTestConfiguration.class)
class BookConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from domain to entity.
     */
    @Test
    void convertBookDomain() {
        final cz.vhromada.book.domain.Book bookDomain = BookUtils.newBookDomain(1);
        final Book book = converter.convert(bookDomain, Book.class);

        BookUtils.assertBookDeepEquals(book, bookDomain);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from domain to entity with null book.
     */
    @Test
    void convertBookDomain_NullBook() {
        assertThat(converter.convert(null, Book.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to domain.
     */
    @Test
    void convertBook() {
        final Book book = BookUtils.newBook(1);
        final cz.vhromada.book.domain.Book bookDomain = converter.convert(book, cz.vhromada.book.domain.Book.class);

        BookUtils.assertBookDeepEquals(book, bookDomain);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to domain with null book.
     */
    @Test
    void convertBook_NullBook() {
        assertThat(converter.convert(null, cz.vhromada.book.domain.Book.class)).isNull();
    }

}

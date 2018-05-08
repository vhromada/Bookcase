package cz.vhromada.book.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.book.entity.Book;
import cz.vhromada.book.web.common.BookUtils;
import cz.vhromada.book.web.fo.BookFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link BookFO} to {@link Book}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class BookConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    void convertBookFO() {
        final BookFO bookFO = BookUtils.getBookFO();

        final Book book = converter.convert(bookFO, Book.class);

        BookUtils.assertBookDeepEquals(bookFO, book);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for book.
     */
    @Test
    void convertBookFO_NullBookFO() {
        assertThat(converter.convert(null, Book.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    void convertBook() {
        final Book book = BookUtils.getBook();

        final BookFO bookFO = converter.convert(book, BookFO.class);

        BookUtils.assertBookDeepEquals(book, bookFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null book.
     */
    @Test
    void convertBook_NullBook() {
        assertThat(converter.convert(null, BookFO.class)).isNull();
    }

}

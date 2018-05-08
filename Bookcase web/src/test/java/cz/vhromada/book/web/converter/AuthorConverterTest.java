package cz.vhromada.book.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.web.common.AuthorUtils;
import cz.vhromada.book.web.fo.AuthorFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link AuthorFO} to {@link Author}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class AuthorConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    void convertAuthorFO() {
        final AuthorFO authorFO = AuthorUtils.getAuthorFO();

        final Author author = converter.convert(authorFO, Author.class);

        AuthorUtils.assertAuthorDeepEquals(authorFO, author);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for author.
     */
    @Test
    void convertAuthorFO_NullAuthorFO() {
        assertThat(converter.convert(null, Author.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    void convertAuthor() {
        final Author author = AuthorUtils.getAuthor();

        final AuthorFO authorFO = converter.convert(author, AuthorFO.class);

        AuthorUtils.assertAuthorDeepEquals(authorFO, author);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null author.
     */
    @Test
    void convertAuthor_NullAuthor() {
        assertThat(converter.convert(null, AuthorFO.class)).isNull();
    }

}

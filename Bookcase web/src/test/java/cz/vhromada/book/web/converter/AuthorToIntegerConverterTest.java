package cz.vhromada.book.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.web.common.AuthorUtils;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link Author} to {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class AuthorToIntegerConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to integer.
     */
    @Test
    void convertAuthor() {
        final Author author = AuthorUtils.getAuthor();

        final Integer result = converter.convert(author, Integer.class);

        AuthorUtils.assertAuthorDeepEquals(author, result);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to integer with null argument.
     */
    @Test
    void convertAuthor_NullAuthor() {
        assertThat(converter.convert(null, Integer.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to entity.
     */
    @Test
    void convertInteger() {
        final Author author = converter.convert(1, Author.class);

        AuthorUtils.assertAuthorDeepEquals(1, author);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to entity with null argument.
     */
    @Test
    void convertInteger_NullInteger() {
        assertThat(converter.convert(null, Integer.class)).isNull();
    }

}

package cz.vhromada.book.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.book.CoreTestConfiguration;
import cz.vhromada.book.entity.Author;
import cz.vhromada.book.utils.AuthorUtils;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter between {@link cz.vhromada.book.domain.Author} and {@link Author}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreTestConfiguration.class)
class AuthorConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from domain to entity.
     */
    @Test
    void convertAuthorDomain() {
        final cz.vhromada.book.domain.Author authorDomain = AuthorUtils.newAuthorDomain(1);
        final Author author = converter.convert(authorDomain, Author.class);

        AuthorUtils.assertAuthorDeepEquals(author, authorDomain);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from domain to entity with null author.
     */
    @Test
    void convertAuthorDomain_NullAuthor() {
        assertThat(converter.convert(null, Author.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to domain.
     */
    @Test
    void convertAuthor() {
        final Author author = AuthorUtils.newAuthor(1);
        final cz.vhromada.book.domain.Author authorDomain = converter.convert(author, cz.vhromada.book.domain.Author.class);

        AuthorUtils.assertAuthorDeepEquals(author, authorDomain);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to domain with null author.
     */
    @Test
    void convertAuthor_NullAuthor() {
        assertThat(converter.convert(null, cz.vhromada.book.domain.Author.class)).isNull();
    }

}

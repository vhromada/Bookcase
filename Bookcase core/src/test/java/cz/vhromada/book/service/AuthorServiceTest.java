package cz.vhromada.book.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cz.vhromada.book.domain.Author;
import cz.vhromada.book.repository.AuthorRepository;
import cz.vhromada.book.utils.AuthorUtils;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.test.service.MovableServiceTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.cache.Cache;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A class represents test for class {@link AuthorService}.
 *
 * @author Vladimir Hromada
 */
class AuthorServiceTest extends MovableServiceTest<Author> {

    /**
     * Instance of {@link AuthorRepository}
     */
    @Mock
    private AuthorRepository authorRepository;

    /**
     * Test method for {@link AuthorService#AuthorService(AuthorRepository, Cache)} with null repository for authors.
     */
    @Test
    void constructor_NullAuthorRepository() {
        assertThatThrownBy(() -> new AuthorService(null, getCache())).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link AuthorService#AuthorService(AuthorRepository, Cache)} with null cache.
     */
    @Test
    void constructor_NullCache() {
        assertThatThrownBy(() -> new AuthorService(authorRepository, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Override
    protected JpaRepository<Author, Integer> getRepository() {
        return authorRepository;
    }

    @Override
    protected MovableService<Author> getMovableService() {
        return new AuthorService(authorRepository, getCache());
    }

    @Override
    protected String getCacheKey() {
        return "authors";
    }

    @Override
    protected Author getItem1() {
        return AuthorUtils.newAuthorDomain(1);
    }

    @Override
    protected Author getItem2() {
        return AuthorUtils.newAuthorDomain(2);
    }

    @Override
    protected Author getAddItem() {
        return AuthorUtils.newAuthorDomain(null);
    }

    @Override
    protected Author getCopyItem() {
        final Author author = AuthorUtils.newAuthorDomain(null);
        author.setPosition(0);

        return author;
    }

    @Override
    protected Class<Author> getItemClass() {
        return Author.class;
    }

    @Override
    protected void assertDataDeepEquals(final Author expected, final Author actual) {
        AuthorUtils.assertAuthorDeepEquals(expected, actual);
    }

}

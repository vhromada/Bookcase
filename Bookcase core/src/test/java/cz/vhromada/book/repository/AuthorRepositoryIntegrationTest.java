package cz.vhromada.book.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;

import cz.vhromada.book.CoreTestConfiguration;
import cz.vhromada.book.domain.Author;
import cz.vhromada.book.utils.AuthorUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * A class represents integration test for class {@link AuthorRepository}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreTestConfiguration.class)
@Transactional
@Rollback
class AuthorRepositoryIntegrationTest {

    /**
     * Instance of {@link EntityManager}
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private EntityManager entityManager;

    /**
     * Instance of {@link AuthorRepository}
     */
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Test method for get authors.
     */
    @Test
    void getAuthors() {
        final List<Author> authors = authorRepository.findAll();

        AuthorUtils.assertAuthorsDeepEquals(AuthorUtils.getAuthors(), authors);

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT);
    }

    /**
     * Test method for get author.
     */
    @Test
    void getAuthor() {
        for (int i = 1; i < AuthorUtils.AUTHORS_COUNT; i++) {
            final Author author = authorRepository.findById(i).orElse(null);

            AuthorUtils.assertAuthorDeepEquals(AuthorUtils.getAuthorDomain(i), author);
        }

        assertThat(authorRepository.findById(Integer.MAX_VALUE).isPresent()).isFalse();

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT);
    }

    /**
     * Test method for add author.
     */
    @Test
    void add() {
        final Author author = AuthorUtils.newAuthorDomain(null);
        author.setPosition(AuthorUtils.AUTHORS_COUNT);

        authorRepository.save(author);

        assertThat(author.getId()).isEqualTo(AuthorUtils.AUTHORS_COUNT + 1);

        final Author addedAuthor = AuthorUtils.getAuthor(entityManager, AuthorUtils.AUTHORS_COUNT + 1);
        final Author expectedAddAuthor = AuthorUtils.newAuthorDomain(null);
        expectedAddAuthor.setId(AuthorUtils.AUTHORS_COUNT + 1);
        expectedAddAuthor.setPosition(AuthorUtils.AUTHORS_COUNT);
        AuthorUtils.assertAuthorDeepEquals(expectedAddAuthor, addedAuthor);

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT + 1);
    }

    /**
     * Test method for update author.
     */
    @Test
    void update() {
        final Author author = AuthorUtils.updateAuthor(entityManager, 1);

        authorRepository.save(author);

        final Author updatedAuthor = AuthorUtils.getAuthor(entityManager, 1);
        final Author expectedUpdatedAuthor = AuthorUtils.getAuthorDomain(1);
        AuthorUtils.updateAuthor(expectedUpdatedAuthor);
        expectedUpdatedAuthor.setPosition(AuthorUtils.POSITION);
        AuthorUtils.assertAuthorDeepEquals(expectedUpdatedAuthor, updatedAuthor);

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT);
    }

    /**
     * Test method for remove author.
     */
    @Test
    void remove() {
        entityManager.createNativeQuery("DELETE FROM book_authors").executeUpdate();

        authorRepository.delete(AuthorUtils.getAuthor(entityManager, 1));

        assertThat(AuthorUtils.getAuthor(entityManager, 1)).isNull();

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT - 1);
    }

    /**
     * Test method for remove all authors.
     */
    @Test
    void removeAll() {
        entityManager.createNativeQuery("DELETE FROM book_authors").executeUpdate();

        authorRepository.deleteAll();

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(0);
    }

}

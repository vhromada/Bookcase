package cz.vhromada.book.facade.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import cz.vhromada.book.CoreTestConfiguration;
import cz.vhromada.book.entity.Author;
import cz.vhromada.book.facade.AuthorFacade;
import cz.vhromada.book.utils.AuthorUtils;
import cz.vhromada.book.utils.BookUtils;
import cz.vhromada.common.facade.MovableParentFacade;
import cz.vhromada.common.test.facade.MovableParentFacadeIntegrationTest;
import cz.vhromada.result.Event;
import cz.vhromada.result.Result;
import cz.vhromada.result.Severity;
import cz.vhromada.result.Status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * A class represents integration test for class {@link AuthorFacadeImpl}.
 *
 * @author Vladimir Hromada
 */
@ContextConfiguration(classes = CoreTestConfiguration.class)
class AuthorFacadeImplIntegrationTest extends MovableParentFacadeIntegrationTest<Author, cz.vhromada.book.domain.Author> {

    /**
     * Instance of {@link EntityManager}
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private EntityManager entityManager;

    /**
     * Instance of {@link PlatformTransactionManager}
     */
    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * Instance of {@link AuthorFacade}
     */
    @Autowired
    private AuthorFacade authorFacade;

    /**
     * Test method for {@link AuthorFacade#add(Author)} with author with null first name.
     */
    @Test
    void add_NullFirstName() {
        final Author author = newData(null);
        author.setFirstName(null);

        final Result<Void> result = authorFacade.add(author);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link AuthorFacade#add(Author)} with author with empty string as first name.
     */
    @Test
    void add_EmptyFirstName() {
        final Author author = newData(null);
        author.setFirstName("");

        final Result<Void> result = authorFacade.add(author);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link AuthorFacade#add(Author)} with author with empty string as middle name.
     */
    @Test
    void add_EmptyMiddleName() {
        final Author author = newData(null);
        author.setMiddleName("");

        final Result<Void> result = authorFacade.add(author);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link AuthorFacade#add(Author)} with author with null last name.
     */
    @Test
    void add_NullLastName() {
        final Author author = newData(null);
        author.setLastName(null);

        final Result<Void> result = authorFacade.add(author);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link AuthorFacade#add(Author)} with author with empty string as last name.
     */
    @Test
    void add_EmptyLastName() {
        final Author author = newData(null);
        author.setLastName("");

        final Result<Void> result = authorFacade.add(author);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link AuthorFacade#update(Author)} with author with null first name.
     */
    @Test
    void update_NullFirstName() {
        final Author author = newData(1);
        author.setFirstName(null);

        final Result<Void> result = authorFacade.update(author);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link AuthorFacade#update(Author)} with author with empty string as first name.
     */
    @Test
    void update_EmptyFirstName() {
        final Author author = newData(1);
        author.setFirstName("");

        final Result<Void> result = authorFacade.update(author);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link AuthorFacade#update(Author)} with author with empty string as middle name.
     */
    @Test
    void update_EmptyMiddleName() {
        final Author author = newData(1);
        author.setMiddleName("");

        final Result<Void> result = authorFacade.update(author);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link AuthorFacade#update(Author)} with author with null last name.
     */
    @Test
    void update_NullLastName() {
        final Author author = newData(1);
        author.setLastName(null);

        final Result<Void> result = authorFacade.update(author);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link AuthorFacade#update(Author)} with author with empty string as last name.
     */
    @Test
    void update_EmptyLastName() {
        final Author author = newData(1);
        author.setLastName("");

        final Result<Void> result = authorFacade.update(author);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    @Override
    protected MovableParentFacade<Author> getMovableParentFacade() {
        return authorFacade;
    }

    @Override
    protected Integer getDefaultDataCount() {
        return AuthorUtils.AUTHORS_COUNT;
    }

    @Override
    protected Integer getRepositoryDataCount() {
        return AuthorUtils.getAuthorsCount(entityManager);
    }

    @Override
    protected List<cz.vhromada.book.domain.Author> getDataList() {
        return AuthorUtils.getAuthors();
    }

    @Override
    protected cz.vhromada.book.domain.Author getDomainData(final Integer index) {
        return AuthorUtils.getAuthorDomain(index);
    }

    @Override
    protected Author newData(final Integer id) {
        return AuthorUtils.newAuthor(id);
    }

    @Override
    protected cz.vhromada.book.domain.Author newDomainData(final Integer id) {
        return AuthorUtils.newAuthorDomain(id);
    }

    @Override
    protected cz.vhromada.book.domain.Author getRepositoryData(final Integer id) {
        return AuthorUtils.getAuthor(entityManager, id);
    }

    @Override
    protected String getName() {
        return "Author";
    }

    @Override
    protected void clearReferencedData() {
        final TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        entityManager.createNativeQuery("DELETE FROM book_authors").executeUpdate();
        transactionManager.commit(transactionStatus);
    }

    @Override
    protected void assertDataListDeepEquals(final List<Author> expected, final List<cz.vhromada.book.domain.Author> actual) {
        AuthorUtils.assertAuthorListDeepEquals(expected, actual);
    }

    @Override
    protected void assertDataDeepEquals(final Author expected, final cz.vhromada.book.domain.Author actual) {
        AuthorUtils.assertAuthorDeepEquals(expected, actual);
    }

    @Override
    protected void assertDataDomainDeepEquals(final cz.vhromada.book.domain.Author expected, final cz.vhromada.book.domain.Author actual) {
        AuthorUtils.assertAuthorDeepEquals(expected, actual);
    }

    @Override
    protected void assertDefaultRepositoryData() {
        super.assertDefaultRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertNewRepositoryData() {
        super.assertNewRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertAddRepositoryData() {
        super.assertAddRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertUpdateRepositoryData() {
        super.assertUpdateRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertRemoveRepositoryData() {
        super.assertRemoveRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertDuplicateRepositoryData() {
        super.assertDuplicateRepositoryData();

        assertReferences();
    }

    /**
     * Asserts references.
     */
    private void assertReferences() {
        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT);
    }

}

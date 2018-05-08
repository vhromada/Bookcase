package cz.vhromada.book.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import cz.vhromada.book.entity.Author;

/**
 * A class represents utility class for authors.
 *
 * @author Vladimir Hromada
 */
public final class AuthorUtils {

    /**
     * Count of authors
     */
    public static final int AUTHORS_COUNT = 3;

    /**
     * ID
     */
    public static final int ID = 1;

    /**
     * Position
     */
    public static final int POSITION = 10;

    /**
     * Author name
     */
    private static final String AUTHOR = "Author ";

    /**
     * Creates a new instance of AuthorUtils.
     */
    private AuthorUtils() {
    }

    /**
     * Returns author.
     *
     * @param id ID
     * @return author
     */
    public static cz.vhromada.book.domain.Author newAuthorDomain(final Integer id) {
        final cz.vhromada.book.domain.Author author = new cz.vhromada.book.domain.Author();
        updateAuthor(author);
        if (id != null) {
            author.setId(id);
            author.setPosition(id - 1);
        }

        return author;
    }

    /**
     * Updates author fields.
     *
     * @param author author
     */
    @SuppressWarnings("Duplicates")
    public static void updateAuthor(final cz.vhromada.book.domain.Author author) {
        author.setFirstName("First name");
        author.setMiddleName("Middle name");
        author.setLastName("Last name");
    }

    /**
     * Returns author.
     *
     * @param id ID
     * @return author
     */
    public static Author newAuthor(final Integer id) {
        final Author author = new Author();
        updateAuthor(author);
        if (id != null) {
            author.setId(id);
            author.setPosition(id - 1);
        }

        return author;
    }

    /**
     * Updates author fields.
     *
     * @param author author
     */
    @SuppressWarnings("Duplicates")
    public static void updateAuthor(final Author author) {
        author.setFirstName("First name");
        author.setMiddleName("Middle name");
        author.setLastName("Last name");
    }

    /**
     * Returns authors.
     *
     * @return authors
     */
    public static List<cz.vhromada.book.domain.Author> getAuthors() {
        final List<cz.vhromada.book.domain.Author> authors = new ArrayList<>();
        for (int i = 0; i < AUTHORS_COUNT; i++) {
            authors.add(getAuthorDomain(i + 1));
        }

        return authors;
    }

    /**
     * Returns author for index.
     *
     * @param index index
     * @return author for index
     */
    public static cz.vhromada.book.domain.Author getAuthorDomain(final int index) {
        final cz.vhromada.book.domain.Author author = new cz.vhromada.book.domain.Author();
        author.setId(index);
        author.setFirstName(AUTHOR + index + " First Name");
        if (index == 2) {
            author.setMiddleName(AUTHOR + "2 Middle Name");
        }
        author.setLastName(AUTHOR + index + " Last Name");
        author.setPosition(index - 1);

        return author;
    }

    /**
     * Returns author for index.
     *
     * @param index index
     * @return author for index
     */
    public static Author getAuthor(final int index) {
        final Author author = new Author();
        author.setId(index);
        author.setFirstName(AUTHOR + index + " First Name");
        if (index == 2) {
            author.setMiddleName(AUTHOR + "2 Middle Name");
        }
        author.setLastName(AUTHOR + index + " Last Name");
        author.setPosition(index - 1);

        return author;
    }

    /**
     * Returns author.
     *
     * @param entityManager entity manager
     * @param id            author ID
     * @return author
     */
    public static cz.vhromada.book.domain.Author getAuthor(final EntityManager entityManager, final int id) {
        return entityManager.find(cz.vhromada.book.domain.Author.class, id);
    }

    /**
     * Returns author with updated fields.
     *
     * @param entityManager entity manager
     * @param id            author ID
     * @return author with updated fields
     */
    public static cz.vhromada.book.domain.Author updateAuthor(final EntityManager entityManager, final int id) {
        final cz.vhromada.book.domain.Author author = getAuthor(entityManager, id);
        updateAuthor(author);
        author.setPosition(POSITION);

        return author;
    }

    /**
     * Returns count of authors.
     *
     * @param entityManager entity manager
     * @return count of authors
     */
    public static int getAuthorsCount(final EntityManager entityManager) {
        return entityManager.createQuery("SELECT COUNT(a.id) FROM Author a", Long.class).getSingleResult().intValue();
    }

    /**
     * Asserts authors deep equals.
     *
     * @param expected expected authors
     * @param actual   actual authors
     */
    public static void assertAuthorsDeepEquals(final List<cz.vhromada.book.domain.Author> expected, final List<cz.vhromada.book.domain.Author> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(expected.size()).isEqualTo(actual.size());
        if (!expected.isEmpty()) {
            for (int i = 0; i < expected.size(); i++) {
                assertAuthorDeepEquals(expected.get(i), actual.get(i));
            }
        }
    }

    /**
     * Asserts author deep equals.
     *
     * @param expected expected author
     * @param actual   actual author
     */
    @SuppressWarnings("Duplicates")
    public static void assertAuthorDeepEquals(final cz.vhromada.book.domain.Author expected, final cz.vhromada.book.domain.Author actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(expected.getId()).isEqualTo(actual.getId());
            softly.assertThat(expected.getFirstName()).isEqualTo(actual.getFirstName());
            softly.assertThat(expected.getMiddleName()).isEqualTo(actual.getMiddleName());
            softly.assertThat(expected.getLastName()).isEqualTo(actual.getLastName());
            softly.assertThat(expected.getPosition()).isEqualTo(actual.getPosition());
        });
    }

    /**
     * Asserts authors deep equals.
     *
     * @param expected expected list of author
     * @param actual   actual authors
     */
    public static void assertAuthorListDeepEquals(final List<Author> expected, final List<cz.vhromada.book.domain.Author> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(expected.size()).isEqualTo(actual.size());
        if (!expected.isEmpty()) {
            for (int i = 0; i < expected.size(); i++) {
                assertAuthorDeepEquals(expected.get(i), actual.get(i));
            }
        }
    }

    /**
     * Asserts author deep equals.
     *
     * @param expected expected author
     * @param actual   actual author
     */
    @SuppressWarnings("Duplicates")
    public static void assertAuthorDeepEquals(final Author expected, final cz.vhromada.book.domain.Author actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(expected.getFirstName()).isEqualTo(actual.getFirstName());
            softly.assertThat(expected.getMiddleName()).isEqualTo(actual.getMiddleName());
            softly.assertThat(expected.getLastName()).isEqualTo(actual.getLastName());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
        });
    }

}

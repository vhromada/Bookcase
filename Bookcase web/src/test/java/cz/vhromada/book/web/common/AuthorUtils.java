package cz.vhromada.book.web.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.web.fo.AuthorFO;

/**
 * A class represents utility class for authors.
 *
 * @author Vladimir Hromada
 */
public final class AuthorUtils {

    /**
     * Creates a new instance of AuthorUtils.
     */
    private AuthorUtils() {
    }

    /**
     * Returns FO for author.
     *
     * @return FO for author
     */
    public static AuthorFO getAuthorFO() {
        final AuthorFO author = new AuthorFO();
        author.setId(1);
        author.setFirstName("First name");
        author.setMiddleName("Middle name");
        author.setLastName("Last name");
        author.setPosition(0);

        return author;
    }

    /**
     * Returns author.
     *
     * @return author
     */
    public static Author getAuthor() {
        final Author author = new Author();
        author.setId(1);
        author.setFirstName("First name");
        author.setMiddleName("Middle name");
        author.setLastName("Last name");
        author.setPosition(0);

        return author;
    }

    /**
     * Asserts author deep equals.
     *
     * @param expected expected FO for author
     * @param actual   actual author
     */
    @SuppressWarnings("Duplicates")
    public static void assertAuthorDeepEquals(final AuthorFO expected, final Author actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
            softly.assertThat(actual.getMiddleName()).isEqualTo(expected.getMiddleName());
            softly.assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
        });
    }

    /**
     * Asserts authors deep equals.
     *
     * @param expected expected list of authors
     * @param actual   actual list of author
     */
    public static void assertAuthorsDeepEquals(final List<Integer> expected, final List<Author> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(actual.size()).isEqualTo(expected.size());
        for (int i = 0; i < expected.size(); i++) {
            assertAuthorDeepEquals(expected.get(i), actual.get(i));
        }
    }

    /**
     * Asserts author deep equals.
     *
     * @param expected expected author
     * @param actual   actual author
     */
    @SuppressWarnings("Duplicates")
    public static void assertAuthorDeepEquals(final Integer expected, final Author actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected);
            softly.assertThat(actual.getFirstName()).isNull();
            softly.assertThat(actual.getMiddleName()).isNull();
            softly.assertThat(actual.getLastName()).isNull();
            softly.assertThat(actual.getPosition()).isNull();
        });
    }

    /**
     * Asserts authors deep equals.
     *
     * @param expected expected list of author
     * @param actual   actual list of authors
     */
    public static void assertAuthorListDeepEquals(final List<Author> expected, final List<Integer> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(actual.size()).isEqualTo(expected.size());
        for (int i = 0; i < expected.size(); i++) {
            assertAuthorDeepEquals(expected.get(i), actual.get(i));
        }
    }

    /**
     * Asserts author deep equals.
     *
     * @param expected expected author
     * @param actual   actual author
     */
    public static void assertAuthorDeepEquals(final Author expected, final Integer actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> softly.assertThat(actual).isEqualTo(expected.getId()));
    }

}

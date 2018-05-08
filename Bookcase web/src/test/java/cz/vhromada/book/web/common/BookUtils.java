package cz.vhromada.book.web.common;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cz.vhromada.book.entity.Book;
import cz.vhromada.book.web.fo.BookFO;
import cz.vhromada.common.Language;
import cz.vhromada.common.utils.CollectionUtils;

/**
 * A class represents utility class for books.
 *
 * @author Vladimir Hromada
 */
public final class BookUtils {

    /**
     * Creates a new instance of BookUtils.
     */
    private BookUtils() {
    }

    /**
     * Returns FO for book.
     *
     * @return FO for book
     */
    public static BookFO getBookFO() {
        final BookFO book = new BookFO();
        book.setId(1);
        book.setCzechName("Czech name");
        book.setOriginalName("Original name");
        book.setLanguages(CollectionUtils.newList(Language.CZ));
        book.setIsbn("ISBN");
        book.setIssueYear("2000");
        book.setDescription("Description");
        book.setElectronic(true);
        book.setPaper(false);
        book.setNote("Note");
        book.setPosition(0);
        book.setAuthors(CollectionUtils.newList(1));
        book.setCategories(CollectionUtils.newList(1));

        return book;
    }

    /**
     * Returns book.
     *
     * @return book
     */
    public static Book getBook() {
        final int issueYear = 2000;

        final Book book = new Book();
        book.setId(1);
        book.setCzechName("Czech name");
        book.setOriginalName("Original name");
        book.setLanguages(CollectionUtils.newList(Language.CZ));
        book.setIsbn("ISBN");
        book.setIssueYear(issueYear);
        book.setDescription("Description");
        book.setElectronic(true);
        book.setPaper(false);
        book.setNote("Note");
        book.setPosition(0);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.getAuthor()));
        book.setCategories(CollectionUtils.newList(CategoryUtils.getCategory()));

        return book;
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected FO for book
     * @param actual   actual book
     */
    @SuppressWarnings("Duplicates")
    public static void assertBookDeepEquals(final BookFO expected, final Book actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(actual.getCzechName()).isEqualTo(expected.getCzechName());
            softly.assertThat(actual.getOriginalName()).isEqualTo(expected.getOriginalName());
            softly.assertThat(actual.getLanguages())
                .hasSameSizeAs(expected.getLanguages())
                .hasSameElementsAs(expected.getLanguages());
            softly.assertThat(actual.getIsbn()).isEqualTo(expected.getIsbn());
            softly.assertThat(actual.getIssueYear()).isEqualTo(Integer.parseInt(expected.getIssueYear()));
            softly.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
            softly.assertThat(actual.getElectronic()).isEqualTo(expected.getElectronic());
            softly.assertThat(actual.getPaper()).isEqualTo(expected.getPaper());
            softly.assertThat(actual.getNote()).isEqualTo(expected.getNote());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
            AuthorUtils.assertAuthorsDeepEquals(expected.getAuthors(), actual.getAuthors());
            CategoryUtils.assertCategoriesDeepEquals(expected.getCategories(), actual.getCategories());
        });
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected book
     * @param actual   actual FO for book
     */
    @SuppressWarnings("Duplicates")
    public static void assertBookDeepEquals(final Book expected, final BookFO actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(actual.getCzechName()).isEqualTo(expected.getCzechName());
            softly.assertThat(actual.getOriginalName()).isEqualTo(expected.getOriginalName());
            softly.assertThat(actual.getLanguages())
                .hasSameSizeAs(expected.getLanguages())
                .hasSameElementsAs(expected.getLanguages());
            softly.assertThat(actual.getIsbn()).isEqualTo(expected.getIsbn());
            softly.assertThat(actual.getIssueYear()).isEqualTo(Integer.toString(expected.getIssueYear()));
            softly.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
            softly.assertThat(actual.getElectronic()).isEqualTo(expected.getElectronic());
            softly.assertThat(actual.getPaper()).isEqualTo(expected.getPaper());
            softly.assertThat(actual.getNote()).isEqualTo(expected.getNote());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
            AuthorUtils.assertAuthorListDeepEquals(expected.getAuthors(), actual.getAuthors());
            CategoryUtils.assertCategoryListDeepEquals(expected.getCategories(), actual.getCategories());
        });
    }

}

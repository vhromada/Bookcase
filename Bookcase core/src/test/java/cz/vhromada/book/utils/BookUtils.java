package cz.vhromada.book.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import cz.vhromada.book.domain.Author;
import cz.vhromada.book.domain.Category;
import cz.vhromada.book.entity.Book;
import cz.vhromada.common.Language;
import cz.vhromada.common.utils.CollectionUtils;

/**
 * A class represents utility class for books.
 *
 * @author Vladimir Hromada
 */
public final class BookUtils {

    /**
     * Count of books
     */
    public static final int BOOKS_COUNT = 3;

    /**
     * ID
     */
    public static final int ID = 1;

    /**
     * Position
     */
    public static final int POSITION = 10;

    /**
     * Book name
     */
    private static final String BOOK = "Book ";

    /**
     * Issue year
     */
    private static final int ISSUE_YEAR = 2000;

    /**
     * Creates a new instance of BookUtils.
     */
    private BookUtils() {
    }

    /**
     * Returns book.
     *
     * @param id ID
     * @return book
     */
    public static cz.vhromada.book.domain.Book newBookDomain(final Integer id) {
        final cz.vhromada.book.domain.Book book = new cz.vhromada.book.domain.Book();
        updateBook(book);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthorDomain(id)));
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategoryDomain(id)));
        if (id != null) {
            book.setId(id);
            book.setPosition(id - 1);
        }

        return book;
    }

    /**
     * Updates book fields.
     *
     * @param book book
     */
    @SuppressWarnings("Duplicates")
    public static void updateBook(final cz.vhromada.book.domain.Book book) {
        book.setCzechName("Czech name");
        book.setOriginalName("Original name");
        book.setLanguages(CollectionUtils.newList(Language.CZ));
        book.setIsbn("ISBN");
        book.setIssueYear(ISSUE_YEAR);
        book.setDescription("Description");
        book.setElectronic(true);
        book.setPaper(false);
        book.setNote("Note");
    }

    /**
     * Returns book.
     *
     * @param id ID
     * @return book
     */
    public static Book newBook(final Integer id) {
        final Book book = new Book();
        updateBook(book);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(id)));
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(id)));
        if (id != null) {
            book.setId(id);
            book.setPosition(id - 1);
        }

        return book;
    }

    /**
     * Updates book fields.
     *
     * @param book book
     */
    @SuppressWarnings("Duplicates")
    public static void updateBook(final Book book) {
        book.setCzechName("Czech name");
        book.setOriginalName("Original name");
        book.setLanguages(CollectionUtils.newList(Language.CZ));
        book.setIsbn("ISBN");
        book.setIssueYear(ISSUE_YEAR);
        book.setDescription("Description");
        book.setElectronic(true);
        book.setPaper(false);
        book.setNote("Note");
    }

    /**
     * Returns books.
     *
     * @return books
     */
    public static List<cz.vhromada.book.domain.Book> getBooks() {
        final List<cz.vhromada.book.domain.Book> books = new ArrayList<>();
        for (int i = 0; i < BOOKS_COUNT; i++) {
            books.add(getBook(i + 1));
        }

        return books;
    }

    /**
     * Returns book for index.
     *
     * @param index index
     * @return book for index
     */
    public static cz.vhromada.book.domain.Book getBook(final int index) {
        final List<Language> languages;
        final List<Author> authors = new ArrayList<>();
        final List<Category> categories = new ArrayList<>();
        authors.add(AuthorUtils.getAuthorDomain(index));
        categories.add(CategoryUtils.getCategoryDomain(index));
        switch (index) {
            case 1:
                languages = CollectionUtils.newList(Language.CZ, Language.EN);
                authors.add(AuthorUtils.getAuthorDomain(2));
                categories.add(CategoryUtils.getCategoryDomain(2));
                break;
            case 2:
                languages = CollectionUtils.newList(Language.CZ);
                break;
            default:
                languages = CollectionUtils.newList(Language.EN);
        }
        final cz.vhromada.book.domain.Book book = new cz.vhromada.book.domain.Book();
        book.setId(index);
        book.setCzechName(BOOK + index + " Czech Name");
        book.setOriginalName(BOOK + index + " Original Name");
        book.setLanguages(languages);
        book.setIsbn(index == 2 ? null : BOOK + index + " ISBN");
        book.setIssueYear(ISSUE_YEAR + index);
        book.setDescription(BOOK + index + " Description");
        book.setElectronic(index != 3);
        book.setPaper(index != 2);
        book.setNote(index == 2 ? null : BOOK + index + " Note");
        book.setPosition(index - 1);
        book.setAuthors(authors);
        book.setCategories(categories);

        return book;
    }

    /**
     * Returns book.
     *
     * @param entityManager entity manager
     * @param id            book ID
     * @return book
     */
    public static cz.vhromada.book.domain.Book getBook(final EntityManager entityManager, final int id) {
        return entityManager.find(cz.vhromada.book.domain.Book.class, id);
    }

    /**
     * Returns book with updated fields.
     *
     * @param entityManager entity manager
     * @param id            book ID
     * @return book with updated fields
     */
    public static cz.vhromada.book.domain.Book updateBook(final EntityManager entityManager, final int id) {
        final cz.vhromada.book.domain.Book book = getBook(entityManager, id);
        updateBook(book);
        book.setPosition(POSITION);

        return book;
    }

    /**
     * Returns count of books.
     *
     * @param entityManager entity manager
     * @return count of books
     */
    public static int getBooksCount(final EntityManager entityManager) {
        return entityManager.createQuery("SELECT COUNT(b.id) FROM Book b", Long.class).getSingleResult().intValue();
    }

    /**
     * Asserts books deep equals.
     *
     * @param expected expected books
     * @param actual   actual books
     */
    public static void assertBooksDeepEquals(final List<cz.vhromada.book.domain.Book> expected, final List<cz.vhromada.book.domain.Book> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(expected.size()).isEqualTo(actual.size());
        if (!expected.isEmpty()) {
            for (int i = 0; i < expected.size(); i++) {
                assertBookDeepEquals(expected.get(i), actual.get(i));
            }
        }
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected book
     * @param actual   actual book
     */
    @SuppressWarnings("Duplicates")
    public static void assertBookDeepEquals(final cz.vhromada.book.domain.Book expected, final cz.vhromada.book.domain.Book actual) {
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
            softly.assertThat(actual.getIssueYear()).isEqualTo(expected.getIssueYear());
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
     * Asserts books deep equals.
     *
     * @param expected expected list of book
     * @param actual   actual books
     */
    public static void assertBookListDeepEquals(final List<Book> expected, final List<cz.vhromada.book.domain.Book> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(expected.size()).isEqualTo(actual.size());
        if (!expected.isEmpty()) {
            for (int i = 0; i < expected.size(); i++) {
                assertBookDeepEquals(expected.get(i), actual.get(i));
            }
        }
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected book
     * @param actual   actual book
     */
    @SuppressWarnings("Duplicates")
    public static void assertBookDeepEquals(final Book expected, final cz.vhromada.book.domain.Book actual) {
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
            softly.assertThat(actual.getIssueYear()).isEqualTo(expected.getIssueYear());
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

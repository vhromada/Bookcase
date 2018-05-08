package cz.vhromada.book.facade.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import cz.vhromada.book.CoreTestConfiguration;
import cz.vhromada.book.entity.Author;
import cz.vhromada.book.entity.Book;
import cz.vhromada.book.entity.Category;
import cz.vhromada.book.facade.BookFacade;
import cz.vhromada.book.utils.AuthorUtils;
import cz.vhromada.book.utils.BookUtils;
import cz.vhromada.book.utils.CategoryUtils;
import cz.vhromada.common.Language;
import cz.vhromada.common.facade.MovableParentFacade;
import cz.vhromada.common.test.facade.MovableParentFacadeIntegrationTest;
import cz.vhromada.common.test.utils.TestConstants;
import cz.vhromada.common.utils.CollectionUtils;
import cz.vhromada.common.utils.Constants;
import cz.vhromada.result.Event;
import cz.vhromada.result.Result;
import cz.vhromada.result.Severity;
import cz.vhromada.result.Status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * A class represents integration test for class {@link BookFacadeImpl}.
 *
 * @author Vladimir Hromada
 */
@ContextConfiguration(classes = CoreTestConfiguration.class)
class BookFacadeImplIntegrationTest extends MovableParentFacadeIntegrationTest<Book, cz.vhromada.book.domain.Book> {

    /**
     * Event for invalid issue year
     */
    private static final Event INVALID_ISSUE_YEAR_EVENT = new Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NOT_VALID",
        "Issue year must be between " + Constants.MIN_YEAR + " and " + Constants.CURRENT_YEAR + '.');

    /**
     * Instance of {@link EntityManager}
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private EntityManager entityManager;

    /**
     * Instance of {@link BookFacade}
     */
    @Autowired
    private BookFacade bookFacade;

    /**
     * Test method for {@link BookFacade#add(Book)} with book with null czech name.
     */
    @Test
    void add_NullCzechName() {
        final Book book = newData(null);
        book.setCzechName(null);

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CZECH_NAME_NULL", "Czech name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with empty string as czech name.
     */
    @Test
    void add_EmptyCzechName() {
        final Book book = newData(null);
        book.setCzechName("");

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with null original name.
     */
    @Test
    void add_NullOriginalName() {
        final Book book = newData(null);
        book.setOriginalName(null);

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_NULL", "Original name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with empty string as original name.
     */
    @Test
    void add_EmptyOriginalName() {
        final Book book = newData(null);
        book.setOriginalName("");

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with null languages.
     */
    @Test
    void add_NullLanguages() {
        final Book book = newData(null);
        book.setLanguages(null);

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_LANGUAGES_NULL", "Languages mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with empty languages.
     */
    @Test
    void add_EmptyLanguages() {
        final Book book = newData(null);
        book.setLanguages(Collections.emptyList());

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_LANGUAGES_EMPTY", "Languages mustn't be empty.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with languages with null value.
     */
    @Test
    void add_BadLanguages() {
        final Book book = newData(null);
        book.setLanguages(CollectionUtils.newList(Language.CZ, null));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_LANGUAGES_CONTAIN_NULL", "Languages mustn't contain null value.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with empty string as ISBN.
     */
    @Test
    void add_EmptyIsbn() {
        final Book book = newData(null);
        book.setIsbn("");

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_ISBN_EMPTY", "ISBN mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with bad minimum issue year.
     */
    @Test
    void add_BadMinimumIssueYear() {
        final Book book = newData(null);
        book.setIssueYear(TestConstants.BAD_MIN_YEAR);

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(INVALID_ISSUE_YEAR_EVENT));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with bad maximum issue year.
     */
    @Test
    void add_BadMaximumIssueYear() {
        final Book book = newData(null);
        book.setIssueYear(TestConstants.BAD_MAX_YEAR);

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(INVALID_ISSUE_YEAR_EVENT));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with null description.
     */
    @Test
    void add_NullDescription() {
        final Book book = newData(null);
        book.setDescription(null);

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_DESCRIPTION_NULL", "Description mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with empty string as description.
     */
    @Test
    void add_EmptyDescription() {
        final Book book = newData(null);
        book.setDescription("");

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with empty string as note.
     */
    @Test
    void add_EmptyNote() {
        final Book book = newData(null);
        book.setNote("");

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_NOTE_EMPTY", "Note mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with null authors.
     */
    @Test
    void add_NullAuthors() {
        final Book book = newData(null);
        book.setAuthors(null);

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_AUTHORS_NULL", "Authors mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with authors with null value.
     */
    @Test
    void add_BadAuthors() {
        final Book book = newData(null);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), null));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_AUTHORS_CONTAIN_NULL", "Authors mustn't contain null value.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with authors with author with null ID.
     */
    @Test
    void add_NullAuthorId() {
        final Book book = newData(null);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), AuthorUtils.newAuthor(null)));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_ID_NULL", "ID mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with authors with author with null first name.
     */
    @Test
    void add_NullAuthorFirstName() {
        final Book book = newData(null);
        final Author badAuthor = AuthorUtils.newAuthor(1);
        badAuthor.setFirstName(null);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), badAuthor));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with authors with author with empty string as first name.
     */
    @Test
    void add_EmptyAuthorFirstName() {
        final Book book = newData(null);
        final Author badAuthor = AuthorUtils.newAuthor(1);
        badAuthor.setFirstName("");
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), badAuthor));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with authors with author with empty string as middle name.
     */
    @Test
    void add_EmptyAuthorMiddleName() {
        final Book book = newData(null);
        final Author badAuthor = AuthorUtils.newAuthor(1);
        badAuthor.setMiddleName("");
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), badAuthor));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with authors with author with null last name.
     */
    @Test
    void add_NullAuthorLastName() {
        final Book book = newData(null);
        final Author badAuthor = AuthorUtils.newAuthor(1);
        badAuthor.setLastName(null);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), badAuthor));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with authors with author with empty string as last name.
     */
    @Test
    void add_EmptyAuthorLastName() {
        final Book book = newData(null);
        final Author badAuthor = AuthorUtils.newAuthor(1);
        badAuthor.setLastName("");
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), badAuthor));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with show with authors with not existing author.
     */
    @Test
    void add_NotExistingAuthor() {
        final Book show = newData(null);
        show.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), AuthorUtils.newAuthor(Integer.MAX_VALUE)));

        final Result<Void> result = bookFacade.add(show);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_NOT_EXIST", "Author doesn't exist.")));
        });

        assertDefaultRepositoryData();
    }


    /**
     * Test method for {@link BookFacade#add(Book)} with book with null categories.
     */
    @Test
    void add_NullCategories() {
        final Book book = newData(null);
        book.setCategories(null);

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CATEGORIES_NULL", "Categories mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with categories with null value.
     */
    @Test
    void add_BadCategories() {
        final Book book = newData(null);
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(1), null));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CATEGORIES_CONTAIN_NULL", "Categories mustn't contain null value.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with categories with category with null ID.
     */
    @Test
    void add_NullCategoryId() {
        final Book book = newData(null);
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(1), CategoryUtils.newCategory(null)));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_ID_NULL", "ID mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with categories with category with null name.
     */
    @Test
    void add_NullCategoryName() {
        final Book book = newData(null);
        final Category badCategory = CategoryUtils.newCategory(1);
        badCategory.setName(null);
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(1), badCategory));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NAME_NULL", "Name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with book with categories with category with empty string as name.
     */
    @Test
    void add_EmptyCategoryName() {
        final Book book = newData(null);
        final Category badCategory = CategoryUtils.newCategory(1);
        badCategory.setName("");
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(1), badCategory));

        final Result<Void> result = bookFacade.add(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#add(Book)} with show with categories with not existing category.
     */
    @Test
    void add_NotExistingCategory() {
        final Book show = newData(null);
        show.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(1), CategoryUtils.newCategory(Integer.MAX_VALUE)));

        final Result<Void> result = bookFacade.add(show);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NOT_EXIST", "Category doesn't exist.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with null czech name.
     */
    @Test
    void update_NullCzechName() {
        final Book book = newData(1);
        book.setCzechName(null);

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CZECH_NAME_NULL", "Czech name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with empty string as czech name.
     */
    @Test
    void update_EmptyCzechName() {
        final Book book = newData(1);
        book.setCzechName("");

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with null original name.
     */
    @Test
    void update_NullOriginalName() {
        final Book book = newData(1);
        book.setOriginalName(null);

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_NULL", "Original name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with empty string as original name.
     */
    @Test
    void update_EmptyOriginalName() {
        final Book book = newData(1);
        book.setOriginalName("");

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with null languages.
     */
    @Test
    void update_NullLanguages() {
        final Book book = newData(1);
        book.setLanguages(null);

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_LANGUAGES_NULL", "Languages mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with empty languages.
     */
    @Test
    void update_EmptyLanguages() {
        final Book book = newData(1);
        book.setLanguages(Collections.emptyList());

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_LANGUAGES_EMPTY", "Languages mustn't be empty.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with languages with null value.
     */
    @Test
    void update_BadLanguages() {
        final Book book = newData(1);
        book.setLanguages(CollectionUtils.newList(Language.CZ, null));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_LANGUAGES_CONTAIN_NULL", "Languages mustn't contain null value.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with empty string as ISBN.
     */
    @Test
    void update_EmptyIsbn() {
        final Book book = newData(1);
        book.setIsbn("");

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_ISBN_EMPTY", "ISBN mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with bad minimum issue year.
     */
    @Test
    void update_BadMinimumIssueYear() {
        final Book book = newData(1);
        book.setIssueYear(TestConstants.BAD_MIN_YEAR);

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(INVALID_ISSUE_YEAR_EVENT));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with bad maximum issue year.
     */
    @Test
    void update_BadMaximumIssueYear() {
        final Book book = newData(1);
        book.setIssueYear(TestConstants.BAD_MAX_YEAR);

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(INVALID_ISSUE_YEAR_EVENT));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with null description.
     */
    @Test
    void update_NullDescription() {
        final Book book = newData(1);
        book.setDescription(null);

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_DESCRIPTION_NULL", "Description mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with empty string as description.
     */
    @Test
    void update_EmptyDescription() {
        final Book book = newData(1);
        book.setDescription("");

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with empty string as note.
     */
    @Test
    void update_EmptyNote() {
        final Book book = newData(1);
        book.setNote("");

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_NOTE_EMPTY", "Note mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with null authors.
     */
    @Test
    void update_NullAuthors() {
        final Book book = newData(1);
        book.setAuthors(null);

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_AUTHORS_NULL", "Authors mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with authors with null value.
     */
    @Test
    void update_BadAuthors() {
        final Book book = newData(1);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), null));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_AUTHORS_CONTAIN_NULL", "Authors mustn't contain null value.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with authors with author with null ID.
     */
    @Test
    void update_NullAuthorId() {
        final Book book = newData(1);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), AuthorUtils.newAuthor(null)));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_ID_NULL", "ID mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with authors with author with null first name.
     */
    @Test
    void update_NullAuthorFirstName() {
        final Book book = newData(1);
        final Author badAuthor = AuthorUtils.newAuthor(1);
        badAuthor.setFirstName(null);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), badAuthor));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with authors with author with empty string as first name.
     */
    @Test
    void update_EmptyAuthorFirstName() {
        final Book book = newData(1);
        final Author badAuthor = AuthorUtils.newAuthor(1);
        badAuthor.setFirstName("");
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), badAuthor));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with authors with author with empty string as middle name.
     */
    @Test
    void update_EmptyAuthorMiddleName() {
        final Book book = newData(1);
        final Author badAuthor = AuthorUtils.newAuthor(1);
        badAuthor.setMiddleName("");
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), badAuthor));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with authors with author with null last name.
     */
    @Test
    void update_NullAuthorLastName() {
        final Book book = newData(1);
        final Author badAuthor = AuthorUtils.newAuthor(1);
        badAuthor.setLastName(null);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), badAuthor));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with authors with author with empty string as last name.
     */
    @Test
    void update_EmptyAuthorLastName() {
        final Book book = newData(1);
        final Author badAuthor = AuthorUtils.newAuthor(1);
        badAuthor.setLastName("");
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), badAuthor));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with show with authors with not existing author.
     */
    @Test
    void update_NotExistingAuthor() {
        final Book show = newData(1);
        show.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), AuthorUtils.newAuthor(Integer.MAX_VALUE)));

        final Result<Void> result = bookFacade.update(show);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_NOT_EXIST", "Author doesn't exist.")));
        });

        assertDefaultRepositoryData();
    }


    /**
     * Test method for {@link BookFacade#update(Book)} with book with null categories.
     */
    @Test
    void update_NullCategories() {
        final Book book = newData(1);
        book.setCategories(null);

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CATEGORIES_NULL", "Categories mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with categories with null value.
     */
    @Test
    void update_BadCategories() {
        final Book book = newData(1);
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(1), null));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CATEGORIES_CONTAIN_NULL", "Categories mustn't contain null value.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with categories with category with null ID.
     */
    @Test
    void update_NullCategoryId() {
        final Book book = newData(1);
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(1), CategoryUtils.newCategory(null)));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_ID_NULL", "ID mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with categories with category with null name.
     */
    @Test
    void update_NullCategoryName() {
        final Book book = newData(1);
        final Category badCategory = CategoryUtils.newCategory(1);
        badCategory.setName(null);
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(1), badCategory));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NAME_NULL", "Name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with book with categories with category with empty string as name.
     */
    @Test
    void update_EmptyCategoryName() {
        final Book book = newData(1);
        final Category badCategory = CategoryUtils.newCategory(1);
        badCategory.setName("");
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(1), badCategory));

        final Result<Void> result = bookFacade.update(book);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link BookFacade#update(Book)} with show with categories with not existing category.
     */
    @Test
    void update_NotExistingCategory() {
        final Book show = newData(1);
        show.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(1), CategoryUtils.newCategory(Integer.MAX_VALUE)));

        final Result<Void> result = bookFacade.update(show);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NOT_EXIST", "Category doesn't exist.")));
        });

        assertDefaultRepositoryData();
    }

    @Override
    protected MovableParentFacade<Book> getMovableParentFacade() {
        return bookFacade;
    }

    @Override
    protected Integer getDefaultDataCount() {
        return BookUtils.BOOKS_COUNT;
    }

    @Override
    protected Integer getRepositoryDataCount() {
        return BookUtils.getBooksCount(entityManager);
    }

    @Override
    protected List<cz.vhromada.book.domain.Book> getDataList() {
        return BookUtils.getBooks();
    }

    @Override
    protected cz.vhromada.book.domain.Book getDomainData(final Integer index) {
        return BookUtils.getBook(index);
    }

    @Override
    protected Book newData(final Integer id) {
        final Book book = BookUtils.newBook(id);
        if (id == null || Integer.MAX_VALUE == id) {
            book.setAuthors(CollectionUtils.newList(AuthorUtils.getAuthor(1)));
            book.setCategories(CollectionUtils.newList(CategoryUtils.getCategory(1)));
        }

        return book;
    }

    @Override
    protected cz.vhromada.book.domain.Book newDomainData(final Integer id) {
        return BookUtils.newBookDomain(id);
    }

    @Override
    protected cz.vhromada.book.domain.Book getRepositoryData(final Integer id) {
        return BookUtils.getBook(entityManager, id);
    }

    @Override
    protected String getName() {
        return "Book";
    }

    @Override
    protected void clearReferencedData() {
    }

    @Override
    protected void assertDataListDeepEquals(final List<Book> expected, final List<cz.vhromada.book.domain.Book> actual) {
        BookUtils.assertBookListDeepEquals(expected, actual);
    }

    @Override
    protected void assertDataDeepEquals(final Book expected, final cz.vhromada.book.domain.Book actual) {
        BookUtils.assertBookDeepEquals(expected, actual);
    }

    @Override
    protected void assertDataDomainDeepEquals(final cz.vhromada.book.domain.Book expected, final cz.vhromada.book.domain.Book actual) {
        BookUtils.assertBookDeepEquals(expected, actual);
    }

    @Override
    protected void assertDefaultRepositoryData() {
        super.assertDefaultRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertNewRepositoryData() {
        super.assertNewRepositoryData();

        assertSoftly(softly -> {
            softly.assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT);
            softly.assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT);
        });
    }

    @Override
    protected void assertAddRepositoryData() {
        super.assertAddRepositoryData();

        assertSoftly(softly -> {
            softly.assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT);
            softly.assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT);
        });
    }

    @Override
    protected void assertUpdateRepositoryData() {
        super.assertUpdateRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertRemoveRepositoryData() {
        super.assertRemoveRepositoryData();

        assertSoftly(softly -> {
            softly.assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT);
            softly.assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT);
        });
    }

    @Override
    protected void assertDuplicateRepositoryData() {
        super.assertDuplicateRepositoryData();

        assertSoftly(softly -> {
            softly.assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT);
            softly.assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT);
        });
    }

    @Override
    protected Book getUpdateData(final Integer id) {
        final Book book = super.getUpdateData(id);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.getAuthor(1)));
        book.setCategories(CollectionUtils.newList(CategoryUtils.getCategory(1)));

        return book;
    }

    @Override
    protected cz.vhromada.book.domain.Book getExpectedAddData() {
        final cz.vhromada.book.domain.Book book = super.getExpectedAddData();
        book.setAuthors(CollectionUtils.newList(AuthorUtils.getAuthorDomain(1)));
        book.setCategories(CollectionUtils.newList(CategoryUtils.getCategoryDomain(1)));

        return book;
    }

    @Override
    protected cz.vhromada.book.domain.Book getExpectedDuplicatedData() {
        final cz.vhromada.book.domain.Book book = super.getExpectedDuplicatedData();
        book.setAuthors(CollectionUtils.newList(AuthorUtils.getAuthorDomain(3)));
        book.setCategories(CollectionUtils.newList(CategoryUtils.getCategoryDomain(3)));

        return book;
    }

    /**
     * Asserts references.
     */
    private void assertReferences() {
        assertSoftly(softly -> {
            softly.assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT);
            softly.assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT);
        });
    }

}

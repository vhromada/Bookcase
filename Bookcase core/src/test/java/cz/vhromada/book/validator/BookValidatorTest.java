package cz.vhromada.book.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.entity.Book;
import cz.vhromada.book.entity.Category;
import cz.vhromada.book.utils.AuthorUtils;
import cz.vhromada.book.utils.BookUtils;
import cz.vhromada.book.utils.CategoryUtils;
import cz.vhromada.common.Language;
import cz.vhromada.common.Movable;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.test.utils.TestConstants;
import cz.vhromada.common.test.validator.MovableValidatorTest;
import cz.vhromada.common.utils.CollectionUtils;
import cz.vhromada.common.utils.Constants;
import cz.vhromada.common.validator.MovableValidator;
import cz.vhromada.common.validator.ValidationType;
import cz.vhromada.result.Event;
import cz.vhromada.result.Result;
import cz.vhromada.result.Severity;
import cz.vhromada.result.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * A class represents test for class {@link BookValidator}.
 *
 * @author Vladimir Hromada
 */
class BookValidatorTest extends MovableValidatorTest<Book, cz.vhromada.book.domain.Book> {

    /**
     * Event for invalid issue year
     */
    private static final Event INVALID_ISSUE_YEAR_EVENT = new Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NOT_VALID",
        "Issue year must be between " + Constants.MIN_YEAR + " and " + Constants.CURRENT_YEAR + '.');

    /**
     * Validator for author
     */
    @Mock
    private MovableValidator<Author> authorValidator;

    /**
     * Validator for category
     */
    @Mock
    private MovableValidator<Category> categoryValidator;

    /**
     * Initializes data.
     */
    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        when(authorValidator.validate(any(Author.class), any())).thenReturn(new Result<>());
        when(categoryValidator.validate(any(Category.class), any())).thenReturn(new Result<>());
    }

    /**
     * Test method for {@link BookValidator#BookValidator(MovableService, MovableValidator, MovableValidator)} with null service for books.
     */
    @Test
    void constructor_NullBookService() {
        assertThatThrownBy(() -> new BookValidator(null, authorValidator, categoryValidator)).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link BookValidator#BookValidator(MovableService, MovableValidator, MovableValidator)} with null validator for author.
     */
    @Test
    void constructor_NullAuthorValidator() {
        assertThatThrownBy(() -> new BookValidator(getMovableService(), null, categoryValidator)).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link BookValidator#BookValidator(MovableService, MovableValidator, MovableValidator)} with null validator for category.
     */
    @Test
    void constructor_NullCategoryValidator() {
        assertThatThrownBy(() -> new BookValidator(getMovableService(), authorValidator, null)).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with null czech name.
     */
    @Test
    void validate_Deep_NullCzechName() {
        final Book book = getValidatingData(1);
        book.setCzechName(null);

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CZECH_NAME_NULL", "Czech name mustn't be null.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with empty string as
     * czech name.
     */
    @Test
    void validate_Deep_EmptyCzechName() {
        final Book book = getValidatingData(1);
        book.setCzechName("");

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with null original name.
     */
    @Test
    void validate_Deep_NullOriginalName() {
        final Book book = getValidatingData(1);
        book.setOriginalName(null);

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_NULL", "Original name mustn't be null.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with empty string as
     * original name.
     */
    @Test
    void validate_Deep_EmptyOriginalName() {
        final Book book = getValidatingData(1);
        book.setOriginalName("");

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with null languages.
     */
    @Test
    void validate_Deep_NullLanguages() {
        final Book book = getValidatingData(1);
        book.setLanguages(null);

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_LANGUAGES_NULL", "Languages mustn't be null.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with empty languages.
     */
    @Test
    void validate_Deep_EmptyLanguages() {
        final Book book = getValidatingData(1);
        book.setLanguages(Collections.emptyList());

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_LANGUAGES_EMPTY", "Languages mustn't be empty.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with languages with
     * null value.
     */
    @Test
    void validate_Deep_BadLanguages() {
        final Book book = getValidatingData(1);
        book.setLanguages(CollectionUtils.newList(Language.CZ, null));

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_LANGUAGES_CONTAIN_NULL", "Languages mustn't contain null value.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with empty string as ISBN.
     */
    @Test
    void validate_Deep_EmptyIsbn() {
        final Book book = getValidatingData(1);
        book.setIsbn("");

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_ISBN_EMPTY", "ISBN mustn't be empty string.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with bad minimum issue year.
     */
    @Test
    void validate_Deep_BadMinimumIssueYear() {
        final Book book = getValidatingData(1);
        book.setIssueYear(TestConstants.BAD_MIN_YEAR);

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(INVALID_ISSUE_YEAR_EVENT));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with bad maximum issue year.
     */
    @Test
    void validate_Deep_BadMaximumIssueYear() {
        final Book book = getValidatingData(1);
        book.setIssueYear(TestConstants.BAD_MAX_YEAR);

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(INVALID_ISSUE_YEAR_EVENT));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with nulldescription.
     */
    @Test
    void validate_Deep_NullDescription() {
        final Book book = getValidatingData(1);
        book.setDescription(null);

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_DESCRIPTION_NULL", "Description mustn't be null.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with empty string as description.
     */
    @Test
    void validate_Deep_EmptyDescription() {
        final Book book = getValidatingData(1);
        book.setDescription("");

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with empty string as note.
     */
    @Test
    void validate_Deep_EmptyNote() {
        final Book book = getValidatingData(1);
        book.setNote("");

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_NOTE_EMPTY", "Note mustn't be empty string.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with null authors.
     */
    @Test
    void validate_Deep_NullAuthors() {
        final Book book = getValidatingData(1);
        book.setAuthors(null);

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_AUTHORS_NULL", "Authors mustn't be null.")));
        });

        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(categoryValidator);
        verifyZeroInteractions(getMovableService(), authorValidator);
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with authors with null value.
     */
    @Test
    void validate_Deep_BadAuthors() {
        final Book book = getValidatingData(1);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(1), null));

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_AUTHORS_CONTAIN_NULL", "Authors mustn't contain null value.")));
        });

        for (final Author author : book.getAuthors()) {
            if (author != null) {
                verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
            }
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with authors with author with
     * invalid data.
     */
    @Test
    void validate_Deep_AuthorsWithAuthorWithInvalidData() {
        final Event event = new Event(Severity.ERROR, "CATEGORY_INVALID", "Invalid data");
        final Book book = getValidatingData(1);
        book.setAuthors(CollectionUtils.newList(AuthorUtils.newAuthor(null)));

        when(authorValidator.validate(any(Author.class), any())).thenReturn(Result.error(event.getKey(), event.getMessage()));

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(event));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with null categories.
     */
    @Test
    void validate_Deep_NullCategories() {
        final Book book = getValidatingData(1);
        book.setCategories(null);

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CATEGORIES_NULL", "Categories mustn't be null.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator);
        verifyZeroInteractions(getMovableService(), categoryValidator);
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with categories with null value.
     */
    @Test
    void validate_Deep_BadCategories() {
        final Book book = getValidatingData(1);
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(1), null));

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "BOOK_CATEGORIES_CONTAIN_NULL", "Categories mustn't contain null value.")));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            if (category != null) {
                verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
            }
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link BookValidator#validate(Movable, ValidationType...)}} with {@link ValidationType#DEEP} with data with categories with category with
     * invalid data.
     */
    @Test
    void validate_Deep_CategoriesWithCategoryWithInvalidData() {
        final Event event = new Event(Severity.ERROR, "CATEGORY_INVALID", "Invalid data");
        final Book book = getValidatingData(1);
        book.setCategories(CollectionUtils.newList(CategoryUtils.newCategory(null)));

        when(categoryValidator.validate(any(Category.class), any())).thenReturn(Result.error(event.getKey(), event.getMessage()));

        final Result<Void> result = getMovableValidator().validate(book, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents()).isEqualTo(Collections.singletonList(event));
        });

        for (final Author author : book.getAuthors()) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP);
        }
        for (final Category category : book.getCategories()) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP);
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator);
        verifyZeroInteractions(getMovableService());
    }

    @Override
    protected MovableValidator<Book> getMovableValidator() {
        return new BookValidator(getMovableService(), authorValidator, categoryValidator);
    }

    @Override
    protected Book getValidatingData(final Integer id) {
        return BookUtils.newBook(id);
    }

    @Override
    protected Book getValidatingData(final Integer id, final Integer position) {
        final Book book = BookUtils.newBook(id);
        book.setPosition(position);

        return book;
    }

    @Override
    protected cz.vhromada.book.domain.Book getRepositoryData(final Book validatingData) {
        return BookUtils.newBookDomain(validatingData.getId());
    }

    @Override
    protected cz.vhromada.book.domain.Book getItem1() {
        return BookUtils.newBookDomain(1);
    }

    @Override
    protected cz.vhromada.book.domain.Book getItem2() {
        return BookUtils.newBookDomain(2);
    }

    @Override
    protected String getName() {
        return "Book";
    }

}

package cz.vhromada.book.validator;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.entity.Book;
import cz.vhromada.book.entity.Category;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.utils.Constants;
import cz.vhromada.common.validator.AbstractMovableValidator;
import cz.vhromada.common.validator.MovableValidator;
import cz.vhromada.common.validator.ValidationType;
import cz.vhromada.result.Event;
import cz.vhromada.result.Result;
import cz.vhromada.result.Severity;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * A class represents implementation of validator for book.
 *
 * @author Vladimir Hromada
 */
@Component("bookValidator")
public class BookValidator extends AbstractMovableValidator<Book, cz.vhromada.book.domain.Book> {

    /**
     * Validator for author
     */
    private final MovableValidator<Author> authorValidator;

    /**
     * Validator for category
     */
    private final MovableValidator<Category> categoryValidator;

    /**
     * Creates a new instance of BookValidator.
     *
     * @param bookService       service for books
     * @param authorValidator   validator for author
     * @param categoryValidator validator for category
     * @throws IllegalArgumentException if service for books is null
     *                                  or validator for author is null
     *                                  or validator for category is null
     */
    public BookValidator(final MovableService<cz.vhromada.book.domain.Book> bookService, final MovableValidator<Author> authorValidator,
        final MovableValidator<Category> categoryValidator) {
        super("Book", bookService);

        Assert.notNull(authorValidator, "Validator for picture mustn't be null.");
        Assert.notNull(categoryValidator, "Validator for genre mustn't be null.");

        this.authorValidator = authorValidator;
        this.categoryValidator = categoryValidator;
    }

    /**
     * Validates book deeply.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Czech name is null</li>
     * <li>Czech name is empty string</li>
     * <li>Original name is null</li>
     * <li>Original name is empty string</li>
     * <li>Languages are null</li>
     * <li>Languages are empty</li>
     * <li>Languages contain null value</li>
     * <li>ISBN is empty string</li>
     * <li>Issue year isn't between 1940 and current year/</li>
     * <li>Description is null</li>
     * <li>Description is empty string</li>
     * <li>Note is empty string</li>
     * <li>Author ID is null</li>
     * <li>Author first name is empty string</li>
     * <li>Author middle name is empty string</li>
     * <li>Author last name is empty string</li>
     * <li>Author doesn't exist in data storage</li>
     * <li>Category ID is null</li>
     * <li>Category name is empty string</li>
     * <li>Category doesn't exist in data storage</li>
     * </ul>
     *
     * @param data   validating game
     * @param result result with validation errors
     */
    @Override
    protected void validateDataDeep(final Book data, final Result<Void> result) {
        validateNames(data, result);
        validateLanguages(data, result);
        if (data.getIsbn() != null && !StringUtils.hasText(data.getIsbn().trim())) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_ISBN_EMPTY", "ISBN mustn't be empty string."));
        }
        if (data.getIssueYear() < Constants.MIN_YEAR || data.getIssueYear() > Constants.CURRENT_YEAR) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NOT_VALID", "Issue year must be between " + Constants.MIN_YEAR + " and "
                + Constants.CURRENT_YEAR + '.'));
        }
        if (data.getDescription() == null) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_DESCRIPTION_NULL", "Description mustn't be null."));
        } else if (!StringUtils.hasText(data.getDescription())) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string."));
        }
        if (data.getNote() != null && !StringUtils.hasText(data.getNote().trim())) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_NOTE_EMPTY", "Note mustn't be empty string."));
        }
        validateAuthors(data, result);
        validateCategories(data, result);
    }

    /**
     * Validates names.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Czech name is null</li>
     * <li>Czech name is empty string</li>
     * <li>Original name is null</li>
     * <li>Original name is empty string</li>
     * </ul>
     *
     * @param data   validating movie
     * @param result result with validation errors
     */
    private static void validateNames(final Book data, final Result<Void> result) {
        if (data.getCzechName() == null) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_CZECH_NAME_NULL", "Czech name mustn't be null."));
        } else if (!StringUtils.hasText(data.getCzechName())) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string."));
        }
        if (data.getOriginalName() == null) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_NULL", "Original name mustn't be null."));
        } else if (!StringUtils.hasText(data.getOriginalName())) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string."));
        }
    }

    /**
     * Validates languages.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Languages are null</li>
     * <li>Languages are empty</li>
     * <li>Languages contain null value</li>
     * </ul>
     *
     * @param data   validating movie
     * @param result result with validation errors
     */
    private static void validateLanguages(final Book data, final Result<Void> result) {
        if (data.getLanguages() == null) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_LANGUAGES_NULL", "Languages mustn't be null."));
        } else if (data.getLanguages().isEmpty()) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_LANGUAGES_EMPTY", "Languages mustn't be empty."));
        } else if (data.getLanguages().contains(null)) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_LANGUAGES_CONTAIN_NULL", "Languages mustn't contain null value."));
        }
    }

    /**
     * Validates authors.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Authors are null</li>
     * <li>Authors contain null value</li>
     * <li>Author ID is null</li>
     * <li>Author first name is null</li>
     * <li>Author first name is empty string</li>
     * <li>Author middle name is empty string</li>
     * <li>Author middle name is null</li>
     * <li>Author last name is null</li>
     * <li>Author last name is empty string</li>
     * <li>Author doesn't exist</li>
     * </ul>
     *
     * @param data   validating movie
     * @param result result with validation errors
     */
    private void validateAuthors(final Book data, final Result<Void> result) {
        if (data.getAuthors() == null) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_AUTHORS_NULL", "Authors mustn't be null."));
        } else {
            if (data.getAuthors().contains(null)) {
                result.addEvent(new Event(Severity.ERROR, "BOOK_AUTHORS_CONTAIN_NULL", "Authors mustn't contain null value."));
            }
            for (final Author author : data.getAuthors()) {
                if (author != null) {
                    final Result<Void> validationResult = authorValidator.validate(author, ValidationType.EXISTS, ValidationType.DEEP);
                    result.addEvents(validationResult.getEvents());
                }
            }
        }
    }


    /**
     * Validates categories.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Categories are null</li>
     * <li>Categories contain null value</li>
     * <li>Category ID is null</li>
     * <li>Category name is null</li>
     * <li>Category name is empty string</li>
     * <li>Category doesn't exist</li>
     * </ul>
     *
     * @param data   validating movie
     * @param result result with validation errors
     */
    private void validateCategories(final Book data, final Result<Void> result) {
        if (data.getCategories() == null) {
            result.addEvent(new Event(Severity.ERROR, "BOOK_CATEGORIES_NULL", "Categories mustn't be null."));
        } else {
            if (data.getCategories().contains(null)) {
                result.addEvent(new Event(Severity.ERROR, "BOOK_CATEGORIES_CONTAIN_NULL", "Categories mustn't contain null value."));
            }
            for (final Category category : data.getCategories()) {
                if (category != null) {
                    final Result<Void> validationResult = categoryValidator.validate(category, ValidationType.EXISTS, ValidationType.DEEP);
                    result.addEvents(validationResult.getEvents());
                }
            }
        }
    }

}

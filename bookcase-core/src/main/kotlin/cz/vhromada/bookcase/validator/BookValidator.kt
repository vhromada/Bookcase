package cz.vhromada.bookcase.validator

import cz.vhromada.bookcase.entity.Author
import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.entity.Category
import cz.vhromada.common.result.Event
import cz.vhromada.common.result.Result
import cz.vhromada.common.result.Severity
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.utils.Constants
import cz.vhromada.common.validator.AbstractMovableValidator
import cz.vhromada.common.validator.MovableValidator
import cz.vhromada.common.validator.ValidationType
import org.springframework.stereotype.Component

/**
 * A class represents validator for book.
 *
 * @author Vladimir Hromada
 */
@Component("bookValidator")
class BookValidator(
        bookService: MovableService<cz.vhromada.bookcase.domain.Book>,
        private val authorValidator: MovableValidator<Author>,
        private val categoryValidator: MovableValidator<Category>) : AbstractMovableValidator<Book, cz.vhromada.bookcase.domain.Book>("Book", bookService) {

    /**
     * Validates book deeply.
     * <br></br>
     * Validation errors:
     *
     *  * Czech name is null
     *  * Czech name is empty string
     *  * Original name is null
     *  * Original name is empty string
     *  * Issue year is null
     *  * Issue year isn't between 1940 and current year
     *  * Description is null
     *  * Description is empty string
     *  * Note is null
     *  * Authors are null
     *  * Authors contain null value
     *  * Author ID is null
     *  * Author first name is null
     *  * Author first name is empty string
     *  * Author middle name is null
     *  * Author last name is null
     *  * Author last name is empty string
     *  * Author doesn't exist
     *  * Categories are null
     *  * Categories contain null value
     *  * Category ID is null
     *  * Category name is null
     *  * Category name is empty string
     *  * Category doesn't exist
     *
     * @param data   validating book
     * @param result result with validation errors
     */
    override fun validateDataDeep(data: Book, result: Result<Unit>) {
        validateNames(data, result)
        when {
            data.issueYear == null -> {
                result.addEvent(Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NULL", "Issue year mustn't be null."))
            }
            data.issueYear < Constants.MIN_YEAR || data.issueYear > Constants.CURRENT_YEAR -> {
                result.addEvent(Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NOT_VALID", "Issue year must be between ${Constants.MIN_YEAR} and ${Constants.CURRENT_YEAR}."))
            }
        }
        when {
            data.description == null -> {
                result.addEvent(Event(Severity.ERROR, "BOOK_DESCRIPTION_NULL", "Description mustn't be null."))
            }
            data.description.isBlank() -> {
                result.addEvent(Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string."))
            }
        }
        if (data.note == null) {
            result.addEvent(Event(Severity.ERROR, "BOOK_NOTE_NULL", "Note mustn't be null."))
        }
        validateAuthors(data, result)
        validateCategories(data, result)
    }

    /**
     * Validates names.
     * <br></br>
     * Validation errors:
     *
     *  * Czech name is null
     *  * Czech name is empty string
     *  * Original name is null
     *  * Original name is empty string
     *
     * @param data   validating movie
     * @param result result with validation errors
     */
    private fun validateNames(data: Book, result: Result<Unit>) {
        when {
            data.czechName == null -> {
                result.addEvent(Event(Severity.ERROR, "BOOK_CZECH_NAME_NULL", "Czech name mustn't be null."))
            }
            data.czechName.isBlank() -> {
                result.addEvent(Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string."))
            }
        }
        when {
            data.originalName == null -> {
                result.addEvent(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_NULL", "Original name mustn't be null."))
            }
            data.originalName.isBlank() -> {
                result.addEvent(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string."))
            }
        }
    }

    /**
     * Validates authors.
     * <br></br>
     * Validation errors:
     *
     *  * Authors are null
     *  * Authors contain null value
     *  * Author ID is null
     *  * Author first name is null
     *  * Author first name is empty string
     *  * Author middle name is null
     *  * Author last name is null
     *  * Author last name is empty string
     *  * Author doesn't exist
     *
     * @param data   validating movie
     * @param result result with validation errors
     */
    private fun validateAuthors(data: Book, result: Result<Unit>) {
        if (data.authors == null) {
            result.addEvent(Event(Severity.ERROR, "BOOK_AUTHORS_NULL", "Authors mustn't be null."))
        } else {
            if (data.authors.contains(null)) {
                result.addEvent(Event(Severity.ERROR, "BOOK_AUTHORS_CONTAIN_NULL", "Authors mustn't contain null value."))
            }
            for (author in data.authors) {
                if (author != null) {
                    val validationResult = authorValidator.validate(author, ValidationType.EXISTS, ValidationType.DEEP)
                    result.addEvents(validationResult.events())
                }
            }
        }
    }

    /**
     * Validates categories.
     * <br></br>
     * Validation errors:
     *
     *  * Categories are null
     *  * Categories contain null value
     *  * Category ID is null
     *  * Category name is null
     *  * Category name is empty string
     *  * Category doesn't exist
     *
     * @param data   validating movie
     * @param result result with validation errors
     */
    private fun validateCategories(data: Book, result: Result<Unit>) {
        if (data.categories == null) {
            result.addEvent(Event(Severity.ERROR, "BOOK_CATEGORIES_NULL", "Categories mustn't be null."))
        } else {
            if (data.categories.contains(null)) {
                result.addEvent(Event(Severity.ERROR, "BOOK_CATEGORIES_CONTAIN_NULL", "Categories mustn't contain null value."))
            }
            for (category in data.categories) {
                if (category != null) {
                    val validationResult = categoryValidator.validate(category, ValidationType.EXISTS, ValidationType.DEEP)
                    result.addEvents(validationResult.events())
                }
            }
        }
    }

}

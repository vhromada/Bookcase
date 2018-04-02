package cz.vhromada.book.validator.impl

import cz.vhromada.book.entity.Author
import cz.vhromada.book.entity.Book
import cz.vhromada.book.entity.Category
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.utils.Constants
import cz.vhromada.book.validator.BookcaseValidator
import cz.vhromada.book.validator.common.ValidationType
import cz.vhromada.result.Event
import cz.vhromada.result.Result
import cz.vhromada.result.Severity
import org.springframework.stereotype.Component

/**
 * A class represents implementation of validator for book.
 *
 * @author Vladimir Hromada
 */
@Component("bookValidator")
class BookValidatorImpl(

    /**
     * Service for books
     */
    bookService: BookcaseService<cz.vhromada.book.domain.Book>,

    /**
     * Validator for author
     */
    val authorValidator: BookcaseValidator<Author>,

    /**
     * Validator for category
     */
    val categoryValidator: BookcaseValidator<Category>) : AbstractBookcaseValidator<Book, cz.vhromada.book.domain.Book>("Book", bookService) {

    /**
     * Validates book deeply.
     *
     * Validation errors:
     *  * Czech name is empty string
     *  * Original name is empty string
     *  * ISBN is empty string
     *  * Issue year isn't between 1940 and current year
     *  * Description is empty string
     *  * Note is empty string
     *
     * @param data   validating book
     * @param result result with validation errors
     */
    override fun validateDataDeep(data: Book, result: Result<Void>) {
        if (data.czechName.isBlank()) {
            result.addEvent(Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string."))
        }
        if (data.originalName.isBlank()) {
            result.addEvent(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string."))
        }
        if (data.isbn != null && data.isbn.isBlank()) {
            result.addEvent(Event(Severity.ERROR, "BOOK_ISBN_EMPTY", "ISBN mustn't be empty string."))
        }
        if (data.issueYear < Constants.MIN_YEAR || data.issueYear > Constants.CURRENT_YEAR) {
            result.addEvent(Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NOT_VALID", "Issue year must be between " + Constants.MIN_YEAR + " and " + Constants.CURRENT_YEAR + '.'))
        }
        if (data.description.isBlank()) {
            result.addEvent(Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string."))
        }
        if (data.note != null && data.note.isBlank()) {
            result.addEvent(Event(Severity.ERROR, "BOOK_NOTE_EMPTY", "Note mustn't be empty string."))
        }
        validateAuthors(data, result)
        validateCategories(data, result)
    }

    /**
     * Validates authors.
     *
     * Validation errors:
     *  * Author ID is null
     *  * Author first name is empty string
     *  * Author middle name is empty string
     *  * Author last name is empty string
     *  * Author doesn't exist
     *
     * @param data   validating book
     * @param result result with validation errors
     */
    private fun validateAuthors(data: Book, result: Result<Void>) {
        for (author in data.authors) {
            val validationResult = authorValidator.validate(author, ValidationType.EXISTS, ValidationType.DEEP)
            result.addEvents(validationResult.events)
        }
    }

    /**
     * Validates categories.
     *
     * Validation errors:
     *  * Category ID is null
     *  * Category name is empty string
     *  * Category doesn't exist
     *
     * @param data   validating book
     * @param result result with validation errors
     */
    private fun validateCategories(data: Book, result: Result<Void>) {
        for (category in data.categories) {
            val validationResult = categoryValidator.validate(category, ValidationType.EXISTS, ValidationType.DEEP)
            result.addEvents(validationResult.events)
        }
    }

}

package cz.vhromada.book.validator.impl

import cz.vhromada.book.entity.Author
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.result.Event
import cz.vhromada.result.Result
import cz.vhromada.result.Severity
import org.springframework.stereotype.Component

/**
 * A class represents implementation of validator for author.
 *
 * @author Vladimir Hromada
 */
@Component("authorValidator")
class AuthorValidatorImpl(authorService: BookcaseService<cz.vhromada.book.domain.Author>) : AbstractBookcaseValidator<Author, cz.vhromada.book.domain.Author>("Author", authorService) {

    /**
     * Validates author deeply.
     *
     * Validation errors:
     * * First name is empty string
     * * Middle name is empty string
     * * Last name is empty string
     *
     * @param data   validating author
     * @param result result with validation errors
     */
    override fun validateDataDeep(data: Author, result: Result<Void>) {
        if (data.firstName.isBlank()) {
            result.addEvent(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string."))
        }
        if (data.middleName != null && data.middleName.isBlank()) {
            result.addEvent(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string."))
        }
        if (data.lastName.isBlank()) {
            result.addEvent(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string."))
        }
    }

}

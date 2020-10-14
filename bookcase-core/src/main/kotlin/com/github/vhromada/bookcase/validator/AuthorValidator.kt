package com.github.vhromada.bookcase.validator

import com.github.vhromada.bookcase.entity.Author
import com.github.vhromada.common.result.Event
import com.github.vhromada.common.result.Result
import com.github.vhromada.common.result.Severity
import com.github.vhromada.common.service.MovableService
import com.github.vhromada.common.validator.AbstractMovableValidator
import org.springframework.stereotype.Component

/**
 * A class represents validator for author.
 *
 * @author Vladimir Hromada
 */
@Component("authorValidator")
class AuthorValidator(authorService: MovableService<com.github.vhromada.bookcase.domain.Author>) : AbstractMovableValidator<Author, com.github.vhromada.bookcase.domain.Author>("Author", authorService) {

    /**
     * Validates author deeply.
     * <br></br>
     * Validation errors:
     *
     *  * First name is null
     *  * First name is empty string
     *  * Middle name is null
     *  * Last name is null
     *  * Last name is empty string
     *
     * @param data   validating author
     * @param result result with validation errors
     */
    override fun validateDataDeep(data: Author, result: Result<Unit>) {
        when {
            data.firstName == null -> {
                result.addEvent(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null."))
            }
            data.firstName.isBlank() -> {
                result.addEvent(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string."))
            }
        }
        if (data.middleName == null) {
            result.addEvent(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_NULL", "Middle name mustn't be null."))
        }
        when {
            data.lastName == null -> {
                result.addEvent(Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null."))
            }
            data.lastName.isBlank() -> {
                result.addEvent(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string."))
            }
        }
    }

}

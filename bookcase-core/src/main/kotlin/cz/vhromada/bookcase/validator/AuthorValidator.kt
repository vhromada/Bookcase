package cz.vhromada.bookcase.validator

import cz.vhromada.bookcase.entity.Author
import cz.vhromada.common.result.Event
import cz.vhromada.common.result.Result
import cz.vhromada.common.result.Severity
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.validator.AbstractMovableValidator
import org.springframework.stereotype.Component

/**
 * A class represents validator for author.
 *
 * @author Vladimir Hromada
 */
@Component("authorValidator")
class AuthorValidator(authorService: MovableService<cz.vhromada.bookcase.domain.Author>) : AbstractMovableValidator<Author, cz.vhromada.bookcase.domain.Author>("Author", authorService) {

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

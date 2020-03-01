package cz.vhromada.bookcase.validator

import cz.vhromada.bookcase.entity.Category
import cz.vhromada.common.result.Event
import cz.vhromada.common.result.Result
import cz.vhromada.common.result.Severity
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.validator.AbstractMovableValidator
import org.springframework.stereotype.Component

/**
 * A class represents validator for category.
 *
 * @author Vladimir Hromada
 */
@Component("categoryValidator")
class CategoryValidator(categoryService: MovableService<cz.vhromada.bookcase.domain.Category>) : AbstractMovableValidator<Category, cz.vhromada.bookcase.domain.Category>("Category", categoryService) {

    /**
     * Validates category deeply.
     * <br></br>
     * Validation errors:
     *
     *  * Name is null
     *  * Name is empty string
     *
     * @param data   validating category
     * @param result result with validation errors
     */
    override fun validateDataDeep(data: Category, result: Result<Unit>) {
        when {
            data.name == null -> {
                result.addEvent(Event(Severity.ERROR, "CATEGORY_NAME_NULL", "Name mustn't be null."))
            }
            data.name.isBlank() -> {
                result.addEvent(Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string."))
            }
        }
    }

}

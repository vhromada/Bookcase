package cz.vhromada.book.validator.impl

import cz.vhromada.book.entity.Category
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.result.Event
import cz.vhromada.result.Result
import cz.vhromada.result.Severity
import org.springframework.stereotype.Component

/**
 * A class represents implementation of validator for category.
 *
 * @author Vladimir Hromada
 */
@Component("categoryValidator")
class CategoryValidatorImpl(categoryService: BookcaseService<cz.vhromada.book.domain.Category>) : AbstractBookcaseValidator<Category, cz.vhromada.book.domain.Category>("Category", categoryService) {

    /**
     * Validates category deeply.
     *
     * Validation errors:
     *  * Name is empty string
     *
     * @param data   validating category
     * @param result result with validation errors
     */
    override fun validateDataDeep(data: Category, result: Result<Void>) {
        if (data.name.isBlank()) {
            result.addEvent(Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string."))
        }
    }

}

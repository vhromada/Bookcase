package cz.vhromada.book.validator

import cz.vhromada.book.common.Movable
import cz.vhromada.book.validator.common.ValidationType
import cz.vhromada.result.Result

/**
 * An interface represents validator for catalog.
 *
 * @author Vladimir Hromada
 */
interface BookcaseValidator<in T : Movable> {

    /**
     * Validates data.
     *
     * @param data            validating data
     * @param validationTypes types of validation
     * @return result with validation errors
     */
    fun validate(data: T, vararg validationTypes: ValidationType): Result<Void>

}

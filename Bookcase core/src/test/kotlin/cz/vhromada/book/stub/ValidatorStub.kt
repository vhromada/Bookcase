package cz.vhromada.book.stub

import cz.vhromada.book.common.Movable
import cz.vhromada.book.validator.BookcaseValidator

/**
 * An interface represents stub for validator for bookcase.
 *
 * @author Vladimir Hromada
 */
interface ValidatorStub<in T : Movable> : BookcaseStub, BookcaseValidator<T> {

    /**
     * Verifies calling validation.
     *
     * @param data data
     */
    fun verify(data: T)

}

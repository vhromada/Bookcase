package cz.vhromada.book.stub.impl.validator

import cz.vhromada.book.common.Movable
import cz.vhromada.result.Result

/**
 * A class represents stub for validator with error result.
 *
 * @author Vladimir Hromada
 */
class ErrorValidatorStub<in T : Movable>(

    /**
     * Key
     */
    private val key: String,

    /**
     * Error message
     */
    private val errorMessage: String) : AbstractValidatorStub<T>() {

    override fun getResult(): Result<Void> {
        return Result.error(key + "_INVALID", errorMessage)
    }

}

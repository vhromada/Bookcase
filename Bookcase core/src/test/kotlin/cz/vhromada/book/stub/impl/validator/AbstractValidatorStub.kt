package cz.vhromada.book.stub.impl.validator

import cz.vhromada.book.common.Movable
import cz.vhromada.book.stub.ValidatorStub
import cz.vhromada.book.validator.common.ValidationType
import cz.vhromada.result.Result

/**
 * An abstract class represents stub for validator.
 *
 * @author Vladimir Hromada
 */
abstract class AbstractValidatorStub<in T : Movable> : ValidatorStub<T> {

    /**
     * Data
     */
    private val data = ArrayList<T>()

    /**
     * Count of interactions
     */
    private var interactions = 0

    override fun validate(data: T, vararg validationTypes: ValidationType): Result<Void> {
        this.data.add(data)
        this.interactions++
        return getResult()
    }

    override fun verify(data: T) {
        if (!this.data.remove(data)) {
            throw AssertionError("Validation for $data was not called.")
        }
    }

    override fun verifyNoMoreInteractions() {
        if (interactions == 0) {
            throw AssertionError("Validation has never been called.")
        }
        if (data.isNotEmpty()) {
            throw AssertionError("Validation was called for $data.")
        }
    }

    override fun verifyZeroInteractions() {
        if (interactions > 0) {
            throw AssertionError("Validation has been called.")
        }
    }

    /**
     * Returns validation result.
     *
     * @return validation result
     */
    protected abstract fun getResult(): Result<Void>

}

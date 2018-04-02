package cz.vhromada.book.stub.impl.validator

import cz.vhromada.book.common.Movable
import cz.vhromada.result.Result

/**
 * A class represents stub for validator with OK result.
 *
 * @author Vladimir Hromada
 */
class OkValidatorStub<in T : Movable> : AbstractValidatorStub<T>() {

    override fun getResult(): Result<Void> {
        return Result()
    }

}

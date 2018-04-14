package cz.vhromada.book.web.controller

import cz.vhromada.result.Result
import cz.vhromada.result.Status

/**
 * An abstract class represents controller for processing result.
 *
 * @author Vladimir Hromada
 */
abstract class AbstractResultController {

    /**
     * Process results.
     *
     * @param results results
     * @throws IllegalArgumentException if results aren't OK
     */
    protected fun processResults(vararg results: Result<*>) {
        val result = Result<Void>()
        for (resultItem in results) {
            result.addEvents(resultItem.events)
        }

        if (Status.OK != result.status) {
            throw IllegalArgumentException("Operation result with errors. $result")
        }
    }

}

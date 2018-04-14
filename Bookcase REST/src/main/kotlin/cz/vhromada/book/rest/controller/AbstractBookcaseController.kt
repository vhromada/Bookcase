package cz.vhromada.book.rest.controller

import cz.vhromada.result.Result
import cz.vhromada.result.Status
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * An abstract class represents controller for bookcase.
 *
 * @author Vladimir Hromada
 */
abstract class AbstractBookcaseController {

    /**
     * Process result.
     *
     * @param result result
     * @return response with result
     */
    protected fun <T> processResult(result: Result<T>): ResponseEntity<Result<T>> {
        return processResult(result, HttpStatus.OK)
    }

    /**
     * Process result.
     *
     * @param result result
     * @param status HTTP status
     * @return response with result
     */
    protected fun <T> processResult(result: Result<T>, status: HttpStatus): ResponseEntity<Result<T>> {
        return if (Status.ERROR == result.status) {
            processErrorResult(result)
        } else {
            ResponseEntity(result, status)
        }
    }

    /**
     * Process error result.
     *
     * @param result result
     * @return response with result
     */
    protected fun <T> processErrorResult(result: Result<T>): ResponseEntity<Result<T>> {
        return if (result.events.stream().anyMatch { event -> event.key.contains("NOT_EXIST") }) {
            ResponseEntity(result, HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(result, HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }

}

package cz.vhromada.book.validator.impl

import cz.vhromada.book.common.Movable
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.validator.BookcaseValidator
import cz.vhromada.book.validator.common.ValidationType
import cz.vhromada.result.Event
import cz.vhromada.result.Result
import cz.vhromada.result.Severity

/**
 * An abstract class represents validator for data.
 *
 * @author Vladimir Hromada
 */
abstract class AbstractBookcaseValidator<in T : Movable, U : Movable>(

    /**
     * Name of entity
     */
    private val name: String,

    /**
     * Service for bookcase
     */
    private val bookcaseService: BookcaseService<U>) : BookcaseValidator<T> {

    /**
     * Prefix for validation keys
     */
    private val prefix: String = name.toUpperCase()

    override fun validate(data: T, vararg validationTypes: ValidationType): Result<Void> {
        val result = Result<Void>()
        if (validationTypes.contains(ValidationType.NEW)) {
            validateNewData(data, result)
        }
        if (validationTypes.contains(ValidationType.EXISTS)) {
            validateExistingData(data, result)
        }
        if (validationTypes.contains(ValidationType.DEEP)) {
            validateDataDeep(data, result)
        }
        if (validationTypes.contains(ValidationType.UP)) {
            validateMovingData(data, result, true)
        }
        if (validationTypes.contains(ValidationType.DOWN)) {
            validateMovingData(data, result, false)
        }

        return result
    }

    /**
     * Validates data deeply.
     *
     * @param data   validating data
     * @param result result with validation errors
     */
    protected abstract fun validateDataDeep(data: T, result: Result<Void>)

    /**
     * Validates new data.
     *
     * Validation errors:
     * * ID isn't null
     *
     * @param data   validating data
     * @param result result with validation errors
     */
    private fun validateNewData(data: T, result: Result<Void>) {
        if (data.id != null) {
            result.addEvent(Event(Severity.ERROR, prefix + "_ID_NOT_NULL", "ID must be null."))
        }
    }

    /**
     * Validates existing data.
     *
     * Validation errors:
     * * ID is null
     * * Data doesn't exist in data storage
     *
     * @param data   validating data
     * @param result result with validation errors
     */
    private fun validateExistingData(data: T, result: Result<Void>) {
        if (data.id == null) {
            result.addEvent(Event(Severity.ERROR, prefix + "_ID_NULL", "ID mustn't be null."))
        } else if (bookcaseService.get(data.id!!) == null) {
            result.addEvent(Event(Severity.ERROR, prefix + "_NOT_EXIST", "$name doesn't exist."))
        }
    }

    /**
     * Validates moving data.
     *
     * Validation errors:
     * * Not movable data
     *
     * @param data   validating data
     * @param result result with validation errors
     * @param up     true if data is moving up
     */
    private fun validateMovingData(data: T, result: Result<Void>, up: Boolean) {
        if (data.id != null) {
            val domainData = bookcaseService.get(data.id!!)
            if (domainData != null) {
                val list = bookcaseService.getAll()
                val index = list.indexOf(domainData)
                if (up && index <= 0) {
                    result.addEvent(Event(Severity.ERROR, prefix + "_NOT_MOVABLE", "$name can't be moved up."))
                } else if (!up && (index < 0 || index >= list.size - 1)) {
                    result.addEvent(Event(Severity.ERROR, prefix + "_NOT_MOVABLE", "$name can't be moved down."))
                }
            }
        }
    }

}

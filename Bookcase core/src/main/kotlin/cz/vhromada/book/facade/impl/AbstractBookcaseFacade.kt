package cz.vhromada.book.facade.impl

import cz.vhromada.book.common.Movable
import cz.vhromada.book.facade.BookcaseFacade
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.validator.BookcaseValidator
import cz.vhromada.book.validator.common.ValidationType
import cz.vhromada.converter.Converter
import cz.vhromada.result.Result
import cz.vhromada.result.Status

/**
 * An abstract class represents facade for bookcase.
 *
 * @author Vladimir Hromada
 */
abstract class AbstractBookcaseFacade<T : Movable, U : Movable>(

    /**
     * Service for bookcase
     */
    private val bookcaseService: BookcaseService<U>,

    /**
     * Validator for bookcase
     */
    private val bookcaseValidator: BookcaseValidator<T>,

    /**
     * Conversion service
     */
    private val converter: Converter) : BookcaseFacade<T> {

    /**
     * Entity class.
     */
    protected abstract val entityClass: Class<T>

    /**
     * Domain class.
     */
    protected abstract val domainClass: Class<U>

    override fun newData(): Result<Void> {
        bookcaseService.newData()

        return Result()
    }

    override fun getAll(): Result<List<T>> {
        return Result.of(converter.convertCollection(bookcaseService.getAll(), entityClass))
    }

    override fun get(id: Int): Result<T> {
        return Result.of(converter.convert(bookcaseService.get(id), entityClass))
    }

    override fun add(data: T): Result<Void> {
        val result = bookcaseValidator.validate(data, ValidationType.NEW, ValidationType.DEEP)

        if (Status.OK == result.status) {
            bookcaseService.add(converter.convert(data, domainClass)!!)
        }

        return result
    }

    override fun update(data: T): Result<Void> {
        val result = bookcaseValidator.validate(data, ValidationType.EXISTS, ValidationType.DEEP)

        if (Status.OK == result.status) {
            bookcaseService.update(converter.convert(data, domainClass)!!)
        }

        return result
    }

    override fun remove(data: T): Result<Void> {
        val result = bookcaseValidator.validate(data, ValidationType.EXISTS)

        if (Status.OK == result.status) {
            bookcaseService.remove(bookcaseService.get(data.id!!)!!)
        }

        return result
    }

    override fun duplicate(data: T): Result<Void> {
        val result = bookcaseValidator.validate(data, ValidationType.EXISTS)

        if (Status.OK == result.status) {
            bookcaseService.duplicate(bookcaseService.get(data.id!!)!!)
        }

        return result
    }

    override fun moveUp(data: T): Result<Void> {
        val result = bookcaseValidator.validate(data, ValidationType.EXISTS, ValidationType.UP)

        if (Status.OK == result.status) {
            bookcaseService.moveUp(bookcaseService.get(data.id!!)!!)
        }

        return result
    }

    override fun moveDown(data: T): Result<Void> {
        val result = bookcaseValidator.validate(data, ValidationType.EXISTS, ValidationType.DOWN)

        if (Status.OK == result.status) {
            bookcaseService.moveDown(bookcaseService.get(data.id!!)!!)
        }

        return result
    }

    override fun updatePositions(): Result<Void> {
        bookcaseService.updatePositions()

        return Result()
    }

}

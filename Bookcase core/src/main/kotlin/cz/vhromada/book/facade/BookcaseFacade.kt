package cz.vhromada.book.facade

import cz.vhromada.book.common.Movable
import cz.vhromada.result.Result

/**
 * An interface represents facade for bookcase.
 *
 * @author Vladimir Hromada
 */
interface BookcaseFacade<T : Movable> {

    /**
     * Creates new data.
     *
     * @return result
     */
    fun newData(): Result<Void>

    /**
     * Returns list of data.
     *
     * @return result with list of data
     */
    fun getAll(): Result<List<T>>

    /**
     * Returns data with ID or null if there isn't such data.
     *
     * @param id ID
     * @return result with data
     */
    fun get(id: Int): Result<T>

    /**
     * Adds data. Sets new ID and position.
     *
     * Validation errors:
     * * ID isn't null
     * * Deep data validation errors
     *
     * @param data data
     * @return result with validation errors
     */
    fun add(data: T): Result<Void>

    /**
     * Updates data.
     *
     * Validation errors:
     * * ID is null
     * * Deep data validation errors
     * * Data doesn't exist in data storage
     *
     * @param data new value of data
     * @return result with validation errors
     */
    fun update(data: T): Result<Void>

    /**
     * Removes data.
     *
     * Validation errors:
     * * ID is null
     * * Data doesn't exist in data storage
     *
     * @param data data
     * @return result with validation errors
     */
    fun remove(data: T): Result<Void>

    /**
     * Duplicates data.
     *
     * Validation errors:
     * * ID is null
     * * Data doesn't exist in data storage
     *
     * @param data data
     * @return result with validation errors
     */
    fun duplicate(data: T): Result<Void>

    /**
     * Moves data in list one position up.
     *
     * Validation errors:
     * * ID is null
     * * Data can't be moved up
     * * Data doesn't exist in data storage
     *
     * @param data data
     * @return result with validation errors
     */
    fun moveUp(data: T): Result<Void>

    /**
     * Moves data in list one position down.
     *
     * Validation errors:
     * * ID is null
     * * Data can't be moved down
     * * Data doesn't exist in data storage
     *
     * @param data data
     * @return result with validation errors
     */
    fun moveDown(data: T): Result<Void>

    /**
     * Updates positions.
     *
     * @return result
     */
    fun updatePositions(): Result<Void>

}

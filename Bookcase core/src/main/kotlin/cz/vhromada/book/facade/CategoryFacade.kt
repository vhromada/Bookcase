package cz.vhromada.book.facade

import cz.vhromada.book.entity.Category
import cz.vhromada.result.Result

/**
 * An interface represents facade for categories.
 *
 * @author Vladimir Hromada
 */
interface CategoryFacade : BookcaseFacade<Category> {

    /**
     * Adds data. Sets new ID and position.
     *
     * Validation errors:
     * * ID isn't null
     * * Name is empty string
     *
     * @param data data
     * @return result with validation errors
     */
    override fun add(data: Category): Result<Void>

    /**
     * Updates data.
     *
     * Validation errors:
     * * ID is null
     * * Name is empty string
     * * Category doesn't exist in data storage
     *
     * @param data new value of data
     * @return result with validation errors
     */
    override fun update(data: Category): Result<Void>

}

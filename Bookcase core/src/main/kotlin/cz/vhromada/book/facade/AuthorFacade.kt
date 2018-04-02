package cz.vhromada.book.facade

import cz.vhromada.book.entity.Author
import cz.vhromada.result.Result

/**
 * An interface represents facade for authors.
 *
 * @author Vladimir Hromada
 */
interface AuthorFacade : BookcaseFacade<Author> {

    /**
     * Adds data. Sets new ID and position.
     *
     * Validation errors:
     * * ID isn't null
     * * First name is empty string
     * * Middle name is empty string
     * * Last name is empty string
     *
     * @param data data
     * @return result with validation errors
     */
    override fun add(data: Author): Result<Void>

    /**
     * Updates data.
     *
     * Validation errors:
     * * ID is null
     * * First name is empty string
     * * Middle name is empty string
     * * Last name is empty string
     * * Author doesn't exist in data storage
     *
     * @param data new value of data
     * @return result with validation errors
     */
    override fun update(data: Author): Result<Void>

}

package cz.vhromada.book.facade

import cz.vhromada.book.entity.Book
import cz.vhromada.result.Result

/**
 * An interface represents facade for books.
 *
 * @author Vladimir Hromada
 */
interface BookFacade : BookcaseFacade<Book> {

    /**
     * Adds data. Sets new ID and position.
     *
     * Validation errors:
     * * ID isn't null
     * * Czech name is empty string
     * * Original name is empty string
     * * ISBN is empty string
     * * Issue year isn't between 1940 and current year
     * * Description is empty string
     * * Note is empty string
     * * Author ID is null
     * * Author first name is empty string
     * * Author middle name is empty string
     * * Author last name is empty string
     * * Author doesn't exist
     * * Category ID is null
     * * Category name is empty string
     * * Category doesn't exist
     *
     * @param data data
     * @return result with validation errors
     */
    override fun add(data: Book): Result<Void>

    /**
     * Updates data.
     *
     * Validation errors:
     * * ID is null
     * * Czech name is empty string
     * * Original name is empty string
     * * ISBN is empty string
     * * Issue year isn't between 1940 and current year
     * * Description is empty string
     * * Note is empty string
     * * Author ID is null
     * * Author first name is empty string
     * * Author middle name is empty string
     * * Author last name is empty string
     * * Author doesn't exist
     * * Category ID is null
     * * Category name is empty string
     * * Category doesn't exist
     * * Book doesn't exist in data storage
     *
     * @param data new value of data
     * @return result with validation errors
     */
    override fun update(data: Book): Result<Void>

}

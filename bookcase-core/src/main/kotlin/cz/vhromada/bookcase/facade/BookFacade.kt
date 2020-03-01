package cz.vhromada.bookcase.facade

import cz.vhromada.bookcase.entity.Book
import cz.vhromada.common.facade.MovableParentFacade
import cz.vhromada.common.result.Result

/**
 * An interface represents facade for books.
 *
 * @author Vladimir Hromada
 */
interface BookFacade : MovableParentFacade<Book> {

    /**
     * Adds book. Sets new ID and position.
     * <br></br>
     * Validation errors:
     *
     *  * ID isn't null
     *  * Position isn't null
     *  * Czech name is null
     *  * Czech name is empty string
     *  * Original name is null
     *  * Original name is empty string
     *  * Issue year is null
     *  * Issue year isn't between 1940 and current year
     *  * Description is null
     *  * Description is empty string
     *  * Note is null
     *  * Authors are null
     *  * Authors contain null value
     *  * Author ID is null
     *  * Author first name is null
     *  * Author first name is empty string
     *  * Author middle name is null
     *  * Author last name is null
     *  * Author last name is empty string
     *  * Author doesn't exist
     *  * Categories are null
     *  * Categories contain null value
     *  * Category ID is null
     *  * Category name is null
     *  * Category name is empty string
     *  * Category doesn't exist
     *
     * @param data book
     * @return result with validation errors
     */
    override fun add(data: Book): Result<Unit>

    /**
     * Updates book.
     * <br></br>
     * Validation errors:
     *
     *  * ID is null
     *  * Position is null
     *  * Czech name is null
     *  * Czech name is empty string
     *  * Original name is null
     *  * Original name is empty string
     *  * Issue year is null
     *  * Issue year isn't between 1940 and current year
     *  * Description is null
     *  * Description is empty string
     *  * Note is null
     *  * Authors are null
     *  * Authors contain null value
     *  * Author ID is null
     *  * Author first name is null
     *  * Author first name is empty string
     *  * Author middle name is null
     *  * Author last name is null
     *  * Author last name is empty string
     *  * Author doesn't exist
     *  * Categories are null
     *  * Categories contain null value
     *  * Category ID is null
     *  * Category name is null
     *  * Category name is empty string
     *  * Category doesn't exist
     *  * Book doesn't exist in data storage
     *
     * @param data new value of book
     * @return result with validation errors
     */
    override fun update(data: Book): Result<Unit>

}

package cz.vhromada.bookcase.facade

import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.entity.Item
import cz.vhromada.common.facade.MovableChildFacade
import cz.vhromada.common.result.Result

/**
 * An interface represents facade for items.
 *
 * @author Vladimir Hromada
 */
interface ItemFacade : MovableChildFacade<Item, Book> {

    /**
     * Adds item. Sets new ID and position.
     * <br></br>
     * Validation errors:
     *
     *  * Book ID is null
     *  * Book doesn't exist in data storage
     *  * Item ID isn't null
     *  * Item position isn't null
     *  * Languages are null
     *  * Languages are empty
     *  * Languages contain null value
     *  * Format is null
     *  * Note is null
     *
     * @param parent book
     * @param data   item
     * @return result with validation errors
     */
    override fun add(parent: Book, data: Item): Result<Unit>

    /**
     * Updates item.
     * <br></br>
     * Validation errors:
     *
     *  * ID isn't null
     *  * Position is null
     *  * Languages are null
     *  * Languages are empty
     *  * Languages contain null value
     *  * Format is null
     *  * Note is null
     *  * Item doesn't exist in data storage
     *
     * @param data new value of item
     * @return result with validation errors
     */
    override fun update(data: Item): Result<Unit>

}

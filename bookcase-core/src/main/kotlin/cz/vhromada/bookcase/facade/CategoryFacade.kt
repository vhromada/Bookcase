package cz.vhromada.bookcase.facade

import cz.vhromada.bookcase.entity.Category
import cz.vhromada.common.facade.MovableParentFacade
import cz.vhromada.common.result.Result

/**
 * An interface represents facade for categories.
 *
 * @author Vladimir Hromada
 */
interface CategoryFacade : MovableParentFacade<Category> {

    /**
     * Adds category. Sets new ID and position.
     * <br></br>
     * Validation errors:
     *
     *  * ID isn't null
     *  * Position isn't null
     *  * Name is null
     *  * Name is empty string
     *
     * @param data category
     * @return result with validation errors
     */
    override fun add(data: Category): Result<Unit>

    /**
     * Updates category.
     * <br></br>
     * Validation errors:
     *
     *  * ID is null
     *  * Position is null
     *  * Name is null
     *  * Name is empty string
     *  * Category doesn't exist in data storage
     *
     * @param data new value of category
     * @return result with validation errors
     */
    override fun update(data: Category): Result<Unit>

}

package cz.vhromada.bookcase.facade

import cz.vhromada.bookcase.entity.Author
import cz.vhromada.common.facade.MovableParentFacade
import cz.vhromada.common.result.Result

/**
 * An interface represents facade for authors.
 *
 * @author Vladimir Hromada
 */
interface AuthorFacade : MovableParentFacade<Author> {

    /**
     * Adds author. Sets new ID and position.
     * <br></br>
     * Validation errors:
     *
     *  * ID isn't null
     *  * Position isn't null
     *  * First name is null
     *  * First name is empty string
     *  * Middle name is null
     *  * Last name is null
     *  * Last name is empty string
     *
     * @param data author
     * @return result with validation errors
     */
    override fun add(data: Author): Result<Unit>

    /**
     * Updates author.
     * <br></br>
     * Validation errors:
     *
     *  * ID is null
     *  * Position is null
     *  * First name is null
     *  * First name is empty string
     *  * Middle name is null
     *  * Last name is null
     *  * Last name is empty string
     *  * Author doesn't exist in data storage
     *
     * @param data new value of author
     * @return result with validation errors
     */
    override fun update(data: Author): Result<Unit>

}

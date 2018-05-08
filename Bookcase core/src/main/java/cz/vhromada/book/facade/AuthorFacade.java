package cz.vhromada.book.facade;

import cz.vhromada.book.entity.Author;
import cz.vhromada.common.facade.MovableParentFacade;
import cz.vhromada.result.Result;

/**
 * An interface represents facade for authors.
 *
 * @author Vladimir Hromada
 */
public interface AuthorFacade extends MovableParentFacade<Author> {

    /**
     * Adds author. Sets new ID and position.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Author is null</li>
     * <li>ID isn't null</li>
     * <li>Position isn't null</li>
     * <li>First name is null</li>
     * <li>First name is empty string</li>
     * <li>Middle name is empty string</li>
     * <li>Last name is null</li>
     * <li>Last name is empty string</li>
     * </ul>
     *
     * @param data author
     * @return result with validation errors
     */
    @Override
    Result<Void> add(Author data);

    /**
     * Updates author.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Author is null</li>
     * <li>ID is null</li>
     * <li>Position is null</li>
     * <li>First name is null</li>
     * <li>First name is empty string</li>
     * <li>Middle name is empty string</li>
     * <li>Last name is null</li>
     * <li>Last name is empty string</li>
     * <li>Author doesn't exist in data storage</li>
     * </ul>
     *
     * @param data new value of author
     * @return result with validation errors
     */
    @Override
    Result<Void> update(Author data);

}

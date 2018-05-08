package cz.vhromada.book.facade;

import cz.vhromada.book.entity.Category;
import cz.vhromada.common.facade.MovableParentFacade;
import cz.vhromada.result.Result;

/**
 * An interface represents facade for categories.
 *
 * @author Vladimir Hromada
 */
public interface CategoryFacade extends MovableParentFacade<Category> {

    /**
     * Adds category. Sets new ID and position.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Category is null</li>
     * <li>ID isn't null</li>
     * <li>Position isn't null</li>
     * <li>Name is null</li>
     * <li>Name is empty string</li>
     * </ul>
     *
     * @param data category
     * @return result with validation errors
     */
    @Override
    Result<Void> add(Category data);

    /**
     * Updates category.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Category is null</li>
     * <li>ID is null</li>
     * <li>Position is null</li>
     * <li>Name is null</li>
     * <li>Name is empty string</li>
     * <li>Category doesn't exist in data storage</li>
     * </ul>
     *
     * @param data new value of category
     * @return result with validation errors
     */
    @Override
    Result<Void> update(Category data);

}

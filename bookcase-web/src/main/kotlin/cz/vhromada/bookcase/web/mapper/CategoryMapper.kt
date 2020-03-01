package cz.vhromada.bookcase.web.mapper

import cz.vhromada.bookcase.entity.Category
import cz.vhromada.bookcase.web.fo.CategoryFO

/**
 * An interface represents mapper for categories.
 *
 * @author Vladimir Hromada
 */
interface CategoryMapper {

    /**
     * Returns FO for category.
     *
     * @param source category
     * @return FO for category
     */
    fun map(source: Category): CategoryFO

    /**
     * Returns category.
     *
     * @param source FO for category
     * @return category
     */
    fun mapBack(source: CategoryFO): Category

}

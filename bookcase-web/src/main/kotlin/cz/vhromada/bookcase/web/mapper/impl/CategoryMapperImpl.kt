package cz.vhromada.bookcase.web.mapper.impl

import cz.vhromada.bookcase.entity.Category
import cz.vhromada.bookcase.web.fo.CategoryFO
import cz.vhromada.bookcase.web.mapper.CategoryMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for categories.
 *
 * @author Vladimir Hromada
 */
@Component("webCategoryMapper")
class CategoryMapperImpl : CategoryMapper {

    override fun map(source: Category): CategoryFO {
        return CategoryFO(
                id = source.id,
                name = source.name,
                position = source.position)
    }

    override fun mapBack(source: CategoryFO): Category {
        return Category(
                id = source.id,
                name = source.name,
                position = source.position)
    }

}

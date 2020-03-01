package cz.vhromada.bookcase.mapper

import cz.vhromada.bookcase.entity.Category
import cz.vhromada.common.mapper.Mapper
import org.springframework.stereotype.Component

/**
 * A class represents mapper for category.
 *
 * @author Vladimir Hromada
 */
@Component("categoryMapper")
class CategoryMapper : Mapper<Category, cz.vhromada.bookcase.domain.Category> {

    override fun map(source: Category): cz.vhromada.bookcase.domain.Category {
        return cz.vhromada.bookcase.domain.Category(
                id = source.id,
                name = source.name!!,
                position = source.position)
    }

    override fun mapBack(source: cz.vhromada.bookcase.domain.Category): Category {
        return Category(
                id = source.id,
                name = source.name,
                position = source.position)
    }

}

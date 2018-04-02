package cz.vhromada.book.converter

import cz.vhromada.book.entity.Category
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * A class represent converter for category.
 *
 * @author Vladimir Hromada
 */
@Component("categoryConverter")
class CategoryConverter : Converter<Category, cz.vhromada.book.domain.Category> {

    /**
     * Returns converted [cz.vhromada.book.domain.Category] from [Category].
     *
     * @param source source
     * @return converted category
     */
    override fun convert(source: Category): cz.vhromada.book.domain.Category {
        return cz.vhromada.book.domain.Category(source.id, source.name, source.position)
    }

}

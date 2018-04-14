package cz.vhromada.book.converter

import cz.vhromada.book.entity.Category
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * A class represent format for category.
 *
 * @author Vladimir Hromada
 */
@Component("domainCategoryConverter")
class DomainCategoryConverter : Converter<cz.vhromada.book.domain.Category, Category> {

    /**
     * Returns converted [Category] from [cz.vhromada.book.domain.Category].
     *
     * @param source source
     * @return converted category
     */
    override fun convert(source: cz.vhromada.book.domain.Category): Category {
        return Category(source.id, source.name, source.position)
    }

}

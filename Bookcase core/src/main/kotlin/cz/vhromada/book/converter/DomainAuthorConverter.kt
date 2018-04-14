package cz.vhromada.book.converter

import cz.vhromada.book.entity.Author
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * A class represent format for author.
 *
 * @author Vladimir Hromada
 */
@Component("domainAuthorConverter")
class DomainAuthorConverter : Converter<cz.vhromada.book.domain.Author, Author> {

    /**
     * Returns converted [Author] from [cz.vhromada.book.domain.Author].
     *
     * @param source source
     * @return converted author
     */
    override fun convert(source: cz.vhromada.book.domain.Author): Author {
        return Author(source.id, source.firstName, source.middleName, source.lastName, source.position)
    }

}

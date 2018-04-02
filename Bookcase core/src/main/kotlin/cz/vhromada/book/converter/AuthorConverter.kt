package cz.vhromada.book.converter

import cz.vhromada.book.entity.Author
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * A class represent converter for author.
 *
 * @author Vladimir Hromada
 */
@Component("authorConverter")
class AuthorConverter : Converter<Author, cz.vhromada.book.domain.Author> {

    /**
     * Returns converted [cz.vhromada.book.domain.Author] from [Author].
     *
     * @param source source
     * @return converted author
     */
    override fun convert(source: Author): cz.vhromada.book.domain.Author {
        return cz.vhromada.book.domain.Author(source.id, source.firstName, source.middleName, source.lastName, source.position)
    }

}

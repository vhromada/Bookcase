package cz.vhromada.bookcase.mapper

import cz.vhromada.bookcase.entity.Author
import cz.vhromada.common.mapper.Mapper
import org.springframework.stereotype.Component

/**
 * A class represents mapper for author.
 *
 * @author Vladimir Hromada
 */
@Component("authorMapper")
class AuthorMapper : Mapper<Author, cz.vhromada.bookcase.domain.Author> {

    override fun map(source: Author): cz.vhromada.bookcase.domain.Author {
        return cz.vhromada.bookcase.domain.Author(
                id = source.id,
                firstName = source.firstName!!,
                middleName = source.middleName,
                lastName = source.lastName!!,
                position = source.position,
                audit = null)
    }

    override fun mapBack(source: cz.vhromada.bookcase.domain.Author): Author {
        return Author(
                id = source.id,
                firstName = source.firstName,
                middleName = source.middleName,
                lastName = source.lastName,
                position = source.position)
    }

}

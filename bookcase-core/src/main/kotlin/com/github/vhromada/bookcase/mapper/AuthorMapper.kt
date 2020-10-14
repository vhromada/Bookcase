package com.github.vhromada.bookcase.mapper

import com.github.vhromada.bookcase.entity.Author
import com.github.vhromada.common.mapper.Mapper
import org.springframework.stereotype.Component

/**
 * A class represents mapper for author.
 *
 * @author Vladimir Hromada
 */
@Component("authorMapper")
class AuthorMapper : Mapper<Author, com.github.vhromada.bookcase.domain.Author> {

    override fun map(source: Author): com.github.vhromada.bookcase.domain.Author {
        return com.github.vhromada.bookcase.domain.Author(
                id = source.id,
                firstName = source.firstName!!,
                middleName = source.middleName,
                lastName = source.lastName!!,
                position = source.position,
                audit = null)
    }

    override fun mapBack(source: com.github.vhromada.bookcase.domain.Author): Author {
        return Author(
                id = source.id,
                firstName = source.firstName,
                middleName = source.middleName,
                lastName = source.lastName,
                position = source.position)
    }

}

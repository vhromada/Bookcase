package com.github.vhromada.bookcase.web.mapper.impl

import com.github.vhromada.bookcase.entity.Author
import com.github.vhromada.bookcase.web.fo.AuthorFO
import com.github.vhromada.bookcase.web.mapper.AuthorMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for authors.
 *
 * @author Vladimir Hromada
 */
@Component("webAuthorMapper")
class AuthorMapperImpl : AuthorMapper {

    override fun map(source: Author): AuthorFO {
        return AuthorFO(
                id = source.id,
                firstName = source.firstName,
                middleName = source.middleName,
                lastName = source.lastName,
                position = source.position)
    }

    override fun mapBack(source: AuthorFO): Author {
        return Author(
                id = source.id,
                firstName = source.firstName,
                middleName = source.middleName,
                lastName = source.lastName,
                position = source.position)
    }

}

package cz.vhromada.book.service.impl

import cz.vhromada.book.domain.Author
import cz.vhromada.book.repository.AuthorRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.stereotype.Component

/**
 * A class represents implementation of service for authors.
 *
 * @author Vladimir Hromada
 */
@Component("authorService")
class AuthorServiceImpl(authorRepository: AuthorRepository, @Value("#{cacheManager.getCache('bookcaseCache')}") cache: Cache) : AbstractBookcaseService<Author>(authorRepository, cache, "authors") {

    override fun getCopy(data: Author): Author {
        return Author(null, data.firstName, data.middleName, data.lastName, data.position)
    }

}

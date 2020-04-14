package cz.vhromada.bookcase.service

import cz.vhromada.bookcase.domain.Author
import cz.vhromada.bookcase.repository.AuthorRepository
import cz.vhromada.common.entity.Account
import cz.vhromada.common.provider.AccountProvider
import cz.vhromada.common.provider.TimeProvider
import cz.vhromada.common.service.AbstractMovableService
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.stereotype.Component

/**
 * A class represents of service for authors.
 *
 * @author Vladimir Hromada
 */
@Component("authorService")
class AuthorService(
        private val authorRepository: AuthorRepository,
        accountProvider: AccountProvider,
        timeProvider: TimeProvider,
        @Value("#{cacheManager.getCache('bookcaseCache')}") cache: Cache) : AbstractMovableService<Author>(authorRepository, accountProvider, timeProvider, cache, "authors") {

    override fun getAccountData(account: Account): List<Author> {
        return authorRepository.findByAuditCreatedUser(account.id)
    }

    override fun getCopy(data: Author): Author {
        return data.copy(id = null)
    }

}

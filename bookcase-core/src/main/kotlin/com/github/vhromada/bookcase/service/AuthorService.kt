package com.github.vhromada.bookcase.service

import com.github.vhromada.bookcase.domain.Author
import com.github.vhromada.bookcase.repository.AuthorRepository
import com.github.vhromada.common.entity.Account
import com.github.vhromada.common.provider.AccountProvider
import com.github.vhromada.common.provider.TimeProvider
import com.github.vhromada.common.service.AbstractMovableService
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
        return authorRepository.findByAuditCreatedUser(account.uuid!!)
    }

    override fun getCopy(data: Author): Author {
        return data.copy(id = null)
    }

}

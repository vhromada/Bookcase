package com.github.vhromada.bookcase.service

import com.github.vhromada.bookcase.domain.Book
import com.github.vhromada.bookcase.repository.BookRepository
import com.github.vhromada.common.entity.Account
import com.github.vhromada.common.provider.AccountProvider
import com.github.vhromada.common.provider.TimeProvider
import com.github.vhromada.common.service.AbstractMovableService
import com.github.vhromada.common.utils.sorted
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.stereotype.Component

/**
 * A class represents of service for books.
 *
 * @author Vladimir Hromada
 */
@Component("bookService")
class BookService(
        private val bookRepository: BookRepository,
        accountProvider: AccountProvider,
        timeProvider: TimeProvider,
        @Value("#{cacheManager.getCache('bookcaseCache')}") cache: Cache) : AbstractMovableService<Book>(bookRepository, accountProvider, timeProvider, cache, "books") {

    override fun getAccountData(account: Account): List<Book> {
        return bookRepository.findByAuditCreatedUser(account.uuid!!)
    }

    override fun getCopy(data: Book): Book {
        return data.copy(id = null, authors = data.authors.map { it }, items = data.items.map { it.copy(id = null) })
    }

    override fun updatePositions(data: List<Book>) {
        val audit = getAudit()
        for (i in data.indices) {
            val book = data[i]
            book.position = i
            book.modify(audit)
            val items = book.items.sorted()
            for (j in items.indices) {
                val item = items[j]
                item.position = j
                item.modify(audit)
            }
        }
    }

}

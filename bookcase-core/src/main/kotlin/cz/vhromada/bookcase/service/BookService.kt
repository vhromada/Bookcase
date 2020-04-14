package cz.vhromada.bookcase.service

import cz.vhromada.bookcase.domain.Book
import cz.vhromada.bookcase.repository.BookRepository
import cz.vhromada.common.entity.Account
import cz.vhromada.common.provider.AccountProvider
import cz.vhromada.common.provider.TimeProvider
import cz.vhromada.common.service.AbstractMovableService
import cz.vhromada.common.utils.sorted
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
        return bookRepository.findByAuditCreatedUser(account.id)
    }

    override fun getCopy(data: Book): Book {
        return data.copy(id = null, authors = data.authors.map { it }, categories = data.categories.map { it }, items = data.items.map { it.copy(id = null) })
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

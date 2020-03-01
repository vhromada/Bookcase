package cz.vhromada.bookcase.service

import cz.vhromada.bookcase.domain.Book
import cz.vhromada.bookcase.repository.BookRepository
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
        bookRepository: BookRepository,
        @Value("#{cacheManager.getCache('bookcaseCache')}") cache: Cache) : AbstractMovableService<Book>(bookRepository, cache, "books") {

    override fun getCopy(data: Book): Book {
        return data.copy(id = null, authors = data.authors.map { it }, categories = data.categories.map { it }, items = data.items.map { it.copy(id = null) })
    }

    override fun updatePositions(data: List<Book>) {
        for (i in data.indices) {
            val book = data[i]
            book.position = i
            val items = book.items.sorted()
            for (j in items.indices) {
                items[j].position = j
            }
        }
    }

}

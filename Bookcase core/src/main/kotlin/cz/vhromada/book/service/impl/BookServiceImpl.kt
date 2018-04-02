package cz.vhromada.book.service.impl

import cz.vhromada.book.domain.Book
import cz.vhromada.book.repository.BookRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.stereotype.Component

/**
 * A class represents implementation of service for books.
 *
 * @author Vladimir Hromada
 */
@Component("bookService")
class BookServiceImpl(bookRepository: BookRepository, @Value("#{cacheManager.getCache('bookcaseCache')}") cache: Cache) : AbstractBookcaseService<Book>(bookRepository, cache, "books") {

    override fun getCopy(data: Book): Book {
        return data.copy(id = null)
    }

}

package cz.vhromada.bookcase.service

import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import cz.vhromada.bookcase.domain.Book
import cz.vhromada.bookcase.repository.BookRepository
import cz.vhromada.bookcase.utils.BookUtils
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.test.service.MovableServiceTest
import org.mockito.Mock
import org.springframework.data.jpa.repository.JpaRepository

/**
 * A class represents test for class [BookService].
 *
 * @author Vladimir Hromada
 */
class BookServiceTest : MovableServiceTest<Book>() {

    /**
     * Instance of [BookRepository]
     */
    @Mock
    private lateinit var bookRepository: BookRepository

    override fun getRepository(): JpaRepository<Book, Int> {
        return bookRepository
    }

    override fun getService(): MovableService<Book> {
        return BookService(bookRepository, cache)
    }

    override fun getCacheKey(): String {
        return "books"
    }

    override fun getItem1(): Book {
        return BookUtils.newBookDomain(1)
    }

    override fun getItem2(): Book {
        return BookUtils.newBookDomain(2)
    }

    override fun getAddItem(): Book {
        return BookUtils.newBookDomain(null)
    }

    override fun getCopyItem(): Book {
        return BookUtils.newBookDomain(1)
                .copy(id = null)
    }

    override fun anyItem(): Book {
        return any()
    }

    override fun argumentCaptorItem(): KArgumentCaptor<Book> {
        return argumentCaptor()
    }

    override fun assertDataDeepEquals(expected: Book, actual: Book) {
        BookUtils.assertBookDeepEquals(expected, actual)
    }

}

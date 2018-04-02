package cz.vhromada.book.service.impl

import cz.vhromada.book.domain.Book
import cz.vhromada.book.repository.BookRepository
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.stub.RepositoryStub
import cz.vhromada.book.stub.impl.repository.BookRepositoryStub
import cz.vhromada.book.utils.BookUtils

/**
 * A class represents test for class [BookServiceImpl].
 *
 * @author Vladimir Hromada
 */
class BookServiceImplTest : AbstractServiceTest<Book>() {

    override val cacheKey = "books"

    override val itemClass = Book::class.java

    /**
     * Instance of [BookRepository]
     */
    private val bookRepository = BookRepositoryStub()

    override fun getRepository(): RepositoryStub<Book> {
        return bookRepository
    }

    override fun getBookcaseService(): BookcaseService<Book> {
        return BookServiceImpl(bookRepository, cache)
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
        val book = BookUtils.newBookDomain(null)
        book.position = 0

        return book
    }

    override fun assertDataDeepEquals(expected: Book, actual: Book) {
        BookUtils.assertBookDeepEquals(expected, actual)
    }

}

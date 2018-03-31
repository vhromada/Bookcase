package cz.vhromada.book.repository

import cz.vhromada.book.CoreTestConfiguration
import cz.vhromada.book.utils.BookUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

/**
 * A class represents integration test for class [BookRepository].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CoreTestConfiguration::class])
@Transactional
@Rollback
class BookRepositoryIntegrationTest {

    /**
     * Instance of [EntityManager]
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private val entityManager: EntityManager? = null

    /**
     * Instance of [BookRepository]
     */
    @Autowired
    private val bookRepository: BookRepository? = null

    /**
     * Checks autowired fields.
     */
    @BeforeEach
    fun setUp() {
        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(bookRepository).isNotNull
            softly.assertThat(entityManager).isNotNull
        }
    }

    /**
     * Test method for get book.
     */
    @Test
    fun getBooks() {
        val books = bookRepository!!.findAll()

        BookUtils.assertBooksDeepEquals(BookUtils.getBooks(), books)

        assertThat(BookUtils.getBooksCount(entityManager!!)).isEqualTo(BookUtils.BOOKS_COUNT)
    }

    /**
     * Test method for get book.
     */
    @Test
    fun getBook() {
        for (i in 1..BookUtils.BOOKS_COUNT) {
            val book = bookRepository!!.findById(i).orElse(null)

            BookUtils.assertBookDeepEquals(BookUtils.getBook(i), book)
        }

        assertThat(bookRepository!!.findById(Integer.MAX_VALUE).isPresent).isFalse()

        assertThat(BookUtils.getBooksCount(entityManager!!)).isEqualTo(BookUtils.BOOKS_COUNT)
    }

    /**
     * Test method for add book.
     */
    @Test
    fun add() {
        val book = BookUtils.newBookDomain(null)

        bookRepository!!.save(book)

        assertThat(book.id).isEqualTo(BookUtils.BOOKS_COUNT + 1)

        val addedBook = BookUtils.getBook(entityManager!!, BookUtils.BOOKS_COUNT + 1)
        val expectedAddBook = BookUtils.newBookDomain(BookUtils.BOOKS_COUNT + 1, 0)
        BookUtils.assertBookDeepEquals(expectedAddBook, addedBook)

        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT + 1)
    }

    /**
     * Test method for update book.
     */
    @Test
    fun update() {
        val book = BookUtils.newBookDomain(1)

        bookRepository!!.save(book)

        val updatedBook = BookUtils.getBook(entityManager!!, 1)
        val expectedUpdatedBook = BookUtils.newBookDomain(1)
        BookUtils.assertBookDeepEquals(expectedUpdatedBook, updatedBook)

        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT)
    }

    /**
     * Test method for remove book.
     */
    @Test
    fun remove() {
        bookRepository!!.delete(BookUtils.getBook(entityManager!!, 1)!!)

        assertThat(BookUtils.getBook(entityManager, 1)).isNull()

        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT - 1)
    }

    /**
     * Test method for remove all book.
     */
    @Test
    fun removeAll() {
        bookRepository!!.deleteAll()

        assertThat(BookUtils.getBooksCount(entityManager!!)).isEqualTo(0)
    }

}

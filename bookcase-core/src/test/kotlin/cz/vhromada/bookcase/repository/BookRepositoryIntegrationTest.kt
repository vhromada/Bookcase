package cz.vhromada.bookcase.repository

import cz.vhromada.bookcase.CoreTestConfiguration
import cz.vhromada.bookcase.utils.AuditUtils
import cz.vhromada.bookcase.utils.AuthorUtils
import cz.vhromada.bookcase.utils.BookUtils
import cz.vhromada.bookcase.utils.CategoryUtils
import cz.vhromada.bookcase.utils.ItemUtils
import cz.vhromada.bookcase.utils.updated
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
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
    private lateinit var entityManager: EntityManager

    /**
     * Instance of [BookRepository]
     */
    @Autowired
    private lateinit var bookRepository: BookRepository

    /**
     * Test method for get books.
     */
    @Test
    fun getBooks() {
        val books = bookRepository.findAll()

        BookUtils.assertBooksDeepEquals(BookUtils.getBooks(), books)

        assertSoftly {
            it.assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT)
            it.assertThat(ItemUtils.getItemsCount(entityManager)).isEqualTo(ItemUtils.ITEMS_COUNT)
        }
    }

    /**
     * Test method for get book.
     */
    @Test
    @Suppress("UsePropertyAccessSyntax")
    fun getBook() {
        for (i in 1 until BookUtils.BOOKS_COUNT) {
            val book = bookRepository.findById(i).orElse(null)

            BookUtils.assertBookDeepEquals(BookUtils.getBook(i), book)
        }

        assertThat(bookRepository.findById(Int.MAX_VALUE).isPresent).isFalse()

        assertSoftly {
            it.assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT)
            it.assertThat(ItemUtils.getItemsCount(entityManager)).isEqualTo(ItemUtils.ITEMS_COUNT)
        }
    }

    /**
     * Test method for add book.
     */
    @Test
    fun add() {
        val book = BookUtils.newBookDomain(null)
                .copy(position = BookUtils.BOOKS_COUNT,
                        authors = listOf(AuthorUtils.getAuthor(entityManager, 1)!!),
                        categories = listOf(CategoryUtils.getCategory(entityManager, 1)!!))

        bookRepository.save(book)

        assertThat(book.id).isEqualTo(BookUtils.BOOKS_COUNT + 1)

        val addedBook = BookUtils.getBook(entityManager, BookUtils.BOOKS_COUNT + 1)
        val expectedAddBook = BookUtils.newBookDomain(null)
                .copy(id = BookUtils.BOOKS_COUNT + 1,
                        position = BookUtils.BOOKS_COUNT,
                        authors = listOf(AuthorUtils.getAuthorDomain(1)),
                        categories = listOf(CategoryUtils.getCategoryDomain(1)))
        BookUtils.assertBookDeepEquals(expectedAddBook, addedBook)

        assertSoftly {
            it.assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT + 1)
            it.assertThat(ItemUtils.getItemsCount(entityManager)).isEqualTo(ItemUtils.ITEMS_COUNT)
        }
    }

    /**
     * Test method for update book.
     */
    @Test
    fun update() {
        val book = BookUtils.updateBook(entityManager, 1)

        bookRepository.save(book)

        val updatedBook = BookUtils.getBook(entityManager, 1)
        val expectedUpdatedBook = BookUtils.getBook(1)
                .updated()
                .copy(position = BookUtils.POSITION)
        BookUtils.assertBookDeepEquals(expectedUpdatedBook, updatedBook)

        assertSoftly {
            it.assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT)
            it.assertThat(ItemUtils.getItemsCount(entityManager)).isEqualTo(ItemUtils.ITEMS_COUNT)
        }
    }

    /**
     * Test method for update book with added item.
     */
    @Test
    fun updateAddedItem() {
        var item = ItemUtils.newItemDomain(null);
        item = item.copy(languages = item.languages.toMutableList(), position = ItemUtils.ITEMS_COUNT)
        entityManager.persist(item)

        var book = BookUtils.getBook(entityManager, 1)!!
        val items = book.items.toMutableList()
        items.add(item)
        book = book.copy(items = items)

        bookRepository.save(book)

        val updatedBook = BookUtils.getBook(entityManager, 1)
        val expectedItem = ItemUtils.newItemDomain(null)
                .copy(id = ItemUtils.ITEMS_COUNT + 1, position = ItemUtils.ITEMS_COUNT)
        var expectedUpdatedBook = BookUtils.getBook(1)
        val expectedItems = expectedUpdatedBook.items.toMutableList()
        expectedItems.add(expectedItem)
        expectedUpdatedBook = expectedUpdatedBook.copy(items = expectedItems)
        BookUtils.assertBookDeepEquals(expectedUpdatedBook, updatedBook)

        assertSoftly {
            it.assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT)
            it.assertThat(ItemUtils.getItemsCount(entityManager)).isEqualTo(ItemUtils.ITEMS_COUNT + 1)
        }
    }

    /**
     * Test method for remove book.
     */
    @Test
    fun remove() {
        val itemsCount = BookUtils.getBook(1).items.size

        bookRepository.delete(BookUtils.getBook(entityManager, 1)!!)

        assertThat(BookUtils.getBook(entityManager, 1)).isNull()

        assertSoftly {
            it.assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT - 1)
            it.assertThat(ItemUtils.getItemsCount(entityManager)).isEqualTo(ItemUtils.ITEMS_COUNT - itemsCount)
        }
    }

    /**
     * Test method for remove all books.
     */
    @Test
    fun removeAll() {
        bookRepository.deleteAll()

        assertSoftly {
            it.assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(0)
            it.assertThat(ItemUtils.getItemsCount(entityManager)).isEqualTo(0)
        }
    }

    /**
     * Test method for get books for user.
     */
    @Test
    fun findByAuditCreatedUser() {
        val books = bookRepository.findByAuditCreatedUser(AuditUtils.getAudit().createdUser)

        BookUtils.assertBooksDeepEquals(BookUtils.getBooks(), books)

        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT)
    }

}

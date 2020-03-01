package cz.vhromada.bookcase.utils

import cz.vhromada.bookcase.domain.Author
import cz.vhromada.bookcase.domain.Category
import cz.vhromada.bookcase.entity.Book
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import javax.persistence.EntityManager

/**
 * Updates book fields.
 *
 * @return updated book
 */
fun cz.vhromada.bookcase.domain.Book.updated(): cz.vhromada.bookcase.domain.Book {
    return copy(
            czechName = "Czech name",
            originalName = "Original name",
            isbn = "ISBN",
            issueYear = BookUtils.ISSUE_YEAR,
            description = "Description",
            note = "Note")
}

/**
 * Updates book fields.
 *
 * @return updated book
 */
fun Book.updated(): Book {
    return copy(
            czechName = "Czech name",
            originalName = "Original name",
            isbn = "ISBN",
            issueYear = BookUtils.ISSUE_YEAR,
            description = "Description",
            note = "Note")
}

/**
 * A class represents utility class for books.
 *
 * @author Vladimir Hromada
 */
object BookUtils {

    /**
     * Count of books
     */
    const val BOOKS_COUNT = 3

    /**
     * Position
     */
    const val POSITION = 10

    /**
     * Issue year
     */
    const val ISSUE_YEAR = 2000

    /**
     * Book name
     */
    private const val BOOK = "Book "

    /**
     * Returns books.
     *
     * @return books
     */
    fun getBooks(): List<cz.vhromada.bookcase.domain.Book> {
        val books = mutableListOf<cz.vhromada.bookcase.domain.Book>()
        for (i in 0 until BOOKS_COUNT) {
            books.add(getBook(i + 1))
        }

        return books
    }

    /**
     * Returns book.
     *
     * @param id ID
     * @return book
     */
    fun newBookDomain(id: Int?): cz.vhromada.bookcase.domain.Book {
        return cz.vhromada.bookcase.domain.Book(
                id = id,
                czechName = "",
                originalName = "",
                isbn = "",
                issueYear = 0,
                description = "",
                note = "",
                position = if (id == null) null else id - 1,
                authors = listOf(AuthorUtils.newAuthorDomain(id)),
                categories = listOf(CategoryUtils.newCategoryDomain(id)),
                items = emptyList())
                .updated()
    }

    /**
     * Returns book with items.
     *
     * @param id ID
     * @return book with items
     */
    fun newBookWithItems(id: Int?): cz.vhromada.bookcase.domain.Book {
        return newBookDomain(id)
                .copy(items = listOf(ItemUtils.newItemDomain(id)))
    }

    /**
     * Returns book.
     *
     * @param id ID
     * @return book
     */
    fun newBook(id: Int?): Book {
        return Book(
                id = id,
                czechName = "",
                originalName = "",
                isbn = "",
                issueYear = 0,
                description = "",
                note = "",
                position = if (id == null) null else id - 1,
                authors = listOf(AuthorUtils.newAuthor(id)),
                categories = listOf(CategoryUtils.newCategory(id)))
                .updated()
    }

    /**
     * Returns book for index.
     *
     * @param index index
     * @return book for index
     */
    fun getBook(index: Int): cz.vhromada.bookcase.domain.Book {
        val authors = mutableListOf<Author>()
        val categories = mutableListOf<Category>()
        authors.add(AuthorUtils.getAuthorDomain(index))
        categories.add(CategoryUtils.getCategoryDomain(index))
        if (index == 1) {
            authors.add(AuthorUtils.getAuthorDomain(2))
            categories.add(CategoryUtils.getCategoryDomain(2))
        }

        return cz.vhromada.bookcase.domain.Book(
                id = index,
                czechName = "$BOOK$index Czech Name",
                originalName = "$BOOK$index Original Name",
                isbn = if (index == 2) "" else "$BOOK$index ISBN",
                issueYear = ISSUE_YEAR + index,
                description = "$BOOK$index Description",
                note = if (index == 2) "" else "$BOOK$index Note",
                position = index - 1,
                authors = authors,
                categories = categories,
                items = ItemUtils.getItems(index))
    }

    /**
     * Returns book.
     *
     * @param entityManager entity manager
     * @param id            book ID
     * @return book
     */
    fun getBook(entityManager: EntityManager, id: Int): cz.vhromada.bookcase.domain.Book? {
        return entityManager.find(cz.vhromada.bookcase.domain.Book::class.java, id)
    }

    /**
     * Returns book with updated fields.
     *
     * @param entityManager entity manager
     * @param id            book ID
     * @return book with updated fields
     */
    fun updateBook(entityManager: EntityManager, id: Int): cz.vhromada.bookcase.domain.Book {
        return getBook(entityManager, id)!!
                .updated()
                .copy(position = POSITION)
    }

    /**
     * Returns count of books.
     *
     * @param entityManager entity manager
     * @return count of books
     */
    @Suppress("CheckStyle")
    fun getBooksCount(entityManager: EntityManager): Int {
        return entityManager.createQuery("SELECT COUNT(b.id) FROM Book b", java.lang.Long::class.java).singleResult.toInt()
    }

    /**
     * Asserts books deep equals.
     *
     * @param expected expected books
     * @param actual   actual books
     */
    fun assertBooksDeepEquals(expected: List<cz.vhromada.bookcase.domain.Book?>?, actual: List<cz.vhromada.bookcase.domain.Book?>?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertThat(expected!!.size).isEqualTo(actual!!.size)
        if (expected.isNotEmpty()) {
            for (i in expected.indices) {
                assertBookDeepEquals(expected[i], actual[i])
            }
        }
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected book
     * @param actual   actual book
     */
    fun assertBookDeepEquals(expected: cz.vhromada.bookcase.domain.Book?, actual: cz.vhromada.bookcase.domain.Book?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(actual!!.id).isEqualTo(expected!!.id)
            it.assertThat(actual.czechName).isEqualTo(expected.czechName)
            it.assertThat(actual.originalName).isEqualTo(expected.originalName)
            it.assertThat(actual.isbn).isEqualTo(expected.isbn)
            it.assertThat(actual.issueYear).isEqualTo(expected.issueYear)
            it.assertThat(actual.description).isEqualTo(expected.description)
            it.assertThat(actual.note).isEqualTo(expected.note)
            it.assertThat(actual.position).isEqualTo(expected.position)
            AuthorUtils.assertAuthorsDeepEquals(expected.authors, actual.authors)
            CategoryUtils.assertCategoriesDeepEquals(expected.categories, actual.categories)
        }
    }

    /**
     * Asserts books deep equals.
     *
     * @param expected expected list of book
     * @param actual   actual books
     */
    fun assertBookListDeepEquals(expected: List<Book?>?, actual: List<cz.vhromada.bookcase.domain.Book?>?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertThat(expected!!.size).isEqualTo(actual!!.size)
        if (expected.isNotEmpty()) {
            for (i in expected.indices) {
                assertBookDeepEquals(expected[i], actual[i])
            }
        }
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected book
     * @param actual   actual book
     */
    fun assertBookDeepEquals(expected: Book?, actual: cz.vhromada.bookcase.domain.Book?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(actual!!.id).isEqualTo(expected!!.id)
            it.assertThat(actual.czechName).isEqualTo(expected.czechName)
            it.assertThat(actual.originalName).isEqualTo(expected.originalName)
            it.assertThat(actual.isbn).isEqualTo(expected.isbn)
            it.assertThat(actual.issueYear).isEqualTo(expected.issueYear)
            it.assertThat(actual.description).isEqualTo(expected.description)
            it.assertThat(actual.note).isEqualTo(expected.note)
            it.assertThat(actual.position).isEqualTo(expected.position)
            AuthorUtils.assertAuthorListDeepEquals(expected.authors, actual.authors)
            CategoryUtils.assertCategoryListDeepEquals(expected.categories, actual.categories)
        }
    }

}

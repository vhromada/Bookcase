package cz.vhromada.book.utils

import cz.vhromada.book.common.Language
import cz.vhromada.book.domain.Book
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import javax.persistence.EntityManager

/**
 * An object represents utility class for book.
 *
 * @author Vladimir Hromada
 */
object BookUtils {

    /**
     * Count of book
     */
    const val BOOKS_COUNT = 3

    /**
     * Bad minimal issue year
     */
    const val BAD_MIN_ISSUE_YEAR = Constants.MIN_YEAR - 1

    /**
     * Bad maximal issue year
     */
    val BAD_MAX_ISSUE_YEAR = Constants.CURRENT_YEAR + 1

    /**
     * Returns book.
     *
     * @param id ID
     * @return book
     */
    fun newBook(id: Int?): cz.vhromada.book.entity.Book {
        val position = if (id == null) {
            0
        } else {
            id - 1
        }
        return cz.vhromada.book.entity.Book(id, "CzechName", "OriginalName", listOf(Language.CZ), "ISBN", 2000, "Description", true, false, "Note", position, listOf(AuthorUtils.getAuthorEntity(1)), listOf(CategoryUtils.getCategoryEntity(1)))
    }

    /**
     * Returns book.
     *
     * @param id ID
     * @return book
     */
    fun newBookDomain(id: Int?): Book {
        val position = if (id == null) {
            0
        } else {
            id - 1
        }
        return newBookDomain(id, position)
    }

    /**
     * Returns book.
     *
     * @param id       ID
     * @param position position
     * @return book
     */
    fun newBookDomain(id: Int?, position: Int): Book {
        return Book(id, "CzechName", "OriginalName", listOf(Language.CZ), "ISBN", 2000, "Description", true, false, "Note", position, listOf(AuthorUtils.getAuthor(1)), listOf(CategoryUtils.getCategory(1)))
    }

    /**
     * Returns book.
     *
     * @return book
     */
    fun getBooks(): List<Book> {
        return (0 until BOOKS_COUNT).map { getBook(it + 1) }
    }

    /**
     * Returns book for index.
     *
     * @param index index
     * @return book for index
     */
    fun getBook(index: Int): Book {
        val isbn = if (index != 2) {
            "Book $index ISBN"
        } else {
            null
        }

        val note = if (index != 2) {
            "Book $index Note"
        } else {
            null
        }

        val languages = when (index) {
            1 -> listOf(Language.CZ, Language.EN)
            2 -> listOf(Language.CZ)
            else -> listOf(Language.EN)
        }

        val books = mutableListOf(AuthorUtils.getAuthor(index))

        val categories = mutableListOf(CategoryUtils.getCategory(index))

        if (index == 1) {
            books.add(AuthorUtils.getAuthor(2))
            categories.add(CategoryUtils.getCategory(2))
        }

        return Book(index, "Book $index Czech Name", "Book $index Original Name", languages, isbn, 2000 + index, "Book $index Description", index != 3, index != 2, note, index - 1, books, categories)
    }

    /**
     * Returns book.
     *
     * @param entityManager entity manager
     * @param id            book's ID
     * @return book
     */
    fun getBook(entityManager: EntityManager, id: Int): Book? {
        return entityManager.find(Book::class.java, id)
    }

    /**
     * Returns count of book.
     *
     * @param entityManager entity manager
     * @return count of book
     */
    fun getBooksCount(entityManager: EntityManager): Int {
        return entityManager.createQuery("SELECT COUNT(b.id) FROM Book b", java.lang.Long::class.java).singleResult.toInt()
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected book
     * @param actual   actual book
     */
    fun assertBooksDeepEquals(expected: List<Book>?, actual: List<Book>?) {
        assertSoftly { softly ->
            softly.assertThat(expected).isNotNull
            softly.assertThat(actual).isNotNull
        }

        assertThat(expected!!.size).isEqualTo(actual!!.size)
        for (i in expected.indices) {
            assertBookDeepEquals(expected[i], actual[i])
        }
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected book
     * @param actual   actual book
     */
    fun assertBookDeepEquals(expected: Book?, actual: Book?) {
        assertSoftly { softly ->
            softly.assertThat(expected).isNotNull
            softly.assertThat(actual).isNotNull
        }

        assertSoftly { softly ->
            softly.assertThat(actual!!.id).isEqualTo(expected!!.id)
            softly.assertThat(actual.czechName).isEqualTo(expected.czechName)
            softly.assertThat(actual.originalName).isEqualTo(expected.originalName)
            softly.assertThat(actual.languages)
                .hasSameSizeAs(expected.languages)
                .hasSameElementsAs(expected.languages)
            softly.assertThat(actual.isbn).isEqualTo(expected.isbn)
            softly.assertThat(actual.issueYear).isEqualTo(expected.issueYear)
            softly.assertThat(actual.description).isEqualTo(expected.description)
            softly.assertThat(actual.electronic).isEqualTo(expected.electronic)
            softly.assertThat(actual.paper).isEqualTo(expected.paper)
            softly.assertThat(actual.note).isEqualTo(expected.note)
            softly.assertThat(actual.position).isEqualTo(expected.position)
            AuthorUtils.assertAuthorsDeepEquals(expected.authors, actual.authors)
            CategoryUtils.assertCategoriesDeepEquals(expected.categories, actual.categories)
        }
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected book
     * @param actual   actual book
     */
    fun assertBookListDeepEquals(expected: List<Book>?, actual: List<cz.vhromada.book.entity.Book>?) {
        assertSoftly { softly ->
            softly.assertThat(expected).isNotNull
            softly.assertThat(actual).isNotNull
        }

        assertThat(expected!!.size).isEqualTo(actual!!.size)
        for (i in expected.indices) {
            assertBookDeepEquals(expected[i], actual[i])
        }
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected book
     * @param actual   actual book
     */
    fun assertBookDeepEquals(expected: Book?, actual: cz.vhromada.book.entity.Book?) {
        assertSoftly { softly ->
            softly.assertThat(expected).isNotNull
            softly.assertThat(actual).isNotNull
        }

        assertSoftly { softly ->
            softly.assertThat(actual!!.id).isEqualTo(expected!!.id)
            softly.assertThat(actual.czechName).isEqualTo(expected.czechName)
            softly.assertThat(actual.originalName).isEqualTo(expected.originalName)
            softly.assertThat(actual.languages)
                .hasSameSizeAs(expected.languages)
                .hasSameElementsAs(expected.languages)
            softly.assertThat(actual.isbn).isEqualTo(expected.isbn)
            softly.assertThat(actual.issueYear).isEqualTo(expected.issueYear)
            softly.assertThat(actual.description).isEqualTo(expected.description)
            softly.assertThat(actual.electronic).isEqualTo(expected.electronic)
            softly.assertThat(actual.paper).isEqualTo(expected.paper)
            softly.assertThat(actual.note).isEqualTo(expected.note)
            softly.assertThat(actual.position).isEqualTo(expected.position)
            AuthorUtils.assertAuthorListDeepEquals(expected.authors, actual.authors)
            CategoryUtils.assertCategoryListDeepEquals(expected.categories, actual.categories)
        }
    }

}

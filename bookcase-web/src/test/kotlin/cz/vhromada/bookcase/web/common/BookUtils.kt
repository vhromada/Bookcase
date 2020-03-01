package cz.vhromada.bookcase.web.common

import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.web.fo.BookFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for books.
 *
 * @author Vladimir Hromada
 */
object BookUtils {

    /**
     * Returns FO for book.
     *
     * @return FO for book
     */
    fun getBookFO(): BookFO {
        return BookFO(
                id = 1,
                czechName = "Czech name",
                originalName = "Original name",
                isbn = "ISBN",
                issueYear = "2000",
                description = "Description",
                note = "Note",
                position = 0,
                authors = listOf(1),
                categories = listOf(1))
    }

    /**
     * Returns book.
     *
     * @return book
     */
    fun getBook(): Book {
        return Book(
                id = 1,
                czechName = "Czech name",
                originalName = "Original name",
                isbn = "ISBN",
                issueYear = 2000,
                description = "Description",
                note = "Note",
                position = 0,
                authors = listOf(AuthorUtils.getAuthor()),
                categories = listOf(CategoryUtils.getCategory()))
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected FO for book
     * @param actual   actual book
     */
    fun assertBookDeepEquals(expected: BookFO?, actual: Book?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(actual!!.id).isEqualTo(expected!!.id)
            it.assertThat(actual.czechName).isEqualTo(expected.czechName)
            it.assertThat(actual.originalName).isEqualTo(expected.originalName)
            it.assertThat(actual.isbn).isEqualTo(expected.isbn)
            it.assertThat(actual.issueYear).isEqualTo(expected.issueYear!!.toInt())
            it.assertThat(actual.description).isEqualTo(expected.description)
            it.assertThat(actual.note).isEqualTo(expected.note)
            it.assertThat(actual.position).isEqualTo(expected.position)
            it.assertThat(actual.authors).isNull()
            it.assertThat(actual.categories).isNull()
        }
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected FO for book
     * @param actual   actual book
     */
    fun assertBookDeepEquals(expected: Book?, actual: BookFO?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(actual!!.id).isEqualTo(expected!!.id)
            it.assertThat(actual.czechName).isEqualTo(expected.czechName)
            it.assertThat(actual.originalName).isEqualTo(expected.originalName)
            it.assertThat(actual.isbn).isEqualTo(expected.isbn)
            it.assertThat(actual.issueYear).isEqualTo(expected.issueYear.toString())
            it.assertThat(actual.description).isEqualTo(expected.description)
            it.assertThat(actual.note).isEqualTo(expected.note)
            it.assertThat(actual.position).isEqualTo(expected.position)
            AuthorUtils.assertAuthorsDeepEquals(expected.authors, actual.authors)
            CategoryUtils.assertCategoriesDeepEquals(expected.categories, actual.categories)
        }
    }

}

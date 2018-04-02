package cz.vhromada.book.facade.impl

import cz.vhromada.book.entity.Book
import cz.vhromada.book.facade.BookFacade
import cz.vhromada.book.facade.BookcaseFacade
import cz.vhromada.book.utils.AuthorUtils
import cz.vhromada.book.utils.BookUtils
import cz.vhromada.book.utils.CategoryUtils
import cz.vhromada.book.utils.Constants
import cz.vhromada.result.Event
import cz.vhromada.result.Severity
import cz.vhromada.result.Status
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import javax.persistence.EntityManager

/**
 * A class represents integration test for facade for books.
 *
 * @author Vladimir Hromada
 */
class BookFacadeIntegrationTest : AbstractFacadeIntegrationTest<Book, cz.vhromada.book.domain.Book>() {

    override val defaultDataCount = BookUtils.BOOKS_COUNT

    override var name = "Book"

    /**
     * Event for invalid issue year
     */
    private val invalidIssueYearEvent = Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NOT_VALID", "Issue year must be between " + Constants.MIN_YEAR + " and " + Constants.CURRENT_YEAR + '.')

    /**
     * Instance of [EntityManager]
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private val entityManager: EntityManager? = null

    /**
     * Instance of [BookFacade]
     */
    @Autowired
    private val bookFacade: BookFacade? = null

    /**
     * Checks autowired fields.
     */
    @BeforeEach
    fun setUp() {
        assertSoftly { softly ->
            softly.assertThat(bookFacade).isNotNull
            softly.assertThat(entityManager).isNotNull
        }
    }

    /**
     * Test method for [BookFacade.add] with data with empty czech name.
     */
    @Test
    fun add_EmptyCzechName() {
        val book = newData(null).copy(czechName = "")

        val result = bookFacade!!.add(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with data with empty original name.
     */
    @Test
    fun add_EmptyOriginalName() {
        val book = newData(null).copy(originalName = "")

        val result = bookFacade!!.add(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with data with ISBN.
     */
    @Test
    fun add_EmptyISBN() {
        val book = newData(null).copy(isbn = "")

        val result = bookFacade!!.add(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ISBN_EMPTY", "ISBN mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with data with bad minimum issue year.
     */
    @Test
    fun add_BadMinimumIssueYear() {
        val book = newData(null).copy(issueYear = BookUtils.BAD_MIN_ISSUE_YEAR)

        val result = bookFacade!!.add(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(invalidIssueYearEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with data with bad maximum issue year.
     */
    @Test
    fun add_BadMaximumIssueYear() {
        val book = newData(null).copy(issueYear = BookUtils.BAD_MAX_ISSUE_YEAR)

        val result = bookFacade!!.add(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(invalidIssueYearEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with data with empty description.
     */
    @Test
    fun add_EmptyDescription() {
        val book = newData(null).copy(description = "")

        val result = bookFacade!!.add(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with data with empty note.
     */
    @Test
    fun add_EmptyNote() {
        val book = newData(null).copy(note = "")

        val result = bookFacade!!.add(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_NOTE_EMPTY", "Note mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with author with author with empty first name.
     */
    @Test
    fun add_AuthorEmptyFirstName() {
        val book = newData(null).copy(authors = listOf(AuthorUtils.getAuthorEntity(1).copy(firstName = "")))

        val result = bookFacade!!.add(book)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with author with author with empty middle name.
     */
    @Test
    fun add_AuthorEmptyMiddleName() {
        val book = newData(null).copy(authors = listOf(AuthorUtils.getAuthorEntity(1).copy(middleName = "")))

        val result = bookFacade!!.add(book)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with author with author with empty last name.
     */
    @Test
    fun add_AuthorEmptyLastName() {
        val book = newData(null).copy(authors = listOf(AuthorUtils.getAuthorEntity(1).copy(lastName = "")))

        val result = bookFacade!!.add(book)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with author with category with empty name.
     */
    @Test
    fun add_CategoryEmptyName() {
        val book = newData(null).copy(categories = listOf(CategoryUtils.getCategoryEntity(1).copy(name = "")))

        val result = bookFacade!!.add(book)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with data with empty czech name.
     */
    @Test
    fun update_EmptyCzechName() {
        val book = newData(1).copy(czechName = "")

        val result = bookFacade!!.update(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with data with empty original name.
     */
    @Test
    fun update_EmptyOriginalName() {
        val book = newData(1).copy(originalName = "")

        val result = bookFacade!!.update(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with data with ISBN.
     */
    @Test
    fun update_EmptyISBN() {
        val book = newData(1).copy(isbn = "")

        val result = bookFacade!!.update(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ISBN_EMPTY", "ISBN mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with data with bad minimum issue year.
     */
    @Test
    fun update_BadMinimumIssueYear() {
        val book = newData(1).copy(issueYear = BookUtils.BAD_MIN_ISSUE_YEAR)

        val result = bookFacade!!.update(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(invalidIssueYearEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with data with bad maximum issue year.
     */
    @Test
    fun update_BadMaximumIssueYear() {
        val book = newData(1).copy(issueYear = BookUtils.BAD_MAX_ISSUE_YEAR)

        val result = bookFacade!!.update(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(invalidIssueYearEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with data with empty description.
     */
    @Test
    fun update_EmptyDescription() {
        val book = newData(1).copy(description = "")

        val result = bookFacade!!.update(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with data with empty note.
     */
    @Test
    fun update_EmptyNote() {
        val book = newData(1).copy(note = "")

        val result = bookFacade!!.update(book)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_NOTE_EMPTY", "Note mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with author with author with empty first name.
     */
    @Test
    fun update_AuthorEmptyFirstName() {
        val book = newData(1).copy(authors = listOf(AuthorUtils.getAuthorEntity(1).copy(firstName = "")))

        val result = bookFacade!!.update(book)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with author with author with empty middle name.
     */
    @Test
    fun update_AuthorEmptyMiddleName() {
        val book = newData(1).copy(authors = listOf(AuthorUtils.getAuthorEntity(1).copy(middleName = "")))

        val result = bookFacade!!.update(book)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with author with author with empty last name.
     */
    @Test
    fun update_AuthorEmptyLastName() {
        val book = newData(1).copy(authors = listOf(AuthorUtils.getAuthorEntity(1).copy(lastName = "")))

        val result = bookFacade!!.update(book)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with author with category with empty name.
     */
    @Test
    fun update_CategoryEmptyName() {
        val book = newData(1).copy(categories = listOf(CategoryUtils.getCategoryEntity(1).copy(name = "")))

        val result = bookFacade!!.update(book)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    override fun getBookcaseFacade(): BookcaseFacade<Book> {
        return bookFacade!!
    }

    override fun getRepositoryDataCount(): Int {
        return BookUtils.getBooksCount(entityManager!!)
    }

    override fun getDataList(): List<cz.vhromada.book.domain.Book> {
        return BookUtils.getBooks()
    }

    override fun getDomainData(index: Int): cz.vhromada.book.domain.Book {
        return BookUtils.getBook(index)
    }

    override fun newData(id: Int?): Book {
        return BookUtils.newBook(id)
    }

    override fun newDomainData(id: Int): cz.vhromada.book.domain.Book {
        return BookUtils.newBookDomain(id)
    }

    override fun getRepositoryData(id: Int): cz.vhromada.book.domain.Book? {
        return BookUtils.getBook(entityManager!!, id)
    }

    override fun clearReferencedData() {}

    override fun assertDataListDeepEquals(expected: List<Book>, actual: List<cz.vhromada.book.domain.Book>) {
        BookUtils.assertBookListDeepEquals(actual, expected)
    }

    override fun assertDataDeepEquals(expected: Book, actual: cz.vhromada.book.domain.Book) {
        BookUtils.assertBookDeepEquals(actual, expected)
    }

    override fun assertDataDomainDeepEquals(expected: cz.vhromada.book.domain.Book, actual: cz.vhromada.book.domain.Book) {
        BookUtils.assertBookDeepEquals(actual, expected)
    }

}

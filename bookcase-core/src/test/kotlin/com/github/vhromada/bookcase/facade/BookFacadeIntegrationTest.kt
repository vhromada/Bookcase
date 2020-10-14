package com.github.vhromada.bookcase.facade

import com.github.vhromada.bookcase.BookcaseTestConfiguration
import com.github.vhromada.bookcase.entity.Book
import com.github.vhromada.bookcase.utils.AuthorUtils
import com.github.vhromada.bookcase.utils.BookUtils
import com.github.vhromada.bookcase.utils.ItemUtils
import com.github.vhromada.common.facade.MovableParentFacade
import com.github.vhromada.common.result.Event
import com.github.vhromada.common.result.Severity
import com.github.vhromada.common.result.Status
import com.github.vhromada.common.test.facade.MovableParentFacadeIntegrationTest
import com.github.vhromada.common.test.utils.TestConstants
import com.github.vhromada.common.utils.Constants
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.test.context.ContextConfiguration
import javax.persistence.EntityManager

/**
 * A class represents integration test for class [BookFacade].
 *
 * @author Vladimir Hromada
 */
@ContextConfiguration(classes = [BookcaseTestConfiguration::class])
class BookFacadeIntegrationTest : MovableParentFacadeIntegrationTest<Book, com.github.vhromada.bookcase.domain.Book>() {

    /**
     * Instance of [EntityManager]
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private lateinit var entityManager: EntityManager

    /**
     * Instance of [BookFacade]
     */
    @Autowired
    private lateinit var bookFacade: BookFacade

    /**
     * Test method for [BookFacade.add] with book with null czech name.
     */
    @Test
    fun addNullCzechName() {
        val book = newData(null)
                .copy(czechName = null)

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_CZECH_NAME_NULL", "Czech name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with empty string as czech name.
     */
    @Test
    fun addEmptyCzechName() {
        val book = newData(null)
                .copy(czechName = "")

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with null original name.
     */
    @Test
    fun addNullOriginalName() {
        val book = newData(null)
                .copy(originalName = null)

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_NULL", "Original name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with empty string as original name.
     */
    @Test
    fun addEmptyOriginalName() {
        val book = newData(null)
                .copy(originalName = "")

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with null issue year.
     */
    @Test
    fun addNullIssueYear() {
        val book = newData(null)
                .copy(issueYear = null)

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NULL", "Issue year mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with bad minimum issue year.
     */
    @Test
    fun addBadMinimumIssueYear() {
        val book = newData(null)
                .copy(issueYear = TestConstants.BAD_MIN_YEAR)

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(INVALID_ISSUE_YEAR_EVENT))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with bad maximum issue year.
     */
    @Test
    fun addBadMaximumIssueYear() {
        val book = newData(null)
                .copy(issueYear = TestConstants.BAD_MAX_YEAR)

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(INVALID_ISSUE_YEAR_EVENT))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with null description.
     */
    @Test
    fun addNullDescription() {
        val book = newData(null)
                .copy(description = null)

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_DESCRIPTION_NULL", "Description mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with empty string as description.
     */
    @Test
    fun addEmptyDescription() {
        val book = newData(null)
                .copy(description = "")

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with nulls note.
     */
    @Test
    fun addNullNote() {
        val book = newData(null)
                .copy(note = null)

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_NOTE_NULL", "Note mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with null authors.
     */
    @Test
    fun addNullAuthors() {
        val book = newData(null)
                .copy(authors = null)

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_AUTHORS_NULL", "Authors mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with authors with null value.
     */
    @Test
    fun addBadAuthors() {
        val book = newData(null)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), null))

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_AUTHORS_CONTAIN_NULL", "Authors mustn't contain null value.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with authors with author with null ID.
     */
    @Test
    fun addNullAuthorId() {
        val book = newData(null)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), AuthorUtils.newAuthor(null)))

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_ID_NULL", "ID mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with authors with author with null first name.
     */
    @Test
    fun addNullAuthorFirstName() {
        val badAuthor = AuthorUtils.newAuthor(1)
                .copy(firstName = null)
        val book = newData(null)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), badAuthor))

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with authors with author with empty string as first name.
     */
    @Test
    fun addEmptyAuthorFirstName() {
        val badAuthor = AuthorUtils.newAuthor(1)
                .copy(firstName = "")
        val book = newData(null)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), badAuthor))

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with authors with author with empty string as middle name.
     */
    @Test
    fun addNullAuthorMiddleName() {
        val badAuthor = AuthorUtils.newAuthor(1)
                .copy(middleName = null)
        val book = newData(null)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), badAuthor))

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_NULL", "Middle name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with authors with author with null last name.
     */
    @Test
    fun addNullAuthorLastName() {
        val badAuthor = AuthorUtils.newAuthor(1)
                .copy(lastName = null)
        val book = newData(null)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), badAuthor))

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with book with authors with author with empty string as last name.
     */
    @Test
    fun addEmptyAuthorLastName() {
        val badAuthor = AuthorUtils.newAuthor(1)
                .copy(lastName = "")
        val book = newData(null)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), badAuthor))

        val result = bookFacade.add(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.add] with show with authors with not existing author.
     */
    @Test
    fun addNotExistingAuthor() {
        val show = newData(null)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), AuthorUtils.newAuthor(Int.MAX_VALUE)))

        val result = bookFacade.add(show)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_NOT_EXIST", "Author doesn't exist.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with null czech name.
     */
    @Test
    fun updateNullCzechName() {
        val book = newData(1)
                .copy(czechName = null)

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_CZECH_NAME_NULL", "Czech name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with empty string as czech name.
     */
    @Test
    fun updateEmptyCzechName() {
        val book = newData(1)
                .copy(czechName = "")

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with null original name.
     */
    @Test
    fun updateNullOriginalName() {
        val book = newData(1)
                .copy(originalName = null)

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_NULL", "Original name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with empty string as original name.
     */
    @Test
    fun updateEmptyOriginalName() {
        val book = newData(1)
                .copy(originalName = "")

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with null issue year.
     */
    @Test
    fun updateNullIssueYear() {
        val book = newData(1)
                .copy(issueYear = null)

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NULL", "Issue year mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with bad minimum issue year.
     */
    @Test
    fun updateBadMinimumIssueYear() {
        val book = newData(1)
                .copy(issueYear = TestConstants.BAD_MIN_YEAR)

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(INVALID_ISSUE_YEAR_EVENT))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with bad maximum issue year.
     */
    @Test
    fun updateBadMaximumIssueYear() {
        val book = newData(1)
                .copy(issueYear = TestConstants.BAD_MAX_YEAR)

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(INVALID_ISSUE_YEAR_EVENT))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with null description.
     */
    @Test
    fun updateNullDescription() {
        val book = newData(1)
                .copy(description = null)

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_DESCRIPTION_NULL", "Description mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with empty string as description.
     */
    @Test
    fun updateEmptyDescription() {
        val book = newData(1)
                .copy(description = "")

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with nulls note.
     */
    @Test
    fun updateNullNote() {
        val book = newData(1)
                .copy(note = null)

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_NOTE_NULL", "Note mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with null authors.
     */
    @Test
    fun updateNullAuthors() {
        val book = newData(1)
                .copy(authors = null)

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_AUTHORS_NULL", "Authors mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with authors with null value.
     */
    @Test
    fun updateBadAuthors() {
        val book = newData(1)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), null))

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_AUTHORS_CONTAIN_NULL", "Authors mustn't contain null value.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with authors with author with null ID.
     */
    @Test
    fun updateNullAuthorId() {
        val book = newData(1)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), AuthorUtils.newAuthor(null)))

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_ID_NULL", "ID mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with authors with author with null first name.
     */
    @Test
    fun updateNullAuthorFirstName() {
        val badAuthor = AuthorUtils.newAuthor(1)
                .copy(firstName = null)
        val book = newData(1)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), badAuthor))

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with authors with author with empty string as first name.
     */
    @Test
    fun updateEmptyAuthorFirstName() {
        val badAuthor = AuthorUtils.newAuthor(1)
                .copy(firstName = "")
        val book = newData(1)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), badAuthor))

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with authors with author with empty string as middle name.
     */
    @Test
    fun updateNullAuthorMiddleName() {
        val badAuthor = AuthorUtils.newAuthor(1)
                .copy(middleName = null)
        val book = newData(1)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), badAuthor))

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_NULL", "Middle name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with authors with author with null last name.
     */
    @Test
    fun updateNullAuthorLastName() {
        val badAuthor = AuthorUtils.newAuthor(1)
                .copy(lastName = null)
        val book = newData(1)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), badAuthor))

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with book with authors with author with empty string as last name.
     */
    @Test
    fun updateEmptyAuthorLastName() {
        val badAuthor = AuthorUtils.newAuthor(1)
                .copy(lastName = "")
        val book = newData(1)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), badAuthor))

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookFacade.update] with show with authors with not existing author.
     */
    @Test
    fun updateNotExistingAuthor() {
        val book = newData(1)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), AuthorUtils.newAuthor(Int.MAX_VALUE)))

        val result = bookFacade.update(book)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_NOT_EXIST", "Author doesn't exist.")))
        }

        assertDefaultRepositoryData()
    }

    override fun getFacade(): MovableParentFacade<Book> {
        return bookFacade
    }

    override fun getDefaultDataCount(): Int {
        return BookUtils.BOOKS_COUNT
    }

    override fun getRepositoryDataCount(): Int {
        return BookUtils.getBooksCount(entityManager)
    }

    override fun getDataList(): List<com.github.vhromada.bookcase.domain.Book> {
        return BookUtils.getBooks()
    }

    override fun getDomainData(index: Int): com.github.vhromada.bookcase.domain.Book {
        return BookUtils.getBook(index)
    }

    override fun newData(id: Int?): Book {
        var book = BookUtils.newBook(id)
        if (id == null || Int.MAX_VALUE == id) {
            book = book.copy(authors = listOf(AuthorUtils.getAuthor(1)))
        }
        return book
    }

    override fun newDomainData(id: Int): com.github.vhromada.bookcase.domain.Book {
        return BookUtils.newBookDomain(id)
    }

    override fun getRepositoryData(id: Int): com.github.vhromada.bookcase.domain.Book? {
        return BookUtils.getBook(entityManager, id)
    }

    override fun getName(): String {
        return "Book"
    }

    override fun clearReferencedData() {}

    override fun assertDataListDeepEquals(expected: List<Book>, actual: List<com.github.vhromada.bookcase.domain.Book>) {
        BookUtils.assertBookListDeepEquals(expected, actual)
    }

    override fun assertDataDeepEquals(expected: Book, actual: com.github.vhromada.bookcase.domain.Book) {
        BookUtils.assertBookDeepEquals(expected, actual)
    }

    override fun assertDataDomainDeepEquals(expected: com.github.vhromada.bookcase.domain.Book, actual: com.github.vhromada.bookcase.domain.Book) {
        BookUtils.assertBookDeepEquals(expected, actual)
    }

    override fun assertDefaultRepositoryData() {
        super.assertDefaultRepositoryData()

        assertReferences()
    }

    override fun assertNewRepositoryData() {
        super.assertNewRepositoryData()

        assertSoftly {
            it.assertThat(ItemUtils.getItemsCount(entityManager)).isEqualTo(0)
            it.assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
        }
    }

    override fun assertAddRepositoryData() {
        super.assertAddRepositoryData()

        assertReferences()
    }

    override fun assertUpdateRepositoryData() {
        super.assertUpdateRepositoryData()

        assertReferences()
    }

    override fun assertRemoveRepositoryData() {
        super.assertRemoveRepositoryData()

        assertSoftly {
            it.assertThat(ItemUtils.getItemsCount(entityManager)).isEqualTo(ItemUtils.ITEMS_COUNT - ItemUtils.ITEMS_PER_BOOK_COUNT)
            it.assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
        }
    }

    override fun assertDuplicateRepositoryData() {
        super.assertDuplicateRepositoryData()

        assertSoftly {
            it.assertThat(ItemUtils.getItemsCount(entityManager)).isEqualTo(ItemUtils.ITEMS_COUNT + ItemUtils.ITEMS_PER_BOOK_COUNT)
            it.assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
        }
    }

    override fun getUpdateData(id: Int?): Book {
        return super.getUpdateData(id)
                .copy(authors = listOf(AuthorUtils.getAuthor(1)))
    }

    override fun getExpectedAddData(): com.github.vhromada.bookcase.domain.Book {
        return super.getExpectedAddData()
                .copy(authors = listOf(AuthorUtils.getAuthorDomain(1)))
    }

    override fun getExpectedDuplicatedData(): com.github.vhromada.bookcase.domain.Book {
        return super.getExpectedDuplicatedData()
                .copy(authors = listOf(AuthorUtils.getAuthorDomain(3)))
    }

    /**
     * Asserts references.
     */
    private fun assertReferences() {
        assertSoftly {
            it.assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
            it.assertThat(ItemUtils.getItemsCount(entityManager)).isEqualTo(ItemUtils.ITEMS_COUNT)
        }
    }

    companion object {

        /**
         * Event for invalid year
         */
        private val INVALID_ISSUE_YEAR_EVENT = Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NOT_VALID",
                "Issue year must be between ${Constants.MIN_YEAR} and ${Constants.CURRENT_YEAR}.")

    }

}

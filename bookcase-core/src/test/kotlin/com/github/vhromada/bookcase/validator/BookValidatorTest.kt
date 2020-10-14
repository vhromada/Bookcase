package com.github.vhromada.bookcase.validator

import com.github.vhromada.bookcase.entity.Author
import com.github.vhromada.bookcase.entity.Book
import com.github.vhromada.bookcase.utils.AuthorUtils
import com.github.vhromada.bookcase.utils.BookUtils
import com.github.vhromada.common.result.Event
import com.github.vhromada.common.result.Result
import com.github.vhromada.common.result.Severity
import com.github.vhromada.common.result.Status
import com.github.vhromada.common.test.utils.TestConstants
import com.github.vhromada.common.test.validator.MovableValidatorTest
import com.github.vhromada.common.utils.Constants
import com.github.vhromada.common.validator.MovableValidator
import com.github.vhromada.common.validator.ValidationType
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import org.mockito.Mock

/**
 * A class represents test for class [BookValidator].
 *
 * @author Vladimir Hromada
 */
class BookValidatorTest : MovableValidatorTest<Book, com.github.vhromada.bookcase.domain.Book>() {

    /**
     * Validator for author
     */
    @Mock
    private lateinit var authorValidator: MovableValidator<Author>

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with null czech name.
     */
    @Test
    fun validateDeepNullCzechName() {
        val book = getValidatingData(1)
                .copy(czechName = null)

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_CZECH_NAME_NULL", "Czech name mustn't be null.")))
        }

        verifyDeepMock(book)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with empty string as
     * czech name.
     */
    @Test
    fun validateDeepEmptyCzechName() {
        val book = getValidatingData(1)
                .copy(czechName = "")

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string.")))
        }

        verifyDeepMock(book)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with null original name.
     */
    @Test
    fun validateDeepNullOriginalName() {
        val book = getValidatingData(1)
                .copy(originalName = null)

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_NULL", "Original name mustn't be null.")))
        }

        verifyDeepMock(book)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with empty string as
     * original name.
     */
    @Test
    fun validateDeepEmptyOriginalName() {
        val book = getValidatingData(1)
                .copy(originalName = "")

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string.")))
        }

        verifyDeepMock(book)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with null issue year.
     */
    @Test
    fun validateDeepBadNullIssueYear() {
        val book = getValidatingData(1)
                .copy(issueYear = null)

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NULL", "Issue year mustn't be null.")))
        }

        verifyDeepMock(book)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with bad minimum issue year.
     */
    @Test
    fun validateDeepBadMinimumIssueYear() {
        val book = getValidatingData(1)
                .copy(issueYear = TestConstants.BAD_MIN_YEAR)

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(INVALID_ISSUE_YEAR_EVENT))
        }

        verifyDeepMock(book)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with bad maximum issue year.
     */
    @Test
    fun validateDeepBadMaximumIssueYear() {
        val book = getValidatingData(1)
                .copy(issueYear = TestConstants.BAD_MAX_YEAR)

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(INVALID_ISSUE_YEAR_EVENT))
        }

        verifyDeepMock(book)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with null description.
     */
    @Test
    fun validateDeepNullDescription() {
        val book = getValidatingData(1)
                .copy(description = null)

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_DESCRIPTION_NULL", "Description mustn't be null.")))
        }

        verifyDeepMock(book)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with empty string as description.
     */
    @Test
    fun validateDeepEmptyDescription() {
        val book = getValidatingData(1)
                .copy(description = "")

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string.")))
        }

        verifyDeepMock(book)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with null note.
     */
    @Test
    fun validateDeepNullNote() {
        val book = getValidatingData(1)
                .copy(note = null)

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)
        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_NOTE_NULL", "Note mustn't be null.")))
        }

        verifyDeepMock(book)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with null authors.
     */
    @Test
    fun validateDeepNullAuthors() {
        val book = getValidatingData(1)
                .copy(authors = null)

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_AUTHORS_NULL", "Authors mustn't be null.")))
        }

        verifyZeroInteractions(service, authorValidator)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with authors with null value.
     */
    @Test
    fun validateDeepBadAuthors() {
        val book = getValidatingData(1)
                .copy(authors = listOf(AuthorUtils.newAuthor(1), null))

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)
        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_AUTHORS_CONTAIN_NULL", "Authors mustn't contain null value.")))
        }

        verify(authorValidator).validate(book.authors!![0], ValidationType.EXISTS, ValidationType.DEEP)
        verifyNoMoreInteractions(authorValidator)
        verifyZeroInteractions(service)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with authors with author with invalid data.
     */
    @Test
    fun validateDeepAuthorsWithAuthorWithInvalidData() {
        val event = Event(Severity.ERROR, "CATEGORY_INVALID", "Invalid data")
        val book = getValidatingData(1)
                .copy(authors = listOf(AuthorUtils.newAuthor(null)))

        whenever(authorValidator.validate(any(), any())).thenReturn(Result.error(event.key, event.message))

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(event))
        }

        verifyDeepMock(book)
    }

    override fun initDeepMock(validatingData: Book) {
        super.initDeepMock(validatingData)

        whenever(authorValidator.validate(any(), any())).thenReturn(Result())
    }

    override fun verifyDeepMock(validatingData: Book) {
        super.verifyDeepMock(validatingData)

        for (author in validatingData.authors!!) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP)
        }
        verifyNoMoreInteractions(authorValidator)
        verifyZeroInteractions(service)
    }

    override fun getValidator(): MovableValidator<Book> {
        return BookValidator(service, authorValidator)
    }

    override fun getValidatingData(id: Int?): Book {
        return BookUtils.newBook(id)
    }

    override fun getValidatingData(id: Int?, position: Int?): Book {
        return BookUtils.newBook(id)
                .copy(position = position)
    }

    override fun getRepositoryData(validatingData: Book): com.github.vhromada.bookcase.domain.Book {
        return BookUtils.newBookDomain(validatingData.id)
    }

    override fun getItem1(): com.github.vhromada.bookcase.domain.Book {
        return BookUtils.newBookDomain(1)
    }

    override fun getItem2(): com.github.vhromada.bookcase.domain.Book {
        return BookUtils.newBookDomain(2)
    }

    override fun getName(): String {
        return "Book"
    }

    companion object {

        /**
         * Event for invalid year
         */
        private val INVALID_ISSUE_YEAR_EVENT = Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NOT_VALID",
                "Issue year must be between ${Constants.MIN_YEAR} and ${Constants.CURRENT_YEAR}.")

    }

}

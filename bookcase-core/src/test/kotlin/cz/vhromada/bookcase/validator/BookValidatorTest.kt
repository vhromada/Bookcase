package cz.vhromada.bookcase.validator

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import cz.vhromada.bookcase.entity.Author
import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.entity.Category
import cz.vhromada.bookcase.utils.AuthorUtils
import cz.vhromada.bookcase.utils.BookUtils
import cz.vhromada.bookcase.utils.CategoryUtils
import cz.vhromada.common.result.Event
import cz.vhromada.common.result.Result
import cz.vhromada.common.result.Severity
import cz.vhromada.common.result.Status
import cz.vhromada.common.test.utils.TestConstants
import cz.vhromada.common.test.validator.MovableValidatorTest
import cz.vhromada.common.utils.Constants
import cz.vhromada.common.validator.MovableValidator
import cz.vhromada.common.validator.ValidationType
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import org.mockito.Mock

/**
 * A class represents test for class [BookValidator].
 *
 * @author Vladimir Hromada
 */
class BookValidatorTest : MovableValidatorTest<Book, cz.vhromada.bookcase.domain.Book>() {

    /**
     * Validator for author
     */
    @Mock
    private lateinit var authorValidator: MovableValidator<Author>

    /**
     * Validator for category
     */
    @Mock
    private lateinit var categoryValidator: MovableValidator<Category>

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

        whenever(categoryValidator.validate(any(), any())).thenReturn(Result())

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_AUTHORS_NULL", "Authors mustn't be null.")))
        }

        for (category in book.categories!!) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP)
        }
        verifyNoMoreInteractions(categoryValidator)
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
        for (category in book.categories!!) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP)
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator)
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
        whenever(categoryValidator.validate(any(), any())).thenReturn(Result())

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(event))
        }

        verifyDeepMock(book)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with null categories.
     */
    @Test
    fun validateDeepNullCategories() {
        val book = getValidatingData(1)
                .copy(categories = null)

        whenever(authorValidator.validate(any(), any())).thenReturn(Result())

        val result = getValidator().validate(book, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_CATEGORIES_NULL", "Categories mustn't be null.")))
        }

        for (author in book.authors!!) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP)
        }
        verifyNoMoreInteractions(authorValidator)
        verifyZeroInteractions(service, categoryValidator)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with categories with null value.
     */
    @Test
    fun validateDeepBadCategories() {
        val book = getValidatingData(1)
                .copy(categories = listOf(CategoryUtils.newCategory(1), null))

        initDeepMock(book)

        val result = getValidator().validate(book, ValidationType.DEEP)
        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_CATEGORIES_CONTAIN_NULL", "Categories mustn't contain null value.")))
        }

        for (author in book.authors!!) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP)
        }
        verify(categoryValidator).validate(book.categories!![0], ValidationType.EXISTS, ValidationType.DEEP)
        verifyNoMoreInteractions(authorValidator, categoryValidator)
        verifyZeroInteractions(service)
    }

    /**
     * Test method for [BookValidator.validate]} with [ValidationType.DEEP] with data with categories with category with
     * invalid data.
     */
    @Test
    fun validateDeepCategoriesWithCategoryWithInvalidData() {
        val event = Event(Severity.ERROR, "CATEGORY_INVALID", "Invalid data")
        val book = getValidatingData(1)
                .copy(categories = listOf(CategoryUtils.newCategory(null)))

        whenever(authorValidator.validate(any(), any())).thenReturn(Result())
        whenever(categoryValidator.validate(any(), any())).thenReturn(Result.error(event.key, event.message))

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
        whenever(categoryValidator.validate(any(), any())).thenReturn(Result())
    }

    override fun verifyDeepMock(validatingData: Book) {
        super.verifyDeepMock(validatingData)

        for (author in validatingData.authors!!) {
            verify(authorValidator).validate(author, ValidationType.EXISTS, ValidationType.DEEP)
        }
        for (category in validatingData.categories!!) {
            verify(categoryValidator).validate(category, ValidationType.EXISTS, ValidationType.DEEP)
        }
        verifyNoMoreInteractions(authorValidator, categoryValidator)
        verifyZeroInteractions(service)
    }

    override fun getValidator(): MovableValidator<Book> {
        return BookValidator(service, authorValidator, categoryValidator)
    }

    override fun getValidatingData(id: Int?): Book {
        return BookUtils.newBook(id)
    }

    override fun getValidatingData(id: Int?, position: Int?): Book {
        return BookUtils.newBook(id)
                .copy(position = position)
    }

    override fun getRepositoryData(validatingData: Book): cz.vhromada.bookcase.domain.Book {
        return BookUtils.newBookDomain(validatingData.id)
    }

    override fun getItem1(): cz.vhromada.bookcase.domain.Book {
        return BookUtils.newBookDomain(1)
    }

    override fun getItem2(): cz.vhromada.bookcase.domain.Book {
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

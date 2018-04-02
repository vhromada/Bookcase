package cz.vhromada.book.validator.impl

import cz.vhromada.book.common.Language
import cz.vhromada.book.entity.Author
import cz.vhromada.book.entity.Book
import cz.vhromada.book.entity.Category
import cz.vhromada.book.stub.ValidatorStub
import cz.vhromada.book.stub.impl.validator.ErrorValidatorStub
import cz.vhromada.book.stub.impl.validator.OkValidatorStub
import cz.vhromada.book.utils.AuthorUtils
import cz.vhromada.book.utils.BookUtils
import cz.vhromada.book.utils.CategoryUtils
import cz.vhromada.book.utils.Constants
import cz.vhromada.book.validator.BookcaseValidator
import cz.vhromada.book.validator.common.ValidationType
import cz.vhromada.result.Event
import cz.vhromada.result.Severity
import cz.vhromada.result.Status
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

/**
 * A class represents test for class [BookValidatorImpl].
 *
 * @author Vladimir Hromada
 */
class BookValidatorImplTest : AbstractValidatorTest<Book, cz.vhromada.book.domain.Book>() {

    override val name = "Book"

    /**
     * Validator for author
     */
    private var authorValidator: ValidatorStub<Author> = OkValidatorStub()

    /**
     * Validator for category
     */
    private var categoryValidator: ValidatorStub<Category> = OkValidatorStub()

    /**
     * Event for invalid issue year
     */
    private val invalidIssueYearEvent = Event(Severity.ERROR, "BOOK_ISSUE_YEAR_NOT_VALID", "Issue year must be between " + Constants.MIN_YEAR + " and " + Constants.CURRENT_YEAR + '.')

    /**
     * Error event
     */
    private val errorEventMessage = "Invalid data"

    /**
     * Test method for [BookValidatorImpl.validate] with [ValidationType.DEEP] with data with empty czech name.
     */
    @Test
    fun validate_Deep_EmptyCzechName() {
        val book = Book(1, "", BookUtils.ORIGINAL_NAME, listOf(Language.CZ), BookUtils.ISBN, BookUtils.ISSUE_YEAR, BookUtils.DESCRIPTION, BookUtils.ELECTRONIC, BookUtils.PAPER, BookUtils.NOTE, 0, listOf(AuthorUtils.newAuthor(1)), listOf(CategoryUtils.newCategory(1)))

        val result = getBookcaseValidator().validate(book, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_CZECH_NAME_EMPTY", "Czech name mustn't be empty string.")))
        }

        for (author in book.authors) {
            authorValidator.verify(author)
        }
        for (category in book.categories) {
            categoryValidator.verify(category)
        }
        authorValidator.verifyNoMoreInteractions()
        categoryValidator.verifyNoMoreInteractions()
        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [BookValidatorImpl.validate] with [ValidationType.DEEP] with data with empty original name.
     */
    @Test
    fun validate_Deep_EmptyOriginalName() {
        val book = Book(1, BookUtils.CZECH_NAME, "", listOf(Language.CZ), BookUtils.ISBN, BookUtils.ISSUE_YEAR, BookUtils.DESCRIPTION, BookUtils.ELECTRONIC, BookUtils.PAPER, BookUtils.NOTE, 0, listOf(AuthorUtils.newAuthor(1)), listOf(CategoryUtils.newCategory(1)))

        val result = getBookcaseValidator().validate(book, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ORIGINAL_NAME_EMPTY", "Original name mustn't be empty string.")))
        }

        for (author in book.authors) {
            authorValidator.verify(author)
        }
        for (category in book.categories) {
            categoryValidator.verify(category)
        }
        authorValidator.verifyNoMoreInteractions()
        categoryValidator.verifyNoMoreInteractions()
        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [BookValidatorImpl.validate] with [ValidationType.DEEP] with data with ISBN.
     */
    @Test
    fun validate_Deep_EmptyISBN() {
        val book = Book(1, BookUtils.CZECH_NAME, BookUtils.ORIGINAL_NAME, listOf(Language.CZ), "", BookUtils.ISSUE_YEAR, BookUtils.DESCRIPTION, BookUtils.ELECTRONIC, BookUtils.PAPER, BookUtils.NOTE, 0, listOf(AuthorUtils.newAuthor(1)), listOf(CategoryUtils.newCategory(1)))

        val result = getBookcaseValidator().validate(book, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_ISBN_EMPTY", "ISBN mustn't be empty string.")))
        }

        for (author in book.authors) {
            authorValidator.verify(author)
        }
        for (category in book.categories) {
            categoryValidator.verify(category)
        }
        authorValidator.verifyNoMoreInteractions()
        categoryValidator.verifyNoMoreInteractions()
        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [BookValidatorImpl.validate]} with [ValidationType.DEEP] with data with bad minimum issue year.
     */
    @Test
    fun validate_Deep_BadMinimumIssueYear() {
        val book = Book(1, BookUtils.CZECH_NAME, BookUtils.ORIGINAL_NAME, listOf(Language.CZ), BookUtils.ISBN, BookUtils.BAD_MIN_ISSUE_YEAR, BookUtils.DESCRIPTION, BookUtils.ELECTRONIC, BookUtils.PAPER, BookUtils.NOTE, 0, listOf(AuthorUtils.newAuthor(1)), listOf(CategoryUtils.newCategory(1)))

        val result = getBookcaseValidator().validate(book, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(invalidIssueYearEvent))
        }

        for (author in book.authors) {
            authorValidator.verify(author)
        }
        for (category in book.categories) {
            categoryValidator.verify(category)
        }
        authorValidator.verifyNoMoreInteractions()
        categoryValidator.verifyNoMoreInteractions()
        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [BookValidatorImpl.validate]} with [ValidationType.DEEP] with data with bad maximum issue year.
     */
    @Test
    fun validate_Deep_BadMaximumIssueYear() {
        val book = Book(1, BookUtils.CZECH_NAME, BookUtils.ORIGINAL_NAME, listOf(Language.CZ), BookUtils.ISBN, BookUtils.BAD_MAX_ISSUE_YEAR, BookUtils.DESCRIPTION, BookUtils.ELECTRONIC, BookUtils.PAPER, BookUtils.NOTE, 0, listOf(AuthorUtils.newAuthor(1)), listOf(CategoryUtils.newCategory(1)))

        val result = getBookcaseValidator().validate(book, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(invalidIssueYearEvent))
        }

        for (author in book.authors) {
            authorValidator.verify(author)
        }
        for (category in book.categories) {
            categoryValidator.verify(category)
        }
        authorValidator.verifyNoMoreInteractions()
        categoryValidator.verifyNoMoreInteractions()
        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [BookValidatorImpl.validate] with [ValidationType.DEEP] with data with empty description.
     */
    @Test
    fun validate_Deep_EmptyDescription() {
        val book = Book(1, BookUtils.CZECH_NAME, BookUtils.ORIGINAL_NAME, listOf(Language.CZ), BookUtils.ISBN, BookUtils.ISSUE_YEAR, "", BookUtils.ELECTRONIC, BookUtils.PAPER, BookUtils.NOTE, 0, listOf(AuthorUtils.newAuthor(1)), listOf(CategoryUtils.newCategory(1)))

        val result = getBookcaseValidator().validate(book, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_DESCRIPTION_EMPTY", "Description mustn't be empty string.")))
        }

        for (author in book.authors) {
            authorValidator.verify(author)
        }
        for (category in book.categories) {
            categoryValidator.verify(category)
        }
        authorValidator.verifyNoMoreInteractions()
        categoryValidator.verifyNoMoreInteractions()
        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [BookValidatorImpl.validate] with [ValidationType.DEEP] with data with empty note.
     */
    @Test
    fun validate_Deep_EmptyNote() {
        val book = Book(1, BookUtils.CZECH_NAME, BookUtils.ORIGINAL_NAME, listOf(Language.CZ), BookUtils.ISBN, BookUtils.ISSUE_YEAR, BookUtils.DESCRIPTION, BookUtils.ELECTRONIC, BookUtils.PAPER, "", 0, listOf(AuthorUtils.newAuthor(1)), listOf(CategoryUtils.newCategory(1)))

        val result = getBookcaseValidator().validate(book, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "BOOK_NOTE_EMPTY", "Note mustn't be empty string.")))
        }

        for (author in book.authors) {
            authorValidator.verify(author)
        }
        for (category in book.categories) {
            categoryValidator.verify(category)
        }
        authorValidator.verifyNoMoreInteractions()
        categoryValidator.verifyNoMoreInteractions()
        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [BookValidatorImpl.validate] with [ValidationType.DEEP] with data with bad author.
     */
    @Test
    fun validate_Deep_BadAuthor() {
        val book = Book(1, BookUtils.CZECH_NAME, BookUtils.ORIGINAL_NAME, listOf(Language.CZ), BookUtils.ISBN, BookUtils.ISSUE_YEAR, BookUtils.DESCRIPTION, BookUtils.ELECTRONIC, BookUtils.PAPER, BookUtils.NOTE, 0, listOf(AuthorUtils.newAuthor(Int.MAX_VALUE)), listOf(CategoryUtils.newCategory(1)))
        authorValidator = ErrorValidatorStub("AUTHOR", errorEventMessage)

        val result = getBookcaseValidator().validate(book, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_INVALID", errorEventMessage)))
        }

        for (author in book.authors) {
            authorValidator.verify(author)
        }
        for (category in book.categories) {
            categoryValidator.verify(category)
        }
        authorValidator.verifyNoMoreInteractions()
        categoryValidator.verifyNoMoreInteractions()
        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [BookValidatorImpl.validate] with [ValidationType.DEEP] with data with bad category.
     */
    @Test
    fun validate_Deep_BadCategory() {
        val book = Book(1, BookUtils.CZECH_NAME, BookUtils.ORIGINAL_NAME, listOf(Language.CZ), BookUtils.ISBN, BookUtils.ISSUE_YEAR, BookUtils.DESCRIPTION, BookUtils.ELECTRONIC, BookUtils.PAPER, BookUtils.NOTE, 0, listOf(AuthorUtils.newAuthor(1)), listOf(CategoryUtils.newCategory(Int.MAX_VALUE)))
        categoryValidator = ErrorValidatorStub("CATEGORY", errorEventMessage)

        val result = getBookcaseValidator().validate(book, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_INVALID", errorEventMessage)))
        }

        for (author in book.authors) {
            authorValidator.verify(author)
        }
        for (category in book.categories) {
            categoryValidator.verify(category)
        }
        authorValidator.verifyNoMoreInteractions()
        categoryValidator.verifyNoMoreInteractions()
        bookcaseService.verifyZeroInteractions()
    }

    override fun getBookcaseValidator(): BookcaseValidator<Book> {
        return BookValidatorImpl(bookcaseService, authorValidator, categoryValidator)
    }

    override fun getValidatingData(id: Int?): Book {
        return BookUtils.newBook(id)
    }

    override fun getRepositoryData(validatingData: Book): cz.vhromada.book.domain.Book {
        return BookUtils.newBookDomain(validatingData.id!!)
    }

    override fun getItem1(): cz.vhromada.book.domain.Book {
        return BookUtils.newBookDomain(1)
    }

    override fun getItem2(): cz.vhromada.book.domain.Book {
        return BookUtils.newBookDomain(2)
    }

}

package cz.vhromada.book.validator.impl

import cz.vhromada.book.entity.Author
import cz.vhromada.book.utils.AuthorUtils
import cz.vhromada.book.validator.BookcaseValidator
import cz.vhromada.book.validator.common.ValidationType
import cz.vhromada.result.Event
import cz.vhromada.result.Severity
import cz.vhromada.result.Status
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

/**
 * A class represents test for class [AuthorValidatorImpl].
 *
 * @author Vladimir Hromada
 */
class AuthorValidatorImplTest : AbstractValidatorTest<Author, cz.vhromada.book.domain.Author>() {

    override val name = "Author"

    /**
     * Test method for [AuthorValidatorImpl.validate] with [ValidationType.DEEP] with data with empty first name.
     */
    @Test
    fun validate_Deep_EmptyFirstName() {
        val author = getValidatingData(1).copy(firstName = "")

        val result = getBookcaseValidator().validate(author, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")))
        }

        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [AuthorValidatorImpl.validate] with [ValidationType.DEEP] with data with empty middle name.
     */
    @Test
    fun validate_Deep_EmptyMiddleName() {
        val author = getValidatingData(1).copy(middleName = "")

        val result = getBookcaseValidator().validate(author, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string.")))
        }

        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [AuthorValidatorImpl.validate] with [ValidationType.DEEP] with data with empty last name.
     */
    @Test
    fun validate_Deep_EmptyLastName() {
        val author = getValidatingData(1).copy(lastName = "")

        val result = getBookcaseValidator().validate(author, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")))
        }

        bookcaseService.verifyZeroInteractions()
    }

    override fun getBookcaseValidator(): BookcaseValidator<Author> {
        return AuthorValidatorImpl(bookcaseService)
    }

    override fun getValidatingData(id: Int?): Author {
        return AuthorUtils.newAuthor(id)
    }

    override fun getRepositoryData(validatingData: Author): cz.vhromada.book.domain.Author {
        return AuthorUtils.newAuthorDomain(validatingData.id!!)
    }

    override fun getItem1(): cz.vhromada.book.domain.Author {
        return AuthorUtils.newAuthorDomain(1)
    }

    override fun getItem2(): cz.vhromada.book.domain.Author {
        return AuthorUtils.newAuthorDomain(2)
    }

}

package cz.vhromada.bookcase.validator

import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import cz.vhromada.bookcase.entity.Author
import cz.vhromada.bookcase.utils.AuthorUtils
import cz.vhromada.common.result.Event
import cz.vhromada.common.result.Severity
import cz.vhromada.common.result.Status
import cz.vhromada.common.test.validator.MovableValidatorTest
import cz.vhromada.common.validator.MovableValidator
import cz.vhromada.common.validator.ValidationType
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

/**
 * A class represents test for class [AuthorValidator].
 *
 * @author Vladimir Hromada
 */
class AuthorValidatorTest : MovableValidatorTest<Author, cz.vhromada.bookcase.domain.Author>() {

    /**
     * Test method for [AuthorValidator.validate] with [ValidationType.DEEP] with data with null first name.
     */
    @Test
    fun validateDeepNullFirstName() {
        val author = getValidatingData(1)
                .copy(firstName = null)

        val result = getValidator().validate(author, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null.")))
        }

        verifyZeroInteractions(service)
    }

    /**
     * Test method for [AuthorValidator.validate] with [ValidationType.DEEP] with data with empty first name.
     */
    @Test
    fun validateDeepEmptyFirstName() {
        val author = getValidatingData(1)
                .copy(firstName = "")

        val result = getValidator().validate(author, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")))
        }

        verifyZeroInteractions(service)
    }

    /**
     * Test method for [AuthorValidator.validate] with [ValidationType.DEEP] with data with null middle name.
     */
    @Test
    fun validateDeepNullMiddleName() {
        val author = getValidatingData(1)
                .copy(middleName = null)

        val result = getValidator().validate(author, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_NULL", "Middle name mustn't be null.")))
        }

        verifyZeroInteractions(service)
    }

    /**
     * Test method for [AuthorValidator.validate] with [ValidationType.DEEP] with data with null last name.
     */
    @Test
    fun validateDeepNullLastName() {
        val author = getValidatingData(1)
                .copy(lastName = null)

        val result = getValidator().validate(author, ValidationType.DEEP)
        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null.")))
        }

        verifyZeroInteractions(service)
    }

    /**
     * Test method for [AuthorValidator.validate] with [ValidationType.DEEP] with data with empty last name.
     */
    @Test
    fun validateDeepEmptyLastName() {
        val author = getValidatingData(1)
                .copy(lastName = "")

        val result = getValidator().validate(author, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")))
        }

        verifyZeroInteractions(service)
    }

    override fun getValidator(): MovableValidator<Author> {
        return AuthorValidator(service)
    }

    override fun getValidatingData(id: Int?): Author {
        return AuthorUtils.newAuthor(id)
    }

    override fun getValidatingData(id: Int?, position: Int?): Author {
        return AuthorUtils.newAuthor(id)
                .copy(position = position)
    }

    override fun getRepositoryData(validatingData: Author): cz.vhromada.bookcase.domain.Author {
        return AuthorUtils.newAuthorDomain(validatingData.id)
    }

    override fun getItem1(): cz.vhromada.bookcase.domain.Author {
        return AuthorUtils.newAuthorDomain(1)
    }

    override fun getItem2(): cz.vhromada.bookcase.domain.Author {
        return AuthorUtils.newAuthorDomain(2)
    }

    override fun getName(): String {
        return "Author"
    }

}

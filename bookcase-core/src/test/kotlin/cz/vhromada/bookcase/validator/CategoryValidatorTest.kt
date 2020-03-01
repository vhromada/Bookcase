package cz.vhromada.bookcase.validator

import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import cz.vhromada.bookcase.entity.Category
import cz.vhromada.bookcase.utils.CategoryUtils
import cz.vhromada.common.result.Event
import cz.vhromada.common.result.Severity
import cz.vhromada.common.result.Status
import cz.vhromada.common.test.validator.MovableValidatorTest
import cz.vhromada.common.validator.MovableValidator
import cz.vhromada.common.validator.ValidationType
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

/**
 * A class represents test for class [CategoryValidator].
 *
 * @author Vladimir Hromada
 */
class CategoryValidatorTest : MovableValidatorTest<Category, cz.vhromada.bookcase.domain.Category>() {

    /**
     * Test method for [CategoryValidator.validate] with [ValidationType.DEEP] with data with null name.
     */
    @Test
    fun validateDeepNullName() {
        val category = getValidatingData(1)
                .copy(name = null)

        val result = getValidator().validate(category, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_NAME_NULL", "Name mustn't be null.")))
        }

        verifyZeroInteractions(service)
    }

    /**
     * Test method for [CategoryValidator.validate] with [ValidationType.DEEP] with data with empty name.
     */
    @Test
    fun validateDeepEmptyName() {
        val category = getValidatingData(1)
                .copy(name = "")

        val result = getValidator().validate(category, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")))
        }

        verifyZeroInteractions(service)
    }

    override fun getValidator(): MovableValidator<Category> {
        return CategoryValidator(service)
    }

    override fun getValidatingData(id: Int?): Category {
        return CategoryUtils.newCategory(id)
    }

    override fun getValidatingData(id: Int?, position: Int?): Category {
        return CategoryUtils.newCategory(id)
                .copy(position = position)
    }

    override fun getRepositoryData(validatingData: Category): cz.vhromada.bookcase.domain.Category {
        return CategoryUtils.newCategoryDomain(validatingData.id)
    }

    override fun getItem1(): cz.vhromada.bookcase.domain.Category {
        return CategoryUtils.newCategoryDomain(1)
    }

    override fun getItem2(): cz.vhromada.bookcase.domain.Category {
        return CategoryUtils.newCategoryDomain(2)
    }

    override fun getName(): String {
        return "Category"
    }

}

package cz.vhromada.book.validator.impl

import cz.vhromada.book.entity.Category
import cz.vhromada.book.utils.CategoryUtils
import cz.vhromada.book.validator.BookcaseValidator
import cz.vhromada.book.validator.common.ValidationType
import cz.vhromada.result.Event
import cz.vhromada.result.Severity
import cz.vhromada.result.Status
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

/**
 * A class represents test for class [CategoryValidatorImpl].
 *
 * @author Vladimir Hromada
 */
class CategoryValidatorImplTest : AbstractValidatorTest<Category, cz.vhromada.book.domain.Category>() {

    override val name = "Category"

    /**
     * Test method for [CategoryValidatorImpl.validate] with [ValidationType.DEEP] with data with empty name.
     */
    @Test
    fun validate_Deep_EmptyName() {
        val category = getValidatingData(1).copy(name = "")

        val result = getBookcaseValidator().validate(category, ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")))
        }

        bookcaseService.verifyZeroInteractions()
    }

    override fun getBookcaseValidator(): BookcaseValidator<Category> {
        return CategoryValidatorImpl(bookcaseService)
    }

    override fun getValidatingData(id: Int?): Category {
        return CategoryUtils.newCategory(id)
    }

    override fun getRepositoryData(validatingData: Category): cz.vhromada.book.domain.Category {
        return CategoryUtils.newCategoryDomain(validatingData.id!!)
    }

    override fun getItem1(): cz.vhromada.book.domain.Category {
        return CategoryUtils.newCategoryDomain(1)
    }

    override fun getItem2(): cz.vhromada.book.domain.Category {
        return CategoryUtils.newCategoryDomain(2)
    }

}

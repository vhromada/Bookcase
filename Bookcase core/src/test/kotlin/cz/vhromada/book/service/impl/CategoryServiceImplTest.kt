package cz.vhromada.book.service.impl

import cz.vhromada.book.domain.Category
import cz.vhromada.book.repository.CategoryRepository
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.utils.CategoryUtils
import org.assertj.core.api.Assertions
import org.mockito.Mock
import org.springframework.data.jpa.repository.JpaRepository

/**
 * A class represents test for class [CategoryServiceImpl].
 *
 * @author Vladimir Hromada
 */
class CategoryServiceImplTest : AbstractServiceTest<Category>() {

    override val cacheKey: String = "categories"

    override val itemClass: Class<Category> = Category::class.java

    /**
     * Instance of [CategoryRepository]
     */
    @Mock
    private val categoryRepository: CategoryRepository? = null

    override fun getRepository(): JpaRepository<Category, Int> {
        Assertions.assertThat(categoryRepository).isNotNull

        return categoryRepository!!
    }

    override fun getBookcaseService(): BookcaseService<Category> {
        return CategoryServiceImpl(categoryRepository!!, cache!!)
    }

    override fun getItem1(): Category {
        return CategoryUtils.newCategoryDomain(1)
    }

    override fun getItem2(): Category {
        return CategoryUtils.newCategoryDomain(2)
    }

    override fun getAddItem(): Category {
        return CategoryUtils.newCategoryDomain(null)
    }

    override fun getCopyItem(): Category {
        val category = CategoryUtils.newCategoryDomain(null)
        category.position = 0

        return category
    }

    override fun assertDataDeepEquals(expected: Category, actual: Category) {
        CategoryUtils.assertCategoryDeepEquals(expected, actual)
    }

}

package cz.vhromada.bookcase.service

import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import cz.vhromada.bookcase.domain.Category
import cz.vhromada.bookcase.repository.CategoryRepository
import cz.vhromada.bookcase.utils.CategoryUtils
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.test.service.MovableServiceTest
import org.mockito.Mock
import org.springframework.data.jpa.repository.JpaRepository

/**
 * A class represents test for class [CategoryService].
 *
 * @author Vladimir Hromada
 */
class CategoryServiceTest : MovableServiceTest<Category>() {

    /**
     * Instance of [CategoryRepository]
     */
    @Mock
    private lateinit var categoryRepository: CategoryRepository

    override fun getRepository(): JpaRepository<Category, Int> {
        return categoryRepository
    }

    override fun getService(): MovableService<Category> {
        return CategoryService(categoryRepository, cache)
    }

    override fun getCacheKey(): String {
        return "categories"
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
        return CategoryUtils.newCategoryDomain(null)
                .copy(position = 0)
    }

    override fun anyItem(): Category {
        return any()
    }

    override fun argumentCaptorItem(): KArgumentCaptor<Category> {
        return argumentCaptor()
    }

    override fun assertDataDeepEquals(expected: Category, actual: Category) {
        CategoryUtils.assertCategoryDeepEquals(expected, actual)
    }

}

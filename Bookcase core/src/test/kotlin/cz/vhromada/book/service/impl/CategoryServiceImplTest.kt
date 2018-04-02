package cz.vhromada.book.service.impl

import cz.vhromada.book.domain.Category
import cz.vhromada.book.repository.CategoryRepository
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.stub.RepositoryStub
import cz.vhromada.book.stub.impl.repository.CategoryRepositoryStub
import cz.vhromada.book.utils.CategoryUtils

/**
 * A class represents test for class [CategoryServiceImpl].
 *
 * @author Vladimir Hromada
 */
class CategoryServiceImplTest : AbstractServiceTest<Category>() {

    override val cacheKey = "categories"

    override val itemClass = Category::class.java

    /**
     * Instance of [CategoryRepository]
     */
    private val categoryRepository = CategoryRepositoryStub()

    override fun getRepository(): RepositoryStub<Category> {
        return categoryRepository
    }

    override fun getBookcaseService(): BookcaseService<Category> {
        return CategoryServiceImpl(categoryRepository, cache)
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

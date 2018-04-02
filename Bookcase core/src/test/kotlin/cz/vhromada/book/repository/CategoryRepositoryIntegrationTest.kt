package cz.vhromada.book.repository

import cz.vhromada.book.CoreTestConfiguration
import cz.vhromada.book.utils.CategoryUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

/**
 * A class represents integration test for class [CategoryRepository].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CoreTestConfiguration::class])
@Transactional
@Rollback
class CategoryRepositoryIntegrationTest {

    /**
     * Instance of [EntityManager]
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private val entityManager: EntityManager? = null

    /**
     * Instance of [CategoryRepository]
     */
    @Autowired
    private val categoryRepository: CategoryRepository? = null

    /**
     * Check autowired fields
     */
    @BeforeEach
    fun setUp() {
        assertSoftly { softly ->
            softly.assertThat(categoryRepository).isNotNull
            softly.assertThat(entityManager).isNotNull
        }
    }

    /**
     * Test method for get categories.
     */
    @Test
    fun getCategories() {
        val categories = categoryRepository!!.findAll()

        CategoryUtils.assertCategoriesDeepEquals(CategoryUtils.getCategories(), categories)

        assertThat(CategoryUtils.getCategoriesCount(entityManager!!)).isEqualTo(CategoryUtils.CATEGORIES_COUNT)
    }

    /**
     * Test method for get category.
     */
    @Test
    fun getCategory() {
        for (i in 1..CategoryUtils.CATEGORIES_COUNT) {
            val category = categoryRepository!!.findById(i).orElse(null)

            CategoryUtils.assertCategoryDeepEquals(CategoryUtils.getCategory(i), category)
        }

        assertThat(categoryRepository!!.findById(Int.MAX_VALUE).isPresent).isFalse()

        assertThat(CategoryUtils.getCategoriesCount(entityManager!!)).isEqualTo(CategoryUtils.CATEGORIES_COUNT)
    }

    /**
     * Test method for add category.
     */
    @Test
    fun add() {
        val category = CategoryUtils.newCategoryDomain(null)

        categoryRepository!!.save(category)

        assertThat(category.id).isEqualTo(CategoryUtils.CATEGORIES_COUNT + 1)

        val addedCategory = CategoryUtils.getCategory(entityManager!!, CategoryUtils.CATEGORIES_COUNT + 1)
        val expectedAddCategory = CategoryUtils.newCategoryDomain(CategoryUtils.CATEGORIES_COUNT + 1, 0)
        CategoryUtils.assertCategoryDeepEquals(expectedAddCategory, addedCategory)

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT + 1)
    }

    /**
     * Test method for update category.
     */
    @Test
    fun update() {
        val category = CategoryUtils.newCategoryDomain(1)

        categoryRepository!!.save(category)

        val updatedCategory = CategoryUtils.getCategory(entityManager!!, 1)
        val expectedUpdatedCategory = CategoryUtils.newCategoryDomain(1)
        CategoryUtils.assertCategoryDeepEquals(expectedUpdatedCategory, updatedCategory)

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT)
    }

    /**
     * Test method for remove category.
     */
    @Test
    fun remove() {
        entityManager!!.createNativeQuery("DELETE FROM book_categories").executeUpdate()

        categoryRepository!!.delete(CategoryUtils.getCategory(entityManager, 1)!!)

        assertThat(CategoryUtils.getCategory(entityManager, 1)).isNull()

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT - 1)
    }

    /**
     * Test method for remove all categories.
     */
    @Test
    fun removeAll() {
        entityManager!!.createNativeQuery("DELETE FROM book_categories").executeUpdate()

        categoryRepository!!.deleteAll()

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(0)
    }

}

package cz.vhromada.bookcase.repository

import cz.vhromada.bookcase.CoreTestConfiguration
import cz.vhromada.bookcase.utils.CategoryUtils
import cz.vhromada.bookcase.utils.updated
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.test.annotation.DirtiesContext
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
    private lateinit var entityManager: EntityManager

    /**
     * Instance of [CategoryRepository]
     */
    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    /**
     * Test method for get categories.
     */
    @Test
    fun getCategories() {
        val categories = categoryRepository.findAll()

        CategoryUtils.assertCategoriesDeepEquals(CategoryUtils.getCategories(), categories)

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT)
    }

    /**
     * Test method for get category.
     */
    @Test
    fun getCategory() {
        for (i in 1 until CategoryUtils.CATEGORIES_COUNT) {
            val category = categoryRepository.findById(i).orElse(null)

            CategoryUtils.assertCategoryDeepEquals(CategoryUtils.getCategoryDomain(i), category)
        }

        assertThat(categoryRepository.findById(Int.MAX_VALUE).isPresent).isFalse()

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT)
    }

    /**
     * Test method for add category.
     */
    @Test
    fun add() {
        val category = CategoryUtils.newCategoryDomain(null)
                .copy(position = CategoryUtils.CATEGORIES_COUNT)

        categoryRepository.save(category)

        assertThat(category.id).isEqualTo(CategoryUtils.CATEGORIES_COUNT + 1)

        val addedCategory = CategoryUtils.getCategory(entityManager, CategoryUtils.CATEGORIES_COUNT + 1)
        val expectedAddCategory = CategoryUtils.newCategoryDomain(null)
                .copy(id = CategoryUtils.CATEGORIES_COUNT + 1, position = CategoryUtils.CATEGORIES_COUNT)
        CategoryUtils.assertCategoryDeepEquals(expectedAddCategory, addedCategory)

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT + 1)
    }

    /**
     * Test method for update category.
     */
    @Test
    fun update() {
        val category = CategoryUtils.updateCategory(entityManager, 1)

        categoryRepository.save(category)

        val updatedCategory = CategoryUtils.getCategory(entityManager, 1)
        val expectedUpdatedCategory = CategoryUtils.getCategoryDomain(1)
                .updated()
                .copy(position = CategoryUtils.POSITION)
        CategoryUtils.assertCategoryDeepEquals(expectedUpdatedCategory, updatedCategory)

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT)
    }

    /**
     * Test method for remove category.
     */
    @Test
    @DirtiesContext
    fun remove() {
        val category = CategoryUtils.newCategoryDomain(null)
                .copy(position = CategoryUtils.CATEGORIES_COUNT)
        entityManager.persist(category)
        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT + 1)

        categoryRepository.delete(category)

        assertThat(CategoryUtils.getCategory(entityManager, category.id!!)).isNull()

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT)
    }

    /**
     * Test method for remove all categories.
     */
    @Test
    fun removeAll() {
        entityManager.createNativeQuery("DELETE FROM book_categories").executeUpdate()

        categoryRepository.deleteAll()

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(0)
    }

}

package cz.vhromada.bookcase.facade.impl

import cz.vhromada.bookcase.CoreTestConfiguration
import cz.vhromada.bookcase.entity.Category
import cz.vhromada.bookcase.facade.CategoryFacade
import cz.vhromada.bookcase.utils.BookUtils
import cz.vhromada.bookcase.utils.CategoryUtils
import cz.vhromada.common.facade.MovableParentFacade
import cz.vhromada.common.result.Event
import cz.vhromada.common.result.Severity
import cz.vhromada.common.result.Status
import cz.vhromada.common.test.facade.MovableParentFacadeIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import javax.persistence.EntityManager

/**
 * A class represents integration test for class [CategoryFacadeImpl].
 *
 * @author Vladimir Hromada
 */
@ContextConfiguration(classes = [CoreTestConfiguration::class])
class CategoryFacadeImplIntegrationTest : MovableParentFacadeIntegrationTest<Category, cz.vhromada.bookcase.domain.Category>() {

    /**
     * Instance of [EntityManager]
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private lateinit var entityManager: EntityManager

    /**
     * Instance of [PlatformTransactionManager]
     */
    @Autowired
    private lateinit var transactionManager: PlatformTransactionManager

    /**
     * Instance of [CategoryFacade]
     */
    @Autowired
    private lateinit var categoryFacade: CategoryFacade

    /**
     * Test method for [CategoryFacade.add] with category with null name.
     */
    @Test
    fun addNullName() {
        val category = newData(null)
                .copy(name = null)

        val result = categoryFacade.add(category)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_NAME_NULL", "Name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [CategoryFacade.add] with category with empty string as name.
     */
    @Test
    fun addEmptyName() {
        val category = newData(null)
                .copy(name = "")

        val result = categoryFacade.add(category)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [CategoryFacade.update] with category with null name.
     */
    @Test
    fun updateNullName() {
        val category = newData(1)
                .copy(name = null)

        val result = categoryFacade.update(category)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_NAME_NULL", "Name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [CategoryFacade.update] with category with empty string as name.
     */
    @Test
    fun updateEmptyName() {
        val category = newData(1)
                .copy(name = "")

        val result = categoryFacade.update(category)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    override fun getFacade(): MovableParentFacade<Category> {
        return categoryFacade
    }

    override fun getDefaultDataCount(): Int {
        return CategoryUtils.CATEGORIES_COUNT
    }

    override fun getRepositoryDataCount(): Int {
        return CategoryUtils.getCategoriesCount(entityManager)
    }

    override fun getDataList(): List<cz.vhromada.bookcase.domain.Category> {
        return CategoryUtils.getCategories()
    }

    override fun getDomainData(index: Int): cz.vhromada.bookcase.domain.Category {
        return CategoryUtils.getCategoryDomain(index)
    }

    override fun newData(id: Int?): Category {
        return CategoryUtils.newCategory(id)
    }

    override fun newDomainData(id: Int): cz.vhromada.bookcase.domain.Category {
        return CategoryUtils.newCategoryDomain(id)
    }

    override fun getRepositoryData(id: Int): cz.vhromada.bookcase.domain.Category? {
        return CategoryUtils.getCategory(entityManager, id)
    }

    override fun getName(): String {
        return "Category"
    }

    override fun clearReferencedData() {
        val transactionStatus = transactionManager.getTransaction(DefaultTransactionDefinition())
        entityManager.createNativeQuery("DELETE FROM book_categories").executeUpdate()
        transactionManager.commit(transactionStatus)
    }

    override fun assertDataListDeepEquals(expected: List<Category>, actual: List<cz.vhromada.bookcase.domain.Category>) {
        CategoryUtils.assertCategoryListDeepEquals(expected, actual)
    }

    override fun assertDataDeepEquals(expected: Category, actual: cz.vhromada.bookcase.domain.Category) {
        CategoryUtils.assertCategoryDeepEquals(expected, actual)
    }

    override fun assertDataDomainDeepEquals(expected: cz.vhromada.bookcase.domain.Category, actual: cz.vhromada.bookcase.domain.Category) {
        CategoryUtils.assertCategoryDeepEquals(expected, actual)
    }

    override fun assertDefaultRepositoryData() {
        super.assertDefaultRepositoryData()

        assertReferences()
    }

    override fun assertNewRepositoryData() {
        super.assertNewRepositoryData()

        assertReferences()
    }

    override fun assertAddRepositoryData() {
        super.assertAddRepositoryData()

        assertReferences()
    }

    override fun assertUpdateRepositoryData() {
        super.assertUpdateRepositoryData()

        assertReferences()
    }

    override fun assertRemoveRepositoryData() {
        super.assertRemoveRepositoryData()

        assertReferences()
    }

    override fun assertDuplicateRepositoryData() {
        super.assertDuplicateRepositoryData()

        assertReferences()
    }

    /**
     * Asserts references.
     */
    private fun assertReferences() {
        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT)
    }

}

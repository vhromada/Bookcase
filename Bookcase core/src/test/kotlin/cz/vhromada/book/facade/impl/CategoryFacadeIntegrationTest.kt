package cz.vhromada.book.facade.impl

import cz.vhromada.book.entity.Category
import cz.vhromada.book.facade.BookcaseFacade
import cz.vhromada.book.facade.CategoryFacade
import cz.vhromada.book.utils.CategoryUtils
import cz.vhromada.result.Event
import cz.vhromada.result.Severity
import cz.vhromada.result.Status
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import javax.persistence.EntityManager

/**
 * A class represents integration test for facade for categories.
 *
 * @author Vladimir Hromada
 */
class CategoryFacadeIntegrationTest : AbstractFacadeIntegrationTest<Category, cz.vhromada.book.domain.Category>() {

    override val defaultDataCount = CategoryUtils.CATEGORIES_COUNT

    override val name = "Category"

    /**
     * Instance of [EntityManager]
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private val entityManager: EntityManager? = null

    /**
     * Instance of [PlatformTransactionManager]
     */
    @Autowired
    private val transactionManager: PlatformTransactionManager? = null

    /**
     * Instance of [CategoryFacade]
     */
    @Autowired
    private val categoryFacade: CategoryFacade? = null

    /**
     * Checks autowired fields.
     */
    @BeforeEach
    fun setUp() {
        assertSoftly { softly ->
            softly.assertThat(categoryFacade).isNotNull
            softly.assertThat(entityManager).isNotNull
            softly.assertThat(transactionManager).isNotNull
        }
    }

    /**
     * Test method for [CategoryFacade.add] with category with empty name.
     */
    @Test
    fun add_EmptyName() {
        val category = newData(null).copy(name = "")

        val result = categoryFacade!!.add(category)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [CategoryFacade.update] with category with empty name.
     */
    @Test
    fun update_EmptyName() {
        val category = newData(1).copy(name = "")

        val result = categoryFacade!!.update(category)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    override fun getBookcaseFacade(): BookcaseFacade<Category> {
        return categoryFacade!!
    }

    override fun getRepositoryDataCount(): Int {
        return CategoryUtils.getCategoriesCount(entityManager!!)
    }

    override fun getDataList(): List<cz.vhromada.book.domain.Category> {
        return CategoryUtils.getCategories()
    }

    override fun getDomainData(index: Int): cz.vhromada.book.domain.Category {
        return CategoryUtils.getCategory(index)
    }

    override fun newData(id: Int?): Category {
        return CategoryUtils.newCategory(id)
    }

    override fun newDomainData(id: Int): cz.vhromada.book.domain.Category {
        return CategoryUtils.newCategoryDomain(id)
    }

    override fun getRepositoryData(id: Int): cz.vhromada.book.domain.Category? {
        return CategoryUtils.getCategory(entityManager!!, id)
    }

    override fun clearReferencedData() {
        val transactionStatus = transactionManager!!.getTransaction(DefaultTransactionDefinition())
        entityManager!!.createNativeQuery("DELETE FROM book_categories").executeUpdate()
        transactionManager.commit(transactionStatus)
    }

    override fun assertDataListDeepEquals(expected: List<Category>, actual: List<cz.vhromada.book.domain.Category>) {
        CategoryUtils.assertCategoryListDeepEquals(actual, expected)
    }

    override fun assertDataDeepEquals(expected: Category, actual: cz.vhromada.book.domain.Category) {
        CategoryUtils.assertCategoryDeepEquals(actual, expected)
    }

    override fun assertDataDomainDeepEquals(expected: cz.vhromada.book.domain.Category, actual: cz.vhromada.book.domain.Category) {
        CategoryUtils.assertCategoryDeepEquals(actual, expected)
    }

}

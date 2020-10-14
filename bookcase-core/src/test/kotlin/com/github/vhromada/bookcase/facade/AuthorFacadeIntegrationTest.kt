package com.github.vhromada.bookcase.facade

import com.github.vhromada.bookcase.BookcaseTestConfiguration
import com.github.vhromada.bookcase.entity.Author
import com.github.vhromada.bookcase.utils.AuthorUtils
import com.github.vhromada.bookcase.utils.BookUtils
import com.github.vhromada.common.facade.MovableParentFacade
import com.github.vhromada.common.result.Event
import com.github.vhromada.common.result.Severity
import com.github.vhromada.common.result.Status
import com.github.vhromada.common.test.facade.MovableParentFacadeIntegrationTest
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
 * A class represents integration test for class [AuthorFacade].
 *
 * @author Vladimir Hromada
 */
@ContextConfiguration(classes = [BookcaseTestConfiguration::class])
class AuthorFacadeIntegrationTest : MovableParentFacadeIntegrationTest<Author, com.github.vhromada.bookcase.domain.Author>() {

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
     * Instance of [AuthorFacade]
     */
    @Autowired
    private lateinit var authorFacade: AuthorFacade

    /**
     * Test method for [AuthorFacade.add] with author with null first name.
     */
    @Test
    fun addNullFirstName() {
        val author = newData(null)
                .copy(firstName = null)

        val result = authorFacade.add(author)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.add] with author with empty string as first name.
     */
    @Test
    fun addEmptyFirstName() {
        val author = newData(null)
                .copy(firstName = "")

        val result = authorFacade.add(author)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.add] with author with null middle name.
     */
    @Test
    fun addNullMiddleName() {
        val author = newData(null)
                .copy(middleName = null)

        val result = authorFacade.add(author)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_NULL", "Middle name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.add] with author with null last name.
     */
    @Test
    fun addNullLastName() {
        val author = newData(null)
                .copy(lastName = null)

        val result = authorFacade.add(author)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.add] with author with empty string as last name.
     */
    @Test
    fun addEmptyLastName() {
        val author = newData(null)
                .copy(lastName = "")

        val result = authorFacade.add(author)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.update] with author with null first name.
     */
    @Test
    fun updateNullFirstName() {
        val author = newData(1)
                .copy(firstName = null)

        val result = authorFacade.update(author)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.update] with author with empty string as first name.
     */
    @Test
    fun updateEmptyFirstName() {
        val author = newData(1)
                .copy(firstName = "")

        val result = authorFacade.update(author)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.update] with author with null middle name.
     */
    @Test
    fun updateNullMiddleName() {
        val author = newData(1)
                .copy(middleName = null)

        val result = authorFacade.update(author)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_NULL", "Middle name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.update] with author with null last name.
     */
    @Test
    fun updateNullLastName() {
        val author = newData(1)
                .copy(lastName = null)

        val result = authorFacade.update(author)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.update] with author with empty string as last name.
     */
    @Test
    fun updateEmptyLastName() {
        val author = newData(1)
                .copy(lastName = "")

        val result = authorFacade.update(author)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    override fun getFacade(): MovableParentFacade<Author> {
        return authorFacade
    }

    override fun getDefaultDataCount(): Int {
        return AuthorUtils.AUTHORS_COUNT
    }

    override fun getRepositoryDataCount(): Int {
        return AuthorUtils.getAuthorsCount(entityManager)
    }

    override fun getDataList(): List<com.github.vhromada.bookcase.domain.Author> {
        return AuthorUtils.getAuthors()
    }

    override fun getDomainData(index: Int): com.github.vhromada.bookcase.domain.Author {
        return AuthorUtils.getAuthorDomain(index)
    }

    override fun newData(id: Int?): Author {
        return AuthorUtils.newAuthor(id)
    }

    override fun newDomainData(id: Int): com.github.vhromada.bookcase.domain.Author {
        return AuthorUtils.newAuthorDomain(id)
    }

    override fun getRepositoryData(id: Int): com.github.vhromada.bookcase.domain.Author? {
        return AuthorUtils.getAuthor(entityManager, id)
    }

    override fun getName(): String {
        return "Author"
    }

    override fun clearReferencedData() {
        val transactionStatus = transactionManager.getTransaction(DefaultTransactionDefinition())
        entityManager.createNativeQuery("DELETE FROM book_authors").executeUpdate()
        transactionManager.commit(transactionStatus)
    }

    override fun assertDataListDeepEquals(expected: List<Author>, actual: List<com.github.vhromada.bookcase.domain.Author>) {
        AuthorUtils.assertAuthorListDeepEquals(expected, actual)
    }

    override fun assertDataDeepEquals(expected: Author, actual: com.github.vhromada.bookcase.domain.Author) {
        AuthorUtils.assertAuthorDeepEquals(expected, actual)
    }

    override fun assertDataDomainDeepEquals(expected: com.github.vhromada.bookcase.domain.Author, actual: com.github.vhromada.bookcase.domain.Author) {
        AuthorUtils.assertAuthorDeepEquals(expected, actual)
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

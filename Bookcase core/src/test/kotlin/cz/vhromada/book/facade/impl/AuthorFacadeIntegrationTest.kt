package cz.vhromada.book.facade.impl

import cz.vhromada.book.entity.Author
import cz.vhromada.book.facade.AuthorFacade
import cz.vhromada.book.facade.BookcaseFacade
import cz.vhromada.book.utils.AuthorUtils
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
 * A class represents integration test for facade for authors.
 *
 * @author Vladimir Hromada
 */
class AuthorFacadeIntegrationTest : AbstractFacadeIntegrationTest<Author, cz.vhromada.book.domain.Author>() {

    override val defaultDataCount = AuthorUtils.AUTHORS_COUNT

    override val name = "Author"

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
     * Instance of [AuthorFacade]
     */
    @Autowired
    private val authorFacade: AuthorFacade? = null

    /**
     * Checks autowired fields.
     */
    @BeforeEach
    fun setUp() {
        assertSoftly { softly ->
            softly.assertThat(authorFacade).isNotNull
            softly.assertThat(entityManager).isNotNull
            softly.assertThat(transactionManager).isNotNull
        }
    }

    /**
     * Test method for [AuthorFacade.add] with author with empty first name.
     */
    @Test
    fun add_EmptyFirstName() {
        val author = newData(null).copy(firstName = "")

        val result = authorFacade!!.add(author)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.add] with author with empty middle name.
     */
    @Test
    fun add_EmptyMiddleName() {
        val author = newData(null).copy(middleName = "")

        val result = authorFacade!!.add(author)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.add] with author with empty last name.
     */
    @Test
    fun add_EmptyLastName() {
        val author = newData(null).copy(lastName = "")

        val result = authorFacade!!.add(author)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.update] with author with empty first name.
     */
    @Test
    fun update_EmptyFirstName() {
        val author = newData(1).copy(firstName = "")

        val result = authorFacade!!.update(author)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.update] with author with empty middle name.
     */
    @Test
    fun update_EmptyMiddleName() {
        val author = newData(1).copy(middleName = "")

        val result = authorFacade!!.update(author)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [AuthorFacade.update] with author with empty last name.
     */
    @Test
    fun update_EmptyLastName() {
        val author = newData(1).copy(lastName = "")

        val result = authorFacade!!.update(author)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")))
        }

        assertDefaultRepositoryData()
    }

    override fun getBookcaseFacade(): BookcaseFacade<Author> {
        return authorFacade!!
    }

    override fun getRepositoryDataCount(): Int {
        return AuthorUtils.getAuthorsCount(entityManager!!)
    }

    override fun getDataList(): List<cz.vhromada.book.domain.Author> {
        return AuthorUtils.getAuthors()
    }

    override fun getDomainData(index: Int): cz.vhromada.book.domain.Author {
        return AuthorUtils.getAuthor(index)
    }

    override fun newData(id: Int?): Author {
        return AuthorUtils.newAuthor(id)
    }

    override fun newDomainData(id: Int): cz.vhromada.book.domain.Author {
        return AuthorUtils.newAuthorDomain(id)
    }

    override fun getRepositoryData(id: Int): cz.vhromada.book.domain.Author? {
        return AuthorUtils.getAuthor(entityManager!!, id)
    }

    override fun clearReferencedData() {
        val transactionStatus = transactionManager!!.getTransaction(DefaultTransactionDefinition())
        entityManager!!.createNativeQuery("DELETE FROM book_authors").executeUpdate()
        transactionManager.commit(transactionStatus)
    }

    override fun assertDataListDeepEquals(expected: List<Author>, actual: List<cz.vhromada.book.domain.Author>) {
        AuthorUtils.assertAuthorListDeepEquals(actual, expected)
    }

    override fun assertDataDeepEquals(expected: Author, actual: cz.vhromada.book.domain.Author) {
        AuthorUtils.assertAuthorDeepEquals(actual, expected)
    }

    override fun assertDataDomainDeepEquals(expected: cz.vhromada.book.domain.Author, actual: cz.vhromada.book.domain.Author) {
        AuthorUtils.assertAuthorDeepEquals(actual, expected)
    }

}

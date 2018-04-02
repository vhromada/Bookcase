package cz.vhromada.book.repository

import cz.vhromada.book.CoreTestConfiguration
import cz.vhromada.book.utils.AuthorUtils
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
 * A class represents integration test for class [AuthorRepository].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CoreTestConfiguration::class])
@Transactional
@Rollback
class AuthorRepositoryIntegrationTest {

    /**
     * Instance of [EntityManager]
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private val entityManager: EntityManager? = null

    /**
     * Instance of [AuthorRepository]
     */
    @Autowired
    private val authorRepository: AuthorRepository? = null

    /**
     * Checks autowired fields.
     */
    @BeforeEach
    fun setUp() {
        assertSoftly { softly ->
            softly.assertThat(authorRepository).isNotNull
            softly.assertThat(entityManager).isNotNull
        }
    }

    /**
     * Test method for get authors.
     */
    @Test
    fun getAuthors() {
        val authors = authorRepository!!.findAll()

        AuthorUtils.assertAuthorsDeepEquals(AuthorUtils.getAuthors(), authors)

        assertThat(AuthorUtils.getAuthorsCount(entityManager!!)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
    }

    /**
     * Test method for get author.
     */
    @Test
    fun getAuthor() {
        for (i in 1..AuthorUtils.AUTHORS_COUNT) {
            val author = authorRepository!!.findById(i).orElse(null)

            AuthorUtils.assertAuthorDeepEquals(AuthorUtils.getAuthor(i), author)
        }

        assertThat(authorRepository!!.findById(Int.MAX_VALUE).isPresent).isFalse()

        assertThat(AuthorUtils.getAuthorsCount(entityManager!!)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
    }

    /**
     * Test method for add author.
     */
    @Test
    fun add() {
        val author = AuthorUtils.newAuthorDomain(null)

        authorRepository!!.save(author)

        assertThat(author.id).isEqualTo(AuthorUtils.AUTHORS_COUNT + 1)

        val addedAuthor = AuthorUtils.getAuthor(entityManager!!, AuthorUtils.AUTHORS_COUNT + 1)
        val expectedAddAuthor = AuthorUtils.newAuthorDomain(AuthorUtils.AUTHORS_COUNT + 1, 0)
        AuthorUtils.assertAuthorDeepEquals(expectedAddAuthor, addedAuthor)

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT + 1)
    }

    /**
     * Test method for update author.
     */
    @Test
    fun update() {
        val author = AuthorUtils.newAuthorDomain(1)

        authorRepository!!.save(author)

        val updatedAuthor = AuthorUtils.getAuthor(entityManager!!, 1)
        val expectedUpdatedAuthor = AuthorUtils.newAuthorDomain(1)
        AuthorUtils.assertAuthorDeepEquals(expectedUpdatedAuthor, updatedAuthor)

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
    }

    /**
     * Test method for remove author.
     */
    @Test
    fun remove() {
        entityManager!!.createNativeQuery("DELETE FROM book_authors").executeUpdate()

        authorRepository!!.delete(AuthorUtils.getAuthor(entityManager, 1)!!)

        assertThat(AuthorUtils.getAuthor(entityManager, 1)).isNull()

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT - 1)
    }

    /**
     * Test method for remove all authors.
     */
    @Test
    fun removeAll() {
        entityManager!!.createNativeQuery("DELETE FROM book_authors").executeUpdate()

        authorRepository!!.deleteAll()

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(0)
    }

}

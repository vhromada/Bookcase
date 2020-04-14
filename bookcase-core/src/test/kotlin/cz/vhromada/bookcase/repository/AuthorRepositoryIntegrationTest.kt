package cz.vhromada.bookcase.repository

import cz.vhromada.bookcase.CoreTestConfiguration
import cz.vhromada.bookcase.utils.AuditUtils
import cz.vhromada.bookcase.utils.AuthorUtils
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
    private lateinit var entityManager: EntityManager

    /**
     * Instance of [AuthorRepository]
     */
    @Autowired
    private lateinit var authorRepository: AuthorRepository

    /**
     * Test method for get authors.
     */
    @Test
    fun getAuthors() {
        val authors = authorRepository.findAll()

        AuthorUtils.assertAuthorsDeepEquals(AuthorUtils.getAuthors(), authors)

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
    }

    /**
     * Test method for get author.
     */
    @Test
    @Suppress("UsePropertyAccessSyntax")
    fun getAuthor() {
        for (i in 1 until AuthorUtils.AUTHORS_COUNT) {
            val author = authorRepository.findById(i).orElse(null)

            AuthorUtils.assertAuthorDeepEquals(AuthorUtils.getAuthorDomain(i), author)
        }

        assertThat(authorRepository.findById(Int.MAX_VALUE).isPresent).isFalse()

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
    }

    /**
     * Test method for add author.
     */
    @Test
    fun add() {
        val author = AuthorUtils.newAuthorDomain(null)
                .copy(position = AuthorUtils.AUTHORS_COUNT)

        authorRepository.save(author)

        assertThat(author.id).isEqualTo(AuthorUtils.AUTHORS_COUNT + 1)

        val addedAuthor = AuthorUtils.getAuthor(entityManager, AuthorUtils.AUTHORS_COUNT + 1)
        val expectedAddAuthor = AuthorUtils.newAuthorDomain(null)
                .copy(id = AuthorUtils.AUTHORS_COUNT + 1, position = AuthorUtils.AUTHORS_COUNT)
        AuthorUtils.assertAuthorDeepEquals(expectedAddAuthor, addedAuthor)

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT + 1)
    }

    /**
     * Test method for update author.
     */
    @Test
    fun update() {
        val author = AuthorUtils.updateAuthor(entityManager, 1)

        authorRepository.save(author)

        val updatedAuthor = AuthorUtils.getAuthor(entityManager, 1)
        val expectedUpdatedAuthor = AuthorUtils.getAuthorDomain(1)
                .updated()
                .copy(position = AuthorUtils.POSITION)
        AuthorUtils.assertAuthorDeepEquals(expectedUpdatedAuthor, updatedAuthor)

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
    }

    /**
     * Test method for remove author.
     */
    @Test
    @DirtiesContext
    fun remove() {
        val author = AuthorUtils.newAuthorDomain(null)
                .copy(position = AuthorUtils.AUTHORS_COUNT)
        entityManager.persist(author)
        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT + 1)

        authorRepository.delete(author)

        assertThat(AuthorUtils.getAuthor(entityManager, author.id!!)).isNull()

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
    }

    /**
     * Test method for remove all authors.
     */
    @Test
    fun removeAll() {
        entityManager.createNativeQuery("DELETE FROM book_authors").executeUpdate()

        authorRepository.deleteAll()

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(0)
    }

    /**
     * Test method for get authors for user.
     */
    @Test
    fun findByAuditCreatedUser() {
        val authors = authorRepository.findByAuditCreatedUser(AuditUtils.getAudit().createdUser)

        AuthorUtils.assertAuthorsDeepEquals(AuthorUtils.getAuthors(), authors)

        assertThat(AuthorUtils.getAuthorsCount(entityManager)).isEqualTo(AuthorUtils.AUTHORS_COUNT)
    }

}

package cz.vhromada.book.service.impl

import cz.vhromada.book.domain.Author
import cz.vhromada.book.repository.AuthorRepository
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.utils.AuthorUtils
import org.assertj.core.api.Assertions
import org.mockito.Mock
import org.springframework.data.jpa.repository.JpaRepository

/**
 * A class represents test for class [AuthorServiceImpl].
 *
 * @author Vladimir Hromada
 */
class AuthorServiceImplTest : AbstractServiceTest<Author>() {

    override val cacheKey: String = "authors"

    override val itemClass: Class<Author> = Author::class.java

    /**
     * Instance of [AuthorRepository]
     */
    @Mock
    private val authorRepository: AuthorRepository? = null

    override fun getRepository(): JpaRepository<Author, Int> {
        Assertions.assertThat(authorRepository).isNotNull

        return authorRepository!!
    }

    override fun getBookcaseService(): BookcaseService<Author> {
        return AuthorServiceImpl(authorRepository!!, cache!!)
    }

    override fun getItem1(): Author {
        return AuthorUtils.newAuthorDomain(1)
    }

    override fun getItem2(): Author {
        return AuthorUtils.newAuthorDomain(2)
    }

    override fun getAddItem(): Author {
        return AuthorUtils.newAuthorDomain(null)
    }

    override fun getCopyItem(): Author {
        val author = AuthorUtils.newAuthorDomain(null)
        author.position = 0

        return author
    }

    override fun assertDataDeepEquals(expected: Author, actual: Author) {
        AuthorUtils.assertAuthorDeepEquals(expected, actual)
    }

}

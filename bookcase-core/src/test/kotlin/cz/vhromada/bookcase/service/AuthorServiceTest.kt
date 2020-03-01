package cz.vhromada.bookcase.service

import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import cz.vhromada.bookcase.domain.Author
import cz.vhromada.bookcase.repository.AuthorRepository
import cz.vhromada.bookcase.utils.AuthorUtils
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.test.service.MovableServiceTest
import org.mockito.Mock
import org.springframework.data.jpa.repository.JpaRepository

/**
 * A class represents test for class [AuthorService].
 *
 * @author Vladimir Hromada
 */
class AuthorServiceTest : MovableServiceTest<Author>() {

    /**
     * Instance of [AuthorRepository]
     */
    @Mock
    private lateinit var authorRepository: AuthorRepository

    override fun getRepository(): JpaRepository<Author, Int> {
        return authorRepository
    }

    override fun getService(): MovableService<Author> {
        return AuthorService(authorRepository, cache)
    }

    override fun getCacheKey(): String {
        return "authors"
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
        return AuthorUtils.newAuthorDomain(null)
                .copy(position = 0)
    }

    override fun anyItem(): Author {
        return any()
    }

    override fun argumentCaptorItem(): KArgumentCaptor<Author> {
        return argumentCaptor()
    }

    override fun assertDataDeepEquals(expected: Author, actual: Author) {
        AuthorUtils.assertAuthorDeepEquals(expected, actual)
    }

}

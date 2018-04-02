package cz.vhromada.book.service.impl

import cz.vhromada.book.domain.Author
import cz.vhromada.book.repository.AuthorRepository
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.stub.RepositoryStub
import cz.vhromada.book.stub.impl.repository.AuthorRepositoryStub
import cz.vhromada.book.utils.AuthorUtils

/**
 * A class represents test for class [AuthorServiceImpl].
 *
 * @author Vladimir Hromada
 */
class AuthorServiceImplTest : AbstractServiceTest<Author>() {

    override val cacheKey = "authors"

    override val itemClass = Author::class.java

    /**
     * Instance of [AuthorRepository]
     */
    private val authorRepository = AuthorRepositoryStub()

    override fun getRepository(): RepositoryStub<Author> {
        return authorRepository
    }

    override fun getBookcaseService(): BookcaseService<Author> {
        return AuthorServiceImpl(authorRepository, cache)
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

package cz.vhromada.book.stub.impl.repository

import cz.vhromada.book.domain.Author
import cz.vhromada.book.repository.AuthorRepository
import java.util.Optional

/**
 * An abstract class represents stub for repository for authors.
 *
 * @author Vladimir Hromada
 */
class AuthorRepositoryStub : AuthorRepository, AbstractRepositoryStub<Author>() {

    override fun deleteById(id: Int) {
        super.deleteById(id)
    }

    override fun getOne(id: Int): Author {
        return super.getOne(id)
    }

    override fun existsById(id: Int): Boolean {
        return super.existsById(id)
    }

    override fun findById(id: Int): Optional<Author> {
        return super.findById(id)
    }

}

package cz.vhromada.book.stub.impl.repository

import cz.vhromada.book.domain.Book
import cz.vhromada.book.repository.BookRepository
import java.util.Optional

/**
 * A class represents stub for repository for books.
 *
 * @author Vladimir Hromada
 */
class BookRepositoryStub : BookRepository, AbstractRepositoryStub<Book>() {

    override fun deleteById(id: Int) {
        super.deleteById(id)
    }

    override fun getOne(id: Int): Book {
        return super.getOne(id)
    }

    override fun existsById(id: Int): Boolean {
        return super.existsById(id)
    }

    override fun findById(id: Int): Optional<Book> {
        return super.findById(id)
    }

}

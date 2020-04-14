package cz.vhromada.bookcase.repository

import cz.vhromada.bookcase.domain.Book
import org.springframework.data.jpa.repository.JpaRepository

/**
 * An interface represents repository for books.
 *
 * @author Vladimir Hromada
 */
interface BookRepository : JpaRepository<Book, Int> {

    /**
     * Returns all books created by user.
     *
     * @param user user's ID
     * @return all books created by user
     */
    fun findByAuditCreatedUser(user: Int): List<Book>

}

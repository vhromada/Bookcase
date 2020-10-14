package com.github.vhromada.bookcase.repository

import com.github.vhromada.bookcase.domain.Book
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
     * @param user user's UUID
     * @return all books created by user
     */
    fun findByAuditCreatedUser(user: String): List<Book>

}

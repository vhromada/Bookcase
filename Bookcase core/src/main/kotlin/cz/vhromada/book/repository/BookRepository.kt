package cz.vhromada.book.repository

import cz.vhromada.book.domain.Book
import org.springframework.data.jpa.repository.JpaRepository

/**
 * An interface represents repository for book.
 *
 * @author Vladimir Hromada
 */
interface BookRepository : JpaRepository<Book, Int>

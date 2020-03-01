package cz.vhromada.bookcase.repository

import cz.vhromada.bookcase.domain.Book
import org.springframework.data.jpa.repository.JpaRepository

/**
 * An interface represents repository for books.
 *
 * @author Vladimir Hromada
 */
interface BookRepository : JpaRepository<Book, Int>

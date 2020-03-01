package cz.vhromada.bookcase.repository

import cz.vhromada.bookcase.domain.Author
import org.springframework.data.jpa.repository.JpaRepository

/**
 * An interface represents repository for authors.
 *
 * @author Vladimir Hromada
 */
interface AuthorRepository : JpaRepository<Author, Int>

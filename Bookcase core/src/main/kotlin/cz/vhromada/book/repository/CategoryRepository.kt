package cz.vhromada.book.repository

import cz.vhromada.book.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

/**
 * An interface represents repository for categories.
 *
 * @author Vladimir Hromada
 */
interface CategoryRepository : JpaRepository<Category, Int>

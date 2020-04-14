package cz.vhromada.bookcase.repository

import cz.vhromada.bookcase.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

/**
 * An interface represents repository for categories.
 *
 * @author Vladimir Hromada
 */
interface CategoryRepository : JpaRepository<Category, Int> {

    /**
     * Returns all categories created by user.
     *
     * @param user user's ID
     * @return all categories created by user
     */
    fun findByAuditCreatedUser(user: Int): List<Category>

}

package cz.vhromada.book.entity

import cz.vhromada.book.common.Language
import cz.vhromada.book.common.Movable
import java.util.Objects

/**
 * A class represents book.
 *
 * @author Vladimir Hromada
 */
data class Book(

    override var id: Int?,

    /**
     * Czech name
     */
    val czechName: String,

    /**
     * Original name
     */
    val originalName: String,

    /**
     * Languages
     */
    val languages: List<Language>,

    /**
     * ISBN
     */
    val isbn: String?,

    /**
     * Issue year
     */
    val issueYear: Int,

    /**
     * Description
     */
    val description: String,

    /**
     * True if there is electronic version
     */
    val electronic: Boolean,

    /**
     * True if there is paper version
     */
    val paper: Boolean,

    /**
     * Note
     */
    val note: String?,

    override var position: Int,

    /**
     * Authors
     */
    val authors: List<Author>,

    /**
     * Categories
     */
    val categories: List<Category>

) : Movable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return if (other !is Book || id == null) {
            false
        } else {
            id == other.id
        }
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}


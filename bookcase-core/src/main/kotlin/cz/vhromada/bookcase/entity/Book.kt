package cz.vhromada.bookcase.entity

import cz.vhromada.common.entity.Movable
import java.util.Objects

/**
 * A class represents book.
 *
 * @author Vladimir Hromada
 */
data class Book(

        /**
         * ID
         */
        override var id: Int?,

        /**
         * Czech name
         */
        val czechName: String?,

        /**
         * Original name
         */
        val originalName: String?,

        /**
         * ISBN
         */
        val isbn: String?,

        /**
         * Issue year
         */
        val issueYear: Int?,

        /**
         * Description
         */
        val description: String?,

        /**
         * Returns note.
         *
         * @return note
         */
        val note: String?,

        /**
         * Position
         */
        override var position: Int?,

        /**
         * Authors
         */
        val authors: List<Author?>?,

        /**
         * Categories
         */
        val categories: List<Category?>?) : Movable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other !is Book || id == null) {
            false
        } else id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}

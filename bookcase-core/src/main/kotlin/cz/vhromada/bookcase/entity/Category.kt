package cz.vhromada.bookcase.entity

import cz.vhromada.common.entity.Movable
import java.util.Objects

/**
 * A class represents category.
 *
 * @author Vladimir Hromada
 */
data class Category(
        /**
         * ID
         */
        override var id: Int?,

        /**
         * Name
         */
        val name: String?,

        /**
         * Position
         */
        override var position: Int?) : Movable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other !is Category || id == null) {
            false
        } else id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return String.format("Category [id=%d, name=%s, position=%d]", id, name, position)
    }

}

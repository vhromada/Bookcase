package cz.vhromada.book.entity

import cz.vhromada.book.common.Movable
import java.util.Objects

/**
 * A class represents category.
 *
 * @author Vladimir Hromada
 */
data class Category(

    override var id: Int?,

    /**
     * Name
     */
    val name: String,

    override var position: Int

) : Movable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return if (other !is Category || id == null) {
            false
        } else {
            id == other.id
        }
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}


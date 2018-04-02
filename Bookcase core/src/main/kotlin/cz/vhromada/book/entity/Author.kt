package cz.vhromada.book.entity

import cz.vhromada.book.common.Movable
import java.util.Objects

/**
 * A class represents author.
 *
 * @author Vladimir Hromada
 */
data class Author(

    override var id: Int?,

    /**
     * First name
     */
    val firstName: String,

    /**
     * Middle name
     */
    val middleName: String?,

    /**
     * Last name
     */
    val lastName: String,

    override var position: Int

) : Movable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return if (other !is Author || id == null) {
            false
        } else {
            id == other.id
        }
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}
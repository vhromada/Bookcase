package cz.vhromada.book.web.fo

import java.util.Objects
import javax.validation.constraints.NotBlank

/**
 * A class represents FO for author.
 *
 * @author Vladimir Hromada
 */
data class AuthorFO(

    /**
     * ID
     */
    val id: Int? = null,

    /**
     * First name
     */
    @field:NotBlank
    var firstName: String? = null,

    /**
     * Middle name
     */
    var middleName: String? = null,

    /**
     * Last name
     */
    @field:NotBlank
    var lastName: String? = null,

    /**
     * Position
     */
    val position: Int = 0) {

    /**
     * Normalizes data.
     */
    fun normalize() {
        if (middleName.isNullOrEmpty()) {
            middleName = null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return if (other !is AuthorFO || id == null) {
            false
        } else {
            id == other.id
        }
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}

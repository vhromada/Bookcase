package cz.vhromada.book.web.fo

import java.util.Objects
import javax.validation.constraints.NotBlank

/**
 * A class represents FO for category.
 *
 * @author Vladimir Hromada
 */
data class CategoryFO(

    /**
     * ID
     */
    val id: Int? = null,

    /**
     * Name
     */
    @field:NotBlank
    var name: String? = null,

    /**
     * Position
     */
    val position: Int = 0) {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return if (other !is CategoryFO || id == null) {
            false
        } else {
            id == other.id
        }
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}

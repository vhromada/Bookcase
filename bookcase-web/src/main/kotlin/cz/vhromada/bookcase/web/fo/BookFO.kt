package cz.vhromada.bookcase.web.fo

import cz.vhromada.bookcase.web.validator.constraint.DateRange
import cz.vhromada.common.utils.Constants
import java.io.Serializable
import java.util.Objects
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * A class represents FO for book.
 *
 * @author Vladimir Hromada
 */
class BookFO(

        /**
         * ID
         */
        val id: Int?,

        /**
         * Czech name
         */
        @field:NotBlank
        val czechName: String?,

        /**
         * Original name
         */
        @field:NotBlank
        val originalName: String?,

        /**
         * ISBN
         */
        val isbn: String?,

        /**
         * Issue year
         */
        @field:DateRange(Constants.MIN_YEAR)
        val issueYear: String?,

        /**
         * Description
         */
        @field:NotBlank
        val description: String?,

        /**
         * Note
         */
        val note: String?,

        /**
         * Position
         */
        val position: Int?,

        /**
         * Authors
         */
        @field:NotNull
        @field:Size(min = 1)
        val authors: List<Int?>?,

        /**
         * Categories
         */
        @field:NotNull
        @field:Size(min = 1)
        val categories: List<Int?>?) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other !is BookFO || id == null) {
            false
        } else id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}

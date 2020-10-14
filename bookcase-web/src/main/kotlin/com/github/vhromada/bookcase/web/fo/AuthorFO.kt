package com.github.vhromada.bookcase.web.fo

import java.io.Serializable
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
        val id: Int?,

        /**
         * First name
         */
        @field:NotBlank
        val firstName: String?,

        /**
         * Middle name
         */
        val middleName: String?,

        /**
         * Last name
         */
        @field:NotBlank
        val lastName: String?,

        /**
         * Position
         */
        val position: Int?) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other !is AuthorFO || id == null) {
            false
        } else id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}

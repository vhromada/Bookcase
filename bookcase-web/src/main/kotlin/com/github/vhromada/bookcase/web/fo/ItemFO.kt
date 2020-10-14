package com.github.vhromada.bookcase.web.fo

import com.github.vhromada.bookcase.common.Format
import com.github.vhromada.common.entity.Language
import java.io.Serializable
import java.util.Objects
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * A class represents FO for item.
 *
 * @author Vladimir Hromada
 */
data class ItemFO(

        /**
         * ID
         */
        val id: Int?,

        /**
         * Languages
         */
        @field:NotNull
        @field:Size(min = 1)
        val languages: List<Language?>?,

        /**
         * Format
         */
        @field:NotNull
        val format: Format?,

        /**
         * Note
         */
        val note: String?,

        /**
         * Position
         */
        val position: Int?) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other !is ItemFO || id == null) {
            false
        } else {
            id == other.id
        }
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}

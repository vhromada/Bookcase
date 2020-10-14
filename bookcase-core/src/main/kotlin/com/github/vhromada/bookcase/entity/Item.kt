package com.github.vhromada.bookcase.entity

import com.github.vhromada.bookcase.common.Format
import com.github.vhromada.bookcase.domain.Item
import com.github.vhromada.common.entity.Language
import com.github.vhromada.common.entity.Movable
import java.util.Objects

/**
 * A class represents item.
 *
 * @author Vladimir Hromada
 */
data class Item(

        /**
         * ID
         */
        override var id: Int?,

        /**
         * Languages
         */
        val languages: List<Language?>?,

        /**
         * Format
         */
        val format: Format?,

        /**
         * Note
         */
        val note: String?,

        /**
         * Position
         */
        override var position: Int?) : Movable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other !is Item || id == null) {
            false
        } else {
            id == other.id
        }
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}

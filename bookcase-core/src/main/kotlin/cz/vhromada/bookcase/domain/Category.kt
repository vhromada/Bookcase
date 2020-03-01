package cz.vhromada.bookcase.domain

import cz.vhromada.common.Movable
import java.util.Objects
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

/**
 * A class represents category.
 *
 * @author Vladimir Hromada
 */
@Entity
@Table(name = "categories")
data class Category(

        /**
         * ID
         */
        @Id
        @SequenceGenerator(name = "category_generator", sequenceName = "categories_sq", allocationSize = 0)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
        override var id: Int? = null,

        /**
         * Name
         */
        @Column(name = "category_name")
        val name: String,

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

}

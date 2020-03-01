package cz.vhromada.bookcase.domain

import cz.vhromada.bookcase.common.Format
import cz.vhromada.common.Language
import cz.vhromada.common.Movable
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.util.Objects
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.SequenceGenerator
import javax.persistence.Table

/**
 * A class represents item.
 *
 * @author Vladimir Hromada
 */
@Entity
@Table(name = "items")
data class Item(

        /**
         * ID
         */
        @Id
        @SequenceGenerator(name = "item_generator", sequenceName = "items_sq", allocationSize = 0)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_generator")
        override var id: Int?,

        /**
         * Languages
         */
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "item_languages", joinColumns = [JoinColumn(name = "item")])
        @Enumerated(EnumType.STRING)
        @Fetch(FetchMode.SELECT)
        @Column(name = "item_language")
        val languages: List<Language>,

        /**
         * Format
         */
        @Enumerated(EnumType.STRING)
        val format: Format,

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

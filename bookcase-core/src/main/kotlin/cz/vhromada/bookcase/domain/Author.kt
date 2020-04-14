package cz.vhromada.bookcase.domain

import cz.vhromada.common.domain.Audit
import cz.vhromada.common.domain.AuditEntity
import java.util.Objects
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

/**
 * A class represents author.
 *
 * @author Vladimir Hromada
 */
@Entity
@Table(name = "authors")
data class Author(

        /**
         * ID
         */
        @Id
        @SequenceGenerator(name = "author_generator", sequenceName = "authors_sq", allocationSize = 0)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
        override var id: Int?,

        /**
         * First name
         */
        @Column(name = "first_name")
        val firstName: String,

        /**
         * Middle name
         */
        @Column(name = "middle_name")
        val middleName: String?,

        /**
         * Last name
         */
        @Column(name = "last_name")
        val lastName: String,

        /**
         * Position
         */
        override var position: Int?,

        /**
         * Audit
         */
        @Embedded
        override var audit: Audit?) : AuditEntity(audit) {

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

package com.github.vhromada.bookcase.domain

import com.github.vhromada.common.domain.Audit
import com.github.vhromada.common.domain.AuditEntity
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.util.Objects
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.OneToMany
import javax.persistence.OrderBy
import javax.persistence.SequenceGenerator
import javax.persistence.Table

/**
 * A class represents book.
 *
 * @author Vladimir Hromada
 */
@Entity
@Table(name = "books")
data class Book(

        /**
         * ID
         */
        @Id
        @SequenceGenerator(name = "book_generator", sequenceName = "books_sq", allocationSize = 0)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
        override var id: Int?,

        /**
         * Czech name
         */
        @Column(name = "czech_name")
        val czechName: String,

        /**
         * Original name
         */
        @Column(name = "original_name")
        val originalName: String,

        /**
         * ISBN
         */
        val isbn: String?,

        /**
         * Issue year
         */
        @Column(name = "issue_year")
        val issueYear: Int,

        /**
         * Description
         */
        val description: String,

        /**
         * Returns note.
         *
         * @return note
         */
        val note: String?,

        /**
         * Position
         */
        override var position: Int?,

        /**
         * Authors
         */
        @OneToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "book_authors", joinColumns = [JoinColumn(name = "book")], inverseJoinColumns = [JoinColumn(name = "author")])
        @Fetch(FetchMode.SELECT)
        @OrderBy("position, id")
        val authors: List<Author>,

        /**
         * Items
         */
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
        @JoinColumn(name = "book", referencedColumnName = "id")
        @OrderBy("position, id")
        @Fetch(FetchMode.SELECT)
        val items: List<Item>,

        /**
         * Audit
         */
        @Embedded
        override var audit: Audit?) : AuditEntity(audit) {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other !is Book || id == null) {
            false
        } else id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}

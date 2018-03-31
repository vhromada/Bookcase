package cz.vhromada.book.domain

import cz.vhromada.book.common.Language
import cz.vhromada.book.common.Movable
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
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
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
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

    @field:Id
    @field:SequenceGenerator(name = "book_generator", sequenceName = "books_sq", allocationSize = 0)
    @field:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    override var id: Int?,

    /**
     * Czech name
     */
    @field:Column(name = "czech_name")
    val czechName: String,

    /**
     * Original name
     */
    @field:Column(name = "original_name")
    val originalName: String,

    /**
     * Languages
     */
    @field:ElementCollection(fetch = FetchType.EAGER)
    @field:CollectionTable(name = "book_languages", joinColumns = [JoinColumn(name = "book")])
    @field:Column(name = "book_language")
    @field:Enumerated(EnumType.STRING)
    @field:Fetch(FetchMode.SELECT)
    val languages: List<Language>,

    /**
     * ISBN
     */
    val isbn: String?,

    /**
     * Issue year
     */
    @field:Column(name = "issue_year")
    val issueYear: Int,

    /**
     * Description
     */
    val description: String,

    /**
     * True if there is electronic version
     */
    val electronic: Boolean,

    /**
     * True if there is paper version
     */
    val paper: Boolean,

    /**
     * Note
     */
    val note: String?,

    override var position: Int,

    /**
     * Authors
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_authors", joinColumns = [JoinColumn(name = "book")], inverseJoinColumns = [JoinColumn(name = "author")])
    @OrderBy("id")
    @Fetch(FetchMode.SELECT)
    val authors: List<Author>,

    /**
     * Categories
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_categories", joinColumns = [JoinColumn(name = "book")], inverseJoinColumns = [JoinColumn(name = "category")])
    @OrderBy("id")
    @Fetch(FetchMode.SELECT)
    val categories: List<Category>

) : Movable

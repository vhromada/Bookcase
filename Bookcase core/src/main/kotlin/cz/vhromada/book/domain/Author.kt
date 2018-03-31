package cz.vhromada.book.domain

import cz.vhromada.book.common.Movable
import javax.persistence.Column
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

    @field:Id
    @field:SequenceGenerator(name = "author_generator", sequenceName = "authors_sq", allocationSize = 0)
    @field:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    override var id: Int?,

    /**
     * First name
     */
    @field:Column(name = "first_name")
    val firstName: String,

    /**
     * Middle name
     */
    @field:Column(name = "middle_name")
    val middleName: String?,

    /**
     * Last name
     */
    @field:Column(name = "last_name")
    val lastName: String,

    override var position: Int

) : Movable

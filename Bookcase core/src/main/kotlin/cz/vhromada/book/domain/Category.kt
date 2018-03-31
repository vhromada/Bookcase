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
 * A class represents category.
 *
 * @author Vladimir Hromada
 */
@Entity
@Table(name = "categories")
data class Category(

    @field:Id
    @field:SequenceGenerator(name = "category_generator", sequenceName = "categories_sq", allocationSize = 0)
    @field:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    override var id: Int?,

    /**
     * Name
     */
    @field:Column(name = "category_name")
    val name: String,

    override var position: Int

) : Movable

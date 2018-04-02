package cz.vhromada.book.entity

import cz.vhromada.book.common.Movable

/**
 * A class represents category.
 *
 * @author Vladimir Hromada
 */
data class Category(

    override var id: Int?,

    /**
     * Name
     */
    val name: String,

    override var position: Int

) : Movable

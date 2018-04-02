package cz.vhromada.book.entity

import cz.vhromada.book.common.Movable

/**
 * A class represents author.
 *
 * @author Vladimir Hromada
 */
data class Author(

    override var id: Int?,

    /**
     * First name
     */
    val firstName: String,

    /**
     * Middle name
     */
    val middleName: String?,

    /**
     * Last name
     */
    val lastName: String,

    override var position: Int

) : Movable

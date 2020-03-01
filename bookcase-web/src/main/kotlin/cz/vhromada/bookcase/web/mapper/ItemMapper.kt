package cz.vhromada.bookcase.web.mapper

import cz.vhromada.bookcase.entity.Item
import cz.vhromada.bookcase.web.fo.ItemFO

/**
 * An interface represents mapper for items.
 *
 * @author Vladimir Hromada
 */
interface ItemMapper {

    /**
     * Returns FO for item.
     *
     * @param source item
     * @return FO for item
     */
    fun map(source: Item): ItemFO

    /**
     * Returns item.
     *
     * @param source FO for item
     * @return item
     */
    fun mapBack(source: ItemFO): Item

}

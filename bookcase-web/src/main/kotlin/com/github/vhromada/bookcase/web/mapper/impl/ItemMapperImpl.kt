package com.github.vhromada.bookcase.web.mapper.impl

import com.github.vhromada.bookcase.entity.Item
import com.github.vhromada.bookcase.web.fo.ItemFO
import com.github.vhromada.bookcase.web.mapper.ItemMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for items.
 *
 * @author Vladimir Hromada
 */
@Component("webItemMapper")
class ItemMapperImpl : ItemMapper {

    override fun map(source: Item): ItemFO {
        return ItemFO(
                id = source.id,
                languages = source.languages,
                format = source.format,
                note = source.note,
                position = source.position)
    }

    override fun mapBack(source: ItemFO): Item {
        return Item(
                id = source.id,
                languages = source.languages,
                format = source.format,
                note = source.note,
                position = source.position)
    }

}

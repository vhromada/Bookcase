package com.github.vhromada.bookcase.mapper

import com.github.vhromada.bookcase.entity.Item
import com.github.vhromada.common.mapper.Mapper
import org.springframework.stereotype.Component

/**
 * A class represents mapper for item.
 *
 * @author Vladimir Hromada
 */
@Component("itemMapper")
class ItemMapper : Mapper<Item, com.github.vhromada.bookcase.domain.Item> {

    override fun map(source: Item): com.github.vhromada.bookcase.domain.Item {
        return com.github.vhromada.bookcase.domain.Item(
                id = source.id,
                languages = source.languages!!.filterNotNull(),
                format = source.format!!,
                note = source.note,
                position = source.position,
                audit = null)
    }

    override fun mapBack(source: com.github.vhromada.bookcase.domain.Item): Item {
        return Item(
                id = source.id,
                languages = source.languages,
                format = source.format,
                note = source.note,
                position = source.position)
    }

}

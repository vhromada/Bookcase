package cz.vhromada.bookcase.mapper

import cz.vhromada.bookcase.entity.Item
import cz.vhromada.common.mapper.Mapper
import org.springframework.stereotype.Component

/**
 * A class represents mapper for item.
 *
 * @author Vladimir Hromada
 */
@Component("itemMapper")
class ItemMapper : Mapper<Item, cz.vhromada.bookcase.domain.Item> {

    override fun map(source: Item): cz.vhromada.bookcase.domain.Item {
        return cz.vhromada.bookcase.domain.Item(
                id = source.id,
                languages = source.languages!!.filterNotNull(),
                format = source.format!!,
                note = source.note,
                position = source.position)
    }

    override fun mapBack(source: cz.vhromada.bookcase.domain.Item): Item {
        return Item(
                id = source.id,
                languages = source.languages,
                format = source.format,
                note = source.note,
                position = source.position)
    }

}

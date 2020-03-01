package cz.vhromada.bookcase.web.mapper

import cz.vhromada.bookcase.entity.Item
import cz.vhromada.bookcase.web.BookcaseMapperTestConfiguration
import cz.vhromada.bookcase.web.common.ItemUtils
import cz.vhromada.bookcase.web.fo.ItemFO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [Item] and [ItemFO].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [BookcaseMapperTestConfiguration::class])
class ItemMapperTest {

    /**
     * Mapper for items
     */
    @Autowired
    private lateinit var mapper: ItemMapper

    /**
     * Test method for [ItemMapper.map].
     */
    @Test
    fun map() {
        val item = ItemUtils.getItem()

        val itemFO = mapper.map(item)

        ItemUtils.assertItemDeepEquals(itemFO, item)
    }

    /**
     * Test method for [ItemMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val itemFO = ItemUtils.getItemFO()

        val item = mapper.mapBack(itemFO)

        ItemUtils.assertItemDeepEquals(itemFO, item)
    }

}

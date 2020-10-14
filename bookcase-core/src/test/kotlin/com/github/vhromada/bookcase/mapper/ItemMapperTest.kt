package com.github.vhromada.bookcase.mapper

import com.github.vhromada.bookcase.BookcaseTestConfiguration
import com.github.vhromada.bookcase.entity.Item
import com.github.vhromada.bookcase.utils.ItemUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [com.github.vhromada.bookcase.domain.Item] and [Item].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [BookcaseTestConfiguration::class])
class ItemMapperTest {

    /**
     * Instance of [ItemMapper]
     */
    @Autowired
    private lateinit var mapper: ItemMapper

    /**
     * Test method for [ItemMapper.map].
     */
    @Test
    fun map() {
        val item = ItemUtils.newItem(1)
        val itemDomain = mapper.map(item)

        ItemUtils.assertItemDeepEquals(item, itemDomain)
    }

    /**
     * Test method for [ItemMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val itemDomain = ItemUtils.newItemDomain(1)
        val item = mapper.mapBack(itemDomain)

        ItemUtils.assertItemDeepEquals(item, itemDomain)
    }

}

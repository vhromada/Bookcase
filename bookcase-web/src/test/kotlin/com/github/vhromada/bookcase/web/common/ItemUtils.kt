package com.github.vhromada.bookcase.web.common

import com.github.vhromada.bookcase.common.Format
import com.github.vhromada.bookcase.entity.Item
import com.github.vhromada.bookcase.web.fo.ItemFO
import com.github.vhromada.common.entity.Language
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for items.
 *
 * @author Vladimir Hromada
 */
object ItemUtils {

    /**
     * Returns FO for item.
     *
     * @return FO for item
     */
    fun getItemFO(): ItemFO {
        return ItemFO(
                id = 1,
                languages = listOf(Language.CZ),
                format = Format.PAPER,
                note = "Note",
                position = 0)
    }

    /**
     * Returns item.
     *
     * @return item
     */
    fun getItem(): Item {
        return Item(
                id = 1,
                languages = listOf(Language.CZ),
                format = Format.PAPER,
                note = "Note",
                position = 0)
    }

    /**
     * Asserts item deep equals.
     *
     * @param expected expected FO for item
     * @param actual   actual item
     */
    fun assertItemDeepEquals(expected: ItemFO?, actual: Item?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(actual!!.id).isEqualTo(expected!!.id)
            it.assertThat(actual.languages).isEqualTo(expected.languages)
            it.assertThat(actual.format).isEqualTo(expected.format)
            it.assertThat(actual.note).isEqualTo(expected.note)
            it.assertThat(actual.position).isEqualTo(expected.position)
        }
    }

}

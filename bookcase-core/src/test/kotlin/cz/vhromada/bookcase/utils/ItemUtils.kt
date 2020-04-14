package cz.vhromada.bookcase.utils

import cz.vhromada.bookcase.common.Format
import cz.vhromada.bookcase.entity.Item
import cz.vhromada.common.entity.Language
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import javax.persistence.EntityManager

/**
 * Updates item fields.
 *
 * @return updated item
 */
fun cz.vhromada.bookcase.domain.Item.updated(): cz.vhromada.bookcase.domain.Item {
    return copy(languages = listOf(Language.CZ), format = Format.PDF, note = "Note", audit = AuditUtils.newAudit())
}

/**
 * Updates item fields.
 *
 * @return updated item
 */
fun Item.updated(): Item {
    return copy(languages = listOf(Language.CZ), format = Format.PDF, note = "Note")
}

/**
 * A class represents utility class for items.
 *
 * @author Vladimir Hromada
 */
object ItemUtils {

    /**
     * Count of items
     */
    const val ITEMS_COUNT = 9

    /**
     * Count of items in book
     */
    const val ITEMS_PER_BOOK_COUNT = 3

    /**
     * Returns item.
     *
     * @param id ID
     * @return item
     */
    fun newItemDomain(id: Int?): cz.vhromada.bookcase.domain.Item {
        return cz.vhromada.bookcase.domain.Item(id = id, languages = emptyList(), format = Format.TXT, note = null, position = if (id == null) null else id - 1, audit = null)
                .updated()
    }

    /**
     * Returns item.
     *
     * @param id ID
     * @return item
     */
    fun newItem(id: Int?): Item {
        return Item(id = id, languages = emptyList(), format = Format.TXT, note = null, position = if (id == null) null else id - 1)
                .updated()
    }

    /**
     * Returns items for book.
     *
     * @param book book
     * @return items for book
     */
    fun getItems(book: Int): List<cz.vhromada.bookcase.domain.Item> {
        val items = mutableListOf<cz.vhromada.bookcase.domain.Item>()
        for (i in 1..ITEMS_PER_BOOK_COUNT) {
            items.add(getItem(book, i))
        }

        return items
    }

    /**
     * Returns item for index.
     *
     * @param index item index
     * @return item for index
     */
    fun getItem(index: Int): cz.vhromada.bookcase.domain.Item {
        val bookNumber = (index - 1) / ITEMS_PER_BOOK_COUNT + 1
        val itemNumber = (index - 1) % ITEMS_PER_BOOK_COUNT + 1

        return getItem(bookNumber, itemNumber)
    }

    /**
     * Returns item for indexes.
     *
     * @param bookIndex book index
     * @param itemIndex  item index
     * @return item for indexes
     */
    private fun getItem(bookIndex: Int, itemIndex: Int): cz.vhromada.bookcase.domain.Item {
        val languages = mutableListOf<Language>()
        val format: Format
        when (itemIndex) {
            1 -> {
                languages.add(Language.CZ)
                languages.add(Language.EN)
                format = Format.PAPER
            }
            2 -> {
                languages.add(Language.CZ)
                format = Format.PDF
            }
            3 -> {
                languages.add(Language.EN)
                format = Format.TXT
            }
            else -> throw IllegalArgumentException("Bad season index")
        }

        return cz.vhromada.bookcase.domain.Item(
                id = (bookIndex - 1) * ITEMS_PER_BOOK_COUNT + itemIndex,
                languages = languages,
                format = format,
                note = if (itemIndex != 2) "Book $bookIndex Item $itemIndex Note" else "",
                position = itemIndex - 1,
                audit = AuditUtils.getAudit())
    }

    /**
     * Returns item.
     *
     * @param entityManager entity manager
     * @param id            item ID
     * @return item
     */
    fun getItem(entityManager: EntityManager, id: Int): cz.vhromada.bookcase.domain.Item? {
        return entityManager.find(cz.vhromada.bookcase.domain.Item::class.java, id)
    }

    /**
     * Returns count of items.
     *
     * @param entityManager entity manager
     * @return count of items
     */
    @Suppress("CheckStyle")
    fun getItemsCount(entityManager: EntityManager): Int {
        return entityManager.createQuery("SELECT COUNT(i.id) FROM Item i", java.lang.Long::class.java).singleResult.toInt()
    }

    /**
     * Asserts items deep equals.
     *
     * @param expected expected items
     * @param actual   actual items
     */
    fun assertItemsDeepEquals(expected: List<cz.vhromada.bookcase.domain.Item?>?, actual: List<cz.vhromada.bookcase.domain.Item?>?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertThat(expected!!.size).isEqualTo(actual!!.size)
        if (expected.isNotEmpty()) {
            for (i in expected.indices) {
                assertItemDeepEquals(expected[i], actual[i])
            }
        }
    }

    /**
     * Asserts item deep equals.
     *
     * @param expected expected item
     * @param actual   actual item
     */
    fun assertItemDeepEquals(expected: cz.vhromada.bookcase.domain.Item?, actual: cz.vhromada.bookcase.domain.Item?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(expected!!.id).isEqualTo(actual!!.id)
            it.assertThat(expected.languages).isEqualTo(actual.languages)
            it.assertThat(expected.format).isEqualTo(actual.format)
            it.assertThat(expected.note).isEqualTo(actual.note)
            it.assertThat(expected.position).isEqualTo(actual.position)
        }
    }

    /**
     * Asserts items deep equals.
     *
     * @param expected expected list of item
     * @param actual   actual items
     */
    fun assertItemListDeepEquals(expected: List<Item?>?, actual: List<cz.vhromada.bookcase.domain.Item?>?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertThat(expected!!.size).isEqualTo(actual!!.size)
        if (expected.isNotEmpty()) {
            for (i in expected.indices) {
                assertItemDeepEquals(expected[i], actual[i])
            }
        }
    }

    /**
     * Asserts item deep equals.
     *
     * @param expected expected item
     * @param actual   actual item
     */
    fun assertItemDeepEquals(expected: Item?, actual: cz.vhromada.bookcase.domain.Item?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(actual!!.id).isEqualTo(expected!!.id)
            it.assertThat(expected.languages)
                    .hasSameSizeAs(actual.languages)
                    .hasSameElementsAs(actual.languages)
            it.assertThat(expected.format).isEqualTo(actual.format)
            it.assertThat(actual.note).isEqualTo(expected.note)
            it.assertThat(actual.position).isEqualTo(expected.position)
        }
    }

}

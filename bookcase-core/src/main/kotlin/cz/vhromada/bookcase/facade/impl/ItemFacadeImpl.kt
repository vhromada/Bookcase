package cz.vhromada.bookcase.facade.impl

import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.entity.Item
import cz.vhromada.bookcase.facade.ItemFacade
import cz.vhromada.common.facade.AbstractMovableChildFacade
import cz.vhromada.common.mapper.Mapper
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.utils.sorted
import cz.vhromada.common.validator.MovableValidator
import org.springframework.stereotype.Component

/**
 * A class represents implementation of service for items.
 *
 * @author Vladimir Hromada
 */
@Component("itemFacade")
class ItemFacadeImpl(
        bookService: MovableService<cz.vhromada.bookcase.domain.Book>,
        mapper: Mapper<Item, cz.vhromada.bookcase.domain.Item>,
        bookValidator: MovableValidator<Book>,
        itemValidator: MovableValidator<Item>
) : AbstractMovableChildFacade<Item, cz.vhromada.bookcase.domain.Item, Book, cz.vhromada.bookcase.domain.Book>(bookService, mapper, bookValidator, itemValidator), ItemFacade {

    override fun getDomainData(id: Int): cz.vhromada.bookcase.domain.Item? {
        val books = service.getAll()
        for (book in books) {
            for (item in book.items) {
                if (id == item.id) {
                    return item
                }
            }
        }

        return null
    }

    override fun getDomainList(parent: Book): List<cz.vhromada.bookcase.domain.Item> {
        return service.get(parent.id!!)!!.items
    }

    override fun getForAdd(parent: Book, data: cz.vhromada.bookcase.domain.Item): cz.vhromada.bookcase.domain.Book {
        val book = service.get(parent.id!!)!!
        val items = book.items.toMutableList()
        items.add(data)

        return book.copy(items = items)
    }

    override fun getForUpdate(data: Item): cz.vhromada.bookcase.domain.Book {
        val book = getBook(data)

        return book.copy(items = updateItem(book, getDataForUpdate(data)))
    }

    override fun getForRemove(data: Item): cz.vhromada.bookcase.domain.Book {
        val book = getBook(data)
        val items = book.items.filter { it.id != data.id }

        return book.copy(items = items)
    }

    override fun getForDuplicate(data: Item): cz.vhromada.bookcase.domain.Book {
        val book = getBook(data)
        val item = getItem(data.id, book)
        val items = book.items.toMutableList()
        items.add(item.copy(id = null))

        return book.copy(items = items)
    }

    override fun getForMove(data: Item, up: Boolean): cz.vhromada.bookcase.domain.Book {
        var book = getBook(data)
        val item = getItem(data.id, book)
        val items = book.items.sorted()

        val index = items.indexOf(item)
        val other = items[if (up) index - 1 else index + 1]
        val position = item.position!!
        item.position = other.position
        other.position = position

        book = book.copy(items = updateItem(book, item))
        return book.copy(items = updateItem(book, other))
    }

    /**
     * Returns book for item.
     *
     * @param item item
     * @return book for item
     */
    private fun getBook(item: Item): cz.vhromada.bookcase.domain.Book {
        for (book in service.getAll()) {
            for (itemDomain in book.items) {
                if (item.id == itemDomain.id) {
                    return book
                }
            }
        }

        throw IllegalStateException("Unknown item.")
    }

    /**
     * Returns item with ID.
     *
     * @param id    ID
     * @param book book
     * @return item with ID
     */
    private fun getItem(id: Int?, book: cz.vhromada.bookcase.domain.Book): cz.vhromada.bookcase.domain.Item {
        for (item in book.items) {
            if (id == item.id) {
                return item
            }
        }

        throw IllegalStateException("Unknown item.")
    }

    /**
     * Updates item.
     *
     * @param book book
     * @param item  item
     * @return updated items
     */
    private fun updateItem(book: cz.vhromada.bookcase.domain.Book, item: cz.vhromada.bookcase.domain.Item): List<cz.vhromada.bookcase.domain.Item> {
        val items = mutableListOf<cz.vhromada.bookcase.domain.Item>()
        for (itemDomain in book.items) {
            if (itemDomain == item) {
                items.add(item)
            } else {
                items.add(itemDomain)
            }
        }

        return items
    }

}

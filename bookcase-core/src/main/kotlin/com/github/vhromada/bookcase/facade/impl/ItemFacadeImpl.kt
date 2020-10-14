package com.github.vhromada.bookcase.facade.impl

import com.github.vhromada.bookcase.entity.Book
import com.github.vhromada.bookcase.entity.Item
import com.github.vhromada.bookcase.facade.ItemFacade
import com.github.vhromada.common.facade.AbstractMovableChildFacade
import com.github.vhromada.common.mapper.Mapper
import com.github.vhromada.common.provider.AccountProvider
import com.github.vhromada.common.provider.TimeProvider
import com.github.vhromada.common.service.MovableService
import com.github.vhromada.common.utils.sorted
import com.github.vhromada.common.validator.MovableValidator
import org.springframework.stereotype.Component

/**
 * A class represents implementation of service for items.
 *
 * @author Vladimir Hromada
 */
@Component("itemFacade")
class ItemFacadeImpl(
        bookService: MovableService<com.github.vhromada.bookcase.domain.Book>,
        accountProvider: AccountProvider,
        timeProvider: TimeProvider,
        mapper: Mapper<Item, com.github.vhromada.bookcase.domain.Item>,
        bookValidator: MovableValidator<Book>,
        itemValidator: MovableValidator<Item>
) : AbstractMovableChildFacade<Item, com.github.vhromada.bookcase.domain.Item, Book, com.github.vhromada.bookcase.domain.Book>(bookService, accountProvider, timeProvider, mapper, bookValidator,
        itemValidator), ItemFacade {

    override fun getDomainData(id: Int): com.github.vhromada.bookcase.domain.Item? {
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

    override fun getDomainList(parent: Book): List<com.github.vhromada.bookcase.domain.Item> {
        return service.get(parent.id!!).get().items
    }

    override fun getForAdd(parent: Book, data: com.github.vhromada.bookcase.domain.Item): com.github.vhromada.bookcase.domain.Book {
        val book = service.get(parent.id!!).get()
        val items = book.items.toMutableList()
        items.add(data)

        return book.copy(items = items)
    }

    override fun getForUpdate(data: Item): com.github.vhromada.bookcase.domain.Book {
        val book = getBook(data)

        return book.copy(items = updateItem(book, getDataForUpdate(data)))
    }

    override fun getForRemove(data: Item): com.github.vhromada.bookcase.domain.Book {
        val book = getBook(data)
        val items = book.items.filter { it.id != data.id }

        return book.copy(items = items)
    }

    override fun getForDuplicate(data: Item): com.github.vhromada.bookcase.domain.Book {
        val book = getBook(data)
        val item = getItem(data.id, book)
        val items = book.items.toMutableList()
        items.add(item.copy(id = null))

        return book.copy(items = items)
    }

    override fun getForMove(data: Item, up: Boolean): com.github.vhromada.bookcase.domain.Book {
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
    private fun getBook(item: Item): com.github.vhromada.bookcase.domain.Book {
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
    private fun getItem(id: Int?, book: com.github.vhromada.bookcase.domain.Book): com.github.vhromada.bookcase.domain.Item {
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
    private fun updateItem(book: com.github.vhromada.bookcase.domain.Book, item: com.github.vhromada.bookcase.domain.Item): List<com.github.vhromada.bookcase.domain.Item> {
        val items = mutableListOf<com.github.vhromada.bookcase.domain.Item>()
        for (itemDomain in book.items) {
            if (itemDomain == item) {
                val audit = getAudit()
                item.audit = itemDomain.audit!!.copy(updatedUser = audit.updatedUser, updatedTime = audit.updatedTime)
                items.add(item)
            } else {
                items.add(itemDomain)
            }
        }

        return items
    }

}

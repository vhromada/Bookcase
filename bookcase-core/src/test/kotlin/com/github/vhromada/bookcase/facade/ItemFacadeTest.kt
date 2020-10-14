package com.github.vhromada.bookcase.facade

import com.github.vhromada.bookcase.entity.Book
import com.github.vhromada.bookcase.entity.Item
import com.github.vhromada.bookcase.facade.impl.ItemFacadeImpl
import com.github.vhromada.bookcase.utils.BookUtils
import com.github.vhromada.bookcase.utils.ItemUtils
import com.github.vhromada.common.facade.MovableChildFacade
import com.github.vhromada.common.test.facade.MovableChildFacadeTest
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor

/**
 * A class represents test for class [ItemFacade].
 *
 * @author Vladimir Hromada
 */
class ItemFacadeTest : MovableChildFacadeTest<Item, com.github.vhromada.bookcase.domain.Item, Book, com.github.vhromada.bookcase.domain.Book>() {

    override fun getFacade(): MovableChildFacade<Item, Book> {
        return ItemFacadeImpl(service, accountProvider, timeProvider, mapper, parentMovableValidator, childMovableValidator)
    }

    override fun newParentEntity(id: Int): Book {
        return BookUtils.newBook(id)
    }

    override fun newParentDomain(id: Int): com.github.vhromada.bookcase.domain.Book {
        return BookUtils.newBookWithItems(id)
    }

    override fun newParentDomainWithChildren(id: Int, children: List<com.github.vhromada.bookcase.domain.Item>): com.github.vhromada.bookcase.domain.Book {
        return newParentDomain(id)
                .copy(items = children)
    }

    override fun newChildEntity(id: Int?): Item {
        return ItemUtils.newItem(id)
    }

    override fun newChildDomain(id: Int?): com.github.vhromada.bookcase.domain.Item {
        return ItemUtils.newItemDomain(id)
    }

    override fun getParentRemovedData(parent: com.github.vhromada.bookcase.domain.Book, child: com.github.vhromada.bookcase.domain.Item): com.github.vhromada.bookcase.domain.Book {
        val items = parent.items.toMutableList()
        items.remove(child)
        return parent.copy(items = items)
    }

    override fun anyParentEntity(): Book {
        return any()
    }

    override fun anyChildEntity(): Item {
        return any()
    }

    override fun anyChildDomain(): com.github.vhromada.bookcase.domain.Item {
        return any()
    }

    override fun argumentCaptorParentDomain(): KArgumentCaptor<com.github.vhromada.bookcase.domain.Book> {
        return argumentCaptor()
    }

    override fun assertParentDeepEquals(expected: com.github.vhromada.bookcase.domain.Book, actual: com.github.vhromada.bookcase.domain.Book) {
        BookUtils.assertBookDeepEquals(expected, actual)
    }

}

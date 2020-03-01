package cz.vhromada.bookcase.facade.impl

import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.entity.Item
import cz.vhromada.bookcase.utils.BookUtils
import cz.vhromada.bookcase.utils.ItemUtils
import cz.vhromada.common.facade.MovableChildFacade
import cz.vhromada.common.test.facade.MovableChildFacadeTest

/**
 * A class represents test for class [ItemFacadeImpl].
 *
 * @author Vladimir Hromada
 */
class ItemFacadeImplTest : MovableChildFacadeTest<Item, cz.vhromada.bookcase.domain.Item, Book, cz.vhromada.bookcase.domain.Book>() {

    override fun getFacade(): MovableChildFacade<Item, Book> {
        return ItemFacadeImpl(service, mapper, parentMovableValidator, childMovableValidator)
    }

    override fun newParentEntity(id: Int): Book {
        return BookUtils.newBook(id)
    }

    override fun newParentDomain(id: Int): cz.vhromada.bookcase.domain.Book {
        return BookUtils.newBookWithItems(id)
    }

    override fun newParentDomainWithChildren(id: Int, children: List<cz.vhromada.bookcase.domain.Item>): cz.vhromada.bookcase.domain.Book {
        return newParentDomain(id)
                .copy(items = children)
    }

    override fun newChildEntity(id: Int?): Item {
        return ItemUtils.newItem(id)
    }

    override fun newChildDomain(id: Int?): cz.vhromada.bookcase.domain.Item {
        return ItemUtils.newItemDomain(id)
    }

    override fun getParentRemovedData(parent: cz.vhromada.bookcase.domain.Book, child: cz.vhromada.bookcase.domain.Item): cz.vhromada.bookcase.domain.Book {
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

    override fun anyChildDomain(): cz.vhromada.bookcase.domain.Item {
        return any()
    }

    override fun argumentCaptorParentDomain(): KArgumentCaptor<cz.vhromada.bookcase.domain.Book> {
        return argumentCaptor()
    }

    override fun assertParentDeepEquals(expected: cz.vhromada.bookcase.domain.Book, actual: cz.vhromada.bookcase.domain.Book) {
        BookUtils.assertBookDeepEquals(expected, actual)
    }

}

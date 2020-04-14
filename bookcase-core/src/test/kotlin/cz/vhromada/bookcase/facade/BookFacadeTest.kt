package cz.vhromada.bookcase.facade

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.facade.impl.BookFacadeImpl
import cz.vhromada.bookcase.utils.BookUtils
import cz.vhromada.common.facade.MovableParentFacade
import cz.vhromada.common.test.facade.MovableParentFacadeTest

/**
 * A class represents test for class [BookFacade].
 *
 * @author Vladimir Hromada
 */
class BookFacadeTest : MovableParentFacadeTest<Book, cz.vhromada.bookcase.domain.Book>() {

    override fun initUpdateMock(domain: cz.vhromada.bookcase.domain.Book) {
        super.initUpdateMock(domain)

        whenever(service.get(any())).thenReturn(domain)
    }

    override fun getFacade(): MovableParentFacade<Book> {
        return BookFacadeImpl(service, accountProvider, timeProvider, mapper, validator)
    }

    override fun newEntity(id: Int?): Book {
        return BookUtils.newBook(id)
    }

    override fun newDomain(id: Int?): cz.vhromada.bookcase.domain.Book {
        return BookUtils.newBookDomain(id)
    }

    override fun anyEntity(): Book {
        return any()
    }

    override fun anyDomain(): cz.vhromada.bookcase.domain.Book {
        return any()
    }

}

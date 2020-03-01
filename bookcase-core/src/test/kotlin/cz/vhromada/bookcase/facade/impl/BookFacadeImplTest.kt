package cz.vhromada.bookcase.facade.impl

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.utils.BookUtils
import cz.vhromada.common.facade.MovableParentFacade
import cz.vhromada.common.test.facade.MovableParentFacadeTest

/**
 * A class represents test for class [BookFacadeImpl].
 *
 * @author Vladimir Hromada
 */
class BookFacadeImplTest : MovableParentFacadeTest<Book, cz.vhromada.bookcase.domain.Book>() {

    override fun initUpdateMock(domain: cz.vhromada.bookcase.domain.Book) {
        super.initUpdateMock(domain)

        whenever(service.get(any())).thenReturn(domain)
    }

    override fun verifyUpdateMock(entity: Book, domain: cz.vhromada.bookcase.domain.Book) {
        super.verifyUpdateMock(entity, domain)

        verify(service).get(entity.id!!)
    }

    override fun getFacade(): MovableParentFacade<Book> {
        return BookFacadeImpl(service, mapper, validator)
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

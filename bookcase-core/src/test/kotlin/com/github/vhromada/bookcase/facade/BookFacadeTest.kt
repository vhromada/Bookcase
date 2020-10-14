package com.github.vhromada.bookcase.facade

import com.github.vhromada.bookcase.entity.Book
import com.github.vhromada.bookcase.facade.impl.BookFacadeImpl
import com.github.vhromada.bookcase.utils.BookUtils
import com.github.vhromada.common.facade.MovableParentFacade
import com.github.vhromada.common.test.facade.MovableParentFacadeTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import java.util.Optional

/**
 * A class represents test for class [BookFacade].
 *
 * @author Vladimir Hromada
 */
class BookFacadeTest : MovableParentFacadeTest<Book, com.github.vhromada.bookcase.domain.Book>() {

    override fun initUpdateMock(domain: com.github.vhromada.bookcase.domain.Book) {
        super.initUpdateMock(domain)

        whenever(service.get(any())).thenReturn(Optional.of(domain))
    }

    override fun getFacade(): MovableParentFacade<Book> {
        return BookFacadeImpl(service, accountProvider, timeProvider, mapper, validator)
    }

    override fun newEntity(id: Int?): Book {
        return BookUtils.newBook(id)
    }

    override fun newDomain(id: Int?): com.github.vhromada.bookcase.domain.Book {
        return BookUtils.newBookDomain(id)
    }

    override fun anyEntity(): Book {
        return any()
    }

    override fun anyDomain(): com.github.vhromada.bookcase.domain.Book {
        return any()
    }

}

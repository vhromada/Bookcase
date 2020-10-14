package com.github.vhromada.bookcase.facade.impl

import com.github.vhromada.bookcase.entity.Book
import com.github.vhromada.bookcase.facade.BookFacade
import com.github.vhromada.common.facade.AbstractMovableParentFacade
import com.github.vhromada.common.mapper.Mapper
import com.github.vhromada.common.provider.AccountProvider
import com.github.vhromada.common.provider.TimeProvider
import com.github.vhromada.common.service.MovableService
import com.github.vhromada.common.validator.MovableValidator
import org.springframework.stereotype.Component

/**
 * A facade represents implementation of facade for books.
 *
 * @author Vladimir Hromada
 */
@Component("bookFacade")
class BookFacadeImpl(
        bookService: MovableService<com.github.vhromada.bookcase.domain.Book>,
        accountProvider: AccountProvider,
        timeProvider: TimeProvider,
        mapper: Mapper<Book, com.github.vhromada.bookcase.domain.Book>,
        bookValidator: MovableValidator<Book>) : AbstractMovableParentFacade<Book, com.github.vhromada.bookcase.domain.Book>(bookService, accountProvider, timeProvider, mapper, bookValidator), BookFacade {

    override fun getDataForUpdate(data: Book): com.github.vhromada.bookcase.domain.Book {
        return super.getDataForUpdate(data).copy(items = service.get(data.id!!).get().items)
    }

}

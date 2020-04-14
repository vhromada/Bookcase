package cz.vhromada.bookcase.facade.impl

import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.facade.BookFacade
import cz.vhromada.common.facade.AbstractMovableParentFacade
import cz.vhromada.common.mapper.Mapper
import cz.vhromada.common.provider.AccountProvider
import cz.vhromada.common.provider.TimeProvider
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.validator.MovableValidator
import org.springframework.stereotype.Component

/**
 * A facade represents implementation of facade for books.
 *
 * @author Vladimir Hromada
 */
@Component("bookFacade")
class BookFacadeImpl(
        bookService: MovableService<cz.vhromada.bookcase.domain.Book>,
        accountProvider: AccountProvider,
        timeProvider: TimeProvider,
        mapper: Mapper<Book, cz.vhromada.bookcase.domain.Book>,
        bookValidator: MovableValidator<Book>) : AbstractMovableParentFacade<Book, cz.vhromada.bookcase.domain.Book>(bookService, accountProvider, timeProvider, mapper, bookValidator), BookFacade {

    override fun getDataForUpdate(data: Book): cz.vhromada.bookcase.domain.Book {
        return super.getDataForUpdate(data).copy(items = service.get(data.id!!)!!.items)
    }

}

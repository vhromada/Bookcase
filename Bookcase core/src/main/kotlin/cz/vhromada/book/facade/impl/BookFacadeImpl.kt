package cz.vhromada.book.facade.impl

import cz.vhromada.book.entity.Book
import cz.vhromada.book.facade.BookFacade
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.validator.BookcaseValidator
import cz.vhromada.converter.Converter
import org.springframework.stereotype.Component

/**
 * A facade represents implementation of facade for books.
 *
 * @author Vladimir Hromada
 */
@Component("bookFacade")
class BookFacadeImpl(bookcaseService: BookcaseService<cz.vhromada.book.domain.Book>, bookcaseValidator: BookcaseValidator<Book>, converter: Converter) : AbstractBookcaseFacade<Book, cz.vhromada.book.domain.Book>(bookcaseService, bookcaseValidator, converter), BookFacade {

    override val entityClass = Book::class.java

    override val domainClass = cz.vhromada.book.domain.Book::class.java

}

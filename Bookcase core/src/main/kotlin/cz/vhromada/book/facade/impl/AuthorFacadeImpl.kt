package cz.vhromada.book.facade.impl

import cz.vhromada.book.entity.Author
import cz.vhromada.book.facade.AuthorFacade
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.validator.BookcaseValidator
import cz.vhromada.converter.Converter
import org.springframework.stereotype.Component

/**
 * A facade represents implementation of facade for authors.
 *
 * @author Vladimir Hromada
 */
@Component("authorFacade")
class AuthorFacadeImpl(bookcaseService: BookcaseService<cz.vhromada.book.domain.Author>, bookcaseValidator: BookcaseValidator<Author>, converter: Converter) : AbstractBookcaseFacade<Author, cz.vhromada.book.domain.Author>(bookcaseService, bookcaseValidator, converter), AuthorFacade {

    override val entityClass = Author::class.java

    override val domainClass = cz.vhromada.book.domain.Author::class.java

}

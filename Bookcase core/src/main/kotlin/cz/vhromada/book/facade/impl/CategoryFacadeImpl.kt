package cz.vhromada.book.facade.impl

import cz.vhromada.book.entity.Category
import cz.vhromada.book.facade.CategoryFacade
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.validator.BookcaseValidator
import cz.vhromada.converter.Converter
import org.springframework.stereotype.Component

/**
 * A facade represents implementation of facade for categories.
 *
 * @author Vladimir Hromada
 */
@Component("categoryFacade")
class CategoryFacadeImpl(bookcaseService: BookcaseService<cz.vhromada.book.domain.Category>, bookcaseValidator: BookcaseValidator<Category>, converter: Converter) : AbstractBookcaseFacade<Category, cz.vhromada.book.domain.Category>(bookcaseService, bookcaseValidator, converter), CategoryFacade {

    override val entityClass = Category::class.java

    override val domainClass = cz.vhromada.book.domain.Category::class.java

}

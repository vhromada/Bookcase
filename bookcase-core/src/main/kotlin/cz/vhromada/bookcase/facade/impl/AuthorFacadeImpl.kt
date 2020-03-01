package cz.vhromada.bookcase.facade.impl

import cz.vhromada.bookcase.entity.Author
import cz.vhromada.bookcase.facade.AuthorFacade
import cz.vhromada.common.facade.AbstractMovableParentFacade
import cz.vhromada.common.mapper.Mapper
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.validator.MovableValidator
import org.springframework.stereotype.Component

/**
 * A facade represents implementation of facade for authors.
 *
 * @author Vladimir Hromada
 */
@Component("authorFacade")
class AuthorFacadeImpl(
        authorService: MovableService<cz.vhromada.bookcase.domain.Author>,
        mapper: Mapper<Author, cz.vhromada.bookcase.domain.Author>,
        authorValidator: MovableValidator<Author>) : AbstractMovableParentFacade<Author, cz.vhromada.bookcase.domain.Author>(authorService, mapper, authorValidator), AuthorFacade

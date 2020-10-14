package com.github.vhromada.bookcase.facade.impl

import com.github.vhromada.bookcase.entity.Author
import com.github.vhromada.bookcase.facade.AuthorFacade
import com.github.vhromada.common.facade.AbstractMovableParentFacade
import com.github.vhromada.common.mapper.Mapper
import com.github.vhromada.common.provider.AccountProvider
import com.github.vhromada.common.provider.TimeProvider
import com.github.vhromada.common.service.MovableService
import com.github.vhromada.common.validator.MovableValidator
import org.springframework.stereotype.Component

/**
 * A facade represents implementation of facade for authors.
 *
 * @author Vladimir Hromada
 */
@Component("authorFacade")
class AuthorFacadeImpl(
        authorService: MovableService<com.github.vhromada.bookcase.domain.Author>,
        accountProvider: AccountProvider,
        timeProvider: TimeProvider,
        mapper: Mapper<Author, com.github.vhromada.bookcase.domain.Author>,
        authorValidator: MovableValidator<Author>
) : AbstractMovableParentFacade<Author, com.github.vhromada.bookcase.domain.Author>(authorService, accountProvider, timeProvider, mapper, authorValidator), AuthorFacade

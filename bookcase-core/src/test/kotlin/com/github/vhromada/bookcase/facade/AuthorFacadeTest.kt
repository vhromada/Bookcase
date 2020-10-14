package com.github.vhromada.bookcase.facade

import com.github.vhromada.bookcase.entity.Author
import com.github.vhromada.bookcase.facade.impl.AuthorFacadeImpl
import com.github.vhromada.bookcase.utils.AuthorUtils
import com.github.vhromada.common.facade.MovableParentFacade
import com.github.vhromada.common.test.facade.MovableParentFacadeTest
import com.nhaarman.mockitokotlin2.any

/**
 * A class represents test for class [AuthorFacade].
 *
 * @author Vladimir Hromada
 */
class AuthorFacadeTest : MovableParentFacadeTest<Author, com.github.vhromada.bookcase.domain.Author>() {

    override fun getFacade(): MovableParentFacade<Author> {
        return AuthorFacadeImpl(service, accountProvider, timeProvider, mapper, validator)
    }

    override fun newEntity(id: Int?): Author {
        return AuthorUtils.newAuthor(id)
    }

    override fun newDomain(id: Int?): com.github.vhromada.bookcase.domain.Author {
        return AuthorUtils.newAuthorDomain(id)
    }

    override fun anyEntity(): Author {
        return any()
    }

    override fun anyDomain(): com.github.vhromada.bookcase.domain.Author {
        return any()
    }

}

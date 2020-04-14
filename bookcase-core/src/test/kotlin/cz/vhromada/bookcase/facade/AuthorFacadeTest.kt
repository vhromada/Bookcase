package cz.vhromada.bookcase.facade

import com.nhaarman.mockitokotlin2.any
import cz.vhromada.bookcase.entity.Author
import cz.vhromada.bookcase.facade.impl.AuthorFacadeImpl
import cz.vhromada.bookcase.utils.AuthorUtils
import cz.vhromada.common.facade.MovableParentFacade
import cz.vhromada.common.test.facade.MovableParentFacadeTest

/**
 * A class represents test for class [AuthorFacade].
 *
 * @author Vladimir Hromada
 */
class AuthorFacadeTest : MovableParentFacadeTest<Author, cz.vhromada.bookcase.domain.Author>() {

    override fun getFacade(): MovableParentFacade<Author> {
        return AuthorFacadeImpl(service, accountProvider, timeProvider, mapper, validator)
    }

    override fun newEntity(id: Int?): Author {
        return AuthorUtils.newAuthor(id)
    }

    override fun newDomain(id: Int?): cz.vhromada.bookcase.domain.Author {
        return AuthorUtils.newAuthorDomain(id)
    }

    override fun anyEntity(): Author {
        return any()
    }

    override fun anyDomain(): cz.vhromada.bookcase.domain.Author {
        return any()
    }

}

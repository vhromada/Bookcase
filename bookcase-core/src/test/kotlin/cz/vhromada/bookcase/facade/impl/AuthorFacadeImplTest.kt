package cz.vhromada.bookcase.facade.impl

import com.nhaarman.mockitokotlin2.any
import cz.vhromada.bookcase.entity.Author
import cz.vhromada.bookcase.utils.AuthorUtils
import cz.vhromada.common.facade.MovableParentFacade
import cz.vhromada.common.test.facade.MovableParentFacadeTest

/**
 * A class represents test for class [AuthorFacadeImpl].
 *
 * @author Vladimir Hromada
 */
class AuthorFacadeImplTest : MovableParentFacadeTest<Author, cz.vhromada.bookcase.domain.Author>() {

    override fun getFacade(): MovableParentFacade<Author> {
        return AuthorFacadeImpl(service, mapper, validator)
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

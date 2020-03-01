package cz.vhromada.bookcase.facade.impl

import com.nhaarman.mockitokotlin2.any
import cz.vhromada.bookcase.entity.Category
import cz.vhromada.bookcase.utils.CategoryUtils
import cz.vhromada.common.facade.MovableParentFacade
import cz.vhromada.common.test.facade.MovableParentFacadeTest

/**
 * A class represents test for class [CategoryFacadeImpl].
 *
 * @author Vladimir Hromada
 */
class CategoryFacadeImplTest : MovableParentFacadeTest<Category, cz.vhromada.bookcase.domain.Category>() {

    override fun getFacade(): MovableParentFacade<Category> {
        return CategoryFacadeImpl(service, mapper, validator)
    }

    override fun newEntity(id: Int?): Category {
        return CategoryUtils.newCategory(id)
    }

    override fun newDomain(id: Int?): cz.vhromada.bookcase.domain.Category {
        return CategoryUtils.newCategoryDomain(id)
    }

    override fun anyEntity(): Category {
        return any()
    }

    override fun anyDomain(): cz.vhromada.bookcase.domain.Category {
        return any()
    }

}

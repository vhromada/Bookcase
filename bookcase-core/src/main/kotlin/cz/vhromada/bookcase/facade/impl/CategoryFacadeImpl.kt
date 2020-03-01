package cz.vhromada.bookcase.facade.impl

import cz.vhromada.bookcase.entity.Category
import cz.vhromada.bookcase.facade.CategoryFacade
import cz.vhromada.common.facade.AbstractMovableParentFacade
import cz.vhromada.common.mapper.Mapper
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.validator.MovableValidator
import org.springframework.stereotype.Component

/**
 * A facade represents implementation of facade for categories.
 *
 * @author Vladimir Hromada
 */
@Component("categoryFacade")
class CategoryFacadeImpl(
        categoryService: MovableService<cz.vhromada.bookcase.domain.Category>,
        mapper: Mapper<Category, cz.vhromada.bookcase.domain.Category>,
        categoryValidator: MovableValidator<Category>) : AbstractMovableParentFacade<Category, cz.vhromada.bookcase.domain.Category>(categoryService, mapper, categoryValidator), CategoryFacade

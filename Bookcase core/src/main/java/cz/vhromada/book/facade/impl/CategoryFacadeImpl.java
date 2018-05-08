package cz.vhromada.book.facade.impl;

import cz.vhromada.book.entity.Category;
import cz.vhromada.book.facade.CategoryFacade;
import cz.vhromada.common.facade.AbstractMovableParentFacade;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.validator.MovableValidator;
import cz.vhromada.converter.Converter;

import org.springframework.stereotype.Component;

/**
 * A facade represents implementation of facade for categories.
 *
 * @author Vladimir Hromada
 */
@Component("categoryFacade")
public class CategoryFacadeImpl extends AbstractMovableParentFacade<Category, cz.vhromada.book.domain.Category> implements CategoryFacade {

    /**
     * Creates a new instance of CategoryFacadeImpl.
     *
     * @param categoryService   service for categories
     * @param converter         converter
     * @param categoryValidator validator for category
     * @throws IllegalArgumentException if service for categories is null
     *                                  or converter is null
     *                                  or validator for category is null
     */
    public CategoryFacadeImpl(final MovableService<cz.vhromada.book.domain.Category> categoryService, final Converter converter,
        final MovableValidator<Category> categoryValidator) {
        super(categoryService, converter, categoryValidator);
    }

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }

    @Override
    protected Class<cz.vhromada.book.domain.Category> getDomainClass() {
        return cz.vhromada.book.domain.Category.class;
    }

}

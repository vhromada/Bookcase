package cz.vhromada.book.facade.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cz.vhromada.book.entity.Category;
import cz.vhromada.book.utils.CategoryUtils;
import cz.vhromada.common.facade.MovableParentFacade;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.test.facade.MovableParentFacadeTest;
import cz.vhromada.common.validator.MovableValidator;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;

/**
 * A class represents test for class {@link CategoryFacadeImpl}.
 *
 * @author Vladimir Hromada
 */
class CategoryFacadeImplTest extends MovableParentFacadeTest<Category, cz.vhromada.book.domain.Category> {

    /**
     * Test method for {@link CategoryFacadeImpl#CategoryFacadeImpl(MovableService, Converter, MovableValidator)} with null service for categories.
     */
    @Test
    void constructor_NullCategoryService() {
        assertThatThrownBy(() -> new CategoryFacadeImpl(null, getConverter(), getMovableValidator())).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link CategoryFacadeImpl#CategoryFacadeImpl(MovableService, Converter, MovableValidator)} with null converter.
     */
    @Test
    void constructor_NullConverter() {
        assertThatThrownBy(() -> new CategoryFacadeImpl(getMovableService(), null, getMovableValidator())).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link CategoryFacadeImpl#CategoryFacadeImpl(MovableService, Converter, MovableValidator)} with null validator for category.
     */
    @Test
    void constructor_NullCategoryValidator() {
        assertThatThrownBy(() -> new CategoryFacadeImpl(getMovableService(), getConverter(), null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Override
    protected MovableParentFacade<Category> getMovableParentFacade() {
        return new CategoryFacadeImpl(getMovableService(), getConverter(), getMovableValidator());
    }

    @Override
    protected Category newEntity(final Integer id) {
        return CategoryUtils.newCategory(id);
    }

    @Override
    protected cz.vhromada.book.domain.Category newDomain(final Integer id) {
        return CategoryUtils.newCategoryDomain(id);
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

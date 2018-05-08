package cz.vhromada.book.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cz.vhromada.book.domain.Category;
import cz.vhromada.book.repository.CategoryRepository;
import cz.vhromada.book.utils.CategoryUtils;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.test.service.MovableServiceTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.cache.Cache;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A class represents test for class {@link CategoryService}.
 *
 * @author Vladimir Hromada
 */
class CategoryServiceTest extends MovableServiceTest<Category> {

    /**
     * Instance of {@link CategoryRepository}
     */
    @Mock
    private CategoryRepository categoryRepository;

    /**
     * Test method for {@link CategoryService#CategoryService(CategoryRepository, Cache)} with null repository for categories.
     */
    @Test
    void constructor_NullCategoryRepository() {
        assertThatThrownBy(() -> new CategoryService(null, getCache())).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link CategoryService#CategoryService(CategoryRepository, Cache)} with null cache.
     */
    @Test
    void constructor_NullCache() {
        assertThatThrownBy(() -> new CategoryService(categoryRepository, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Override
    protected JpaRepository<Category, Integer> getRepository() {
        return categoryRepository;
    }

    @Override
    protected MovableService<Category> getMovableService() {
        return new CategoryService(categoryRepository, getCache());
    }

    @Override
    protected String getCacheKey() {
        return "categories";
    }

    @Override
    protected Category getItem1() {
        return CategoryUtils.newCategoryDomain(1);
    }

    @Override
    protected Category getItem2() {
        return CategoryUtils.newCategoryDomain(2);
    }

    @Override
    protected Category getAddItem() {
        return CategoryUtils.newCategoryDomain(null);
    }

    @Override
    protected Category getCopyItem() {
        final Category category = CategoryUtils.newCategoryDomain(null);
        category.setPosition(0);

        return category;
    }

    @Override
    protected Class<Category> getItemClass() {
        return Category.class;
    }

    @Override
    protected void assertDataDeepEquals(final Category expected, final Category actual) {
        CategoryUtils.assertCategoryDeepEquals(expected, actual);
    }

}

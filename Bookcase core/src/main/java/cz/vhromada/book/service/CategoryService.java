package cz.vhromada.book.service;

import cz.vhromada.book.domain.Category;
import cz.vhromada.book.repository.CategoryRepository;
import cz.vhromada.common.service.AbstractMovableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

/**
 * A class represents implementation of service for categories.
 *
 * @author Vladimir Hromada
 */
@Component("categoryService")
public class CategoryService extends AbstractMovableService<Category> {

    /**
     * Creates a new instance of CategoryService.
     *
     * @param categoryRepository repository for categories
     * @param cache              cache
     * @throws IllegalArgumentException if repository for categories is null
     *                                  or cache is null
     */
    @Autowired
    public CategoryService(final CategoryRepository categoryRepository, @Value("#{cacheManager.getCache('bookcaseCache')}") final Cache cache) {
        super(categoryRepository, cache, "categories");
    }

    @Override
    protected Category getCopy(final Category data) {
        final Category newCategory = new Category();
        newCategory.setName(data.getName());
        newCategory.setPosition(data.getPosition());

        return newCategory;
    }

}

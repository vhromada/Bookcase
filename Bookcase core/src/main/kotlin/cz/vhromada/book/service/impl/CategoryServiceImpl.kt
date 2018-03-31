package cz.vhromada.book.service.impl

import cz.vhromada.book.domain.Category
import cz.vhromada.book.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.stereotype.Component

/**
 * A class represents implementation of service for categories.
 *
 * @author Vladimir Hromada
 */
@Component("categoryService")
class CategoryServiceImpl(categoryRepository: CategoryRepository, @Value("#{cacheManager.getCache('bookcaseCache')}") cache: Cache) : AbstractBookcaseService<Category>(categoryRepository, cache, "categories") {

    override fun getCopy(data: Category): Category {
        return Category(null, data.name, data.position);
    }

}

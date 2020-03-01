package cz.vhromada.bookcase.service

import cz.vhromada.bookcase.domain.Category
import cz.vhromada.bookcase.repository.CategoryRepository
import cz.vhromada.common.service.AbstractMovableService
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.stereotype.Component

/**
 * A class represents of service for categories.
 *
 * @author Vladimir Hromada
 */
@Component("categoryService")
class CategoryService(
        categoryRepository: CategoryRepository,
        @Value("#{cacheManager.getCache('bookcaseCache')}") cache: Cache) : AbstractMovableService<Category>(categoryRepository, cache, "categories") {

    override fun getCopy(data: Category): Category {
        return data.copy(id = null)
    }

}

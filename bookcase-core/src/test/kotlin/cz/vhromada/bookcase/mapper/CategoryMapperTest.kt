package cz.vhromada.bookcase.mapper

import cz.vhromada.bookcase.CoreTestConfiguration
import cz.vhromada.bookcase.entity.Category
import cz.vhromada.bookcase.utils.CategoryUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [cz.vhromada.bookcase.domain.Category] and [Category].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CoreTestConfiguration::class])
class CategoryMapperTest {

    /**
     * Instance of [CategoryMapper]
     */
    @Autowired
    private lateinit var mapper: CategoryMapper

    /**
     * Test method for [CategoryMapper.map].
     */
    @Test
    fun map() {
        val category = CategoryUtils.newCategory(1)
        val categoryDomain = mapper.map(category)

        CategoryUtils.assertCategoryDeepEquals(category, categoryDomain)
    }

    /**
     * Test method for [CategoryMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val categoryDomain = CategoryUtils.newCategoryDomain(1)
        val category = mapper.mapBack(categoryDomain)

        CategoryUtils.assertCategoryDeepEquals(category, categoryDomain)
    }

}

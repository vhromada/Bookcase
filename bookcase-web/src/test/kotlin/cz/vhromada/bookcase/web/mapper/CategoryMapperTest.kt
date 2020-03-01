package cz.vhromada.bookcase.web.mapper

import cz.vhromada.bookcase.entity.Category
import cz.vhromada.bookcase.web.BookcaseMapperTestConfiguration
import cz.vhromada.bookcase.web.common.CategoryUtils
import cz.vhromada.bookcase.web.fo.CategoryFO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [Category] and [CategoryFO].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [BookcaseMapperTestConfiguration::class])
class CategoryMapperTest {

    /**
     * Mapper for categories
     */
    @Autowired
    private lateinit var mapper: CategoryMapper

    /**
     * Test method for [CategoryMapper.map].
     */
    @Test
    fun map() {
        val category = CategoryUtils.getCategory()

        val categoryFO = mapper.map(category)

        CategoryUtils.assertCategoryDeepEquals(categoryFO, category)
    }

    /**
     * Test method for [CategoryMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val categoryFO = CategoryUtils.getCategoryFO()

        val category = mapper.mapBack(categoryFO)

        CategoryUtils.assertCategoryDeepEquals(categoryFO, category)
    }

}

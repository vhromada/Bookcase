package cz.vhromada.book.converter

import cz.vhromada.book.CoreTestConfiguration
import cz.vhromada.book.domain.Category
import cz.vhromada.book.utils.CategoryUtils
import cz.vhromada.converter.Converter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for class [CategoryConverter] and [DomainCategoryConverter].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CoreTestConfiguration::class])
class CategoryConverterTest {

    /**
     * Instance of [Converter]
     */
    @Autowired
    private val converter: Converter? = null

    /**
     * Checks autowired fields.
     */
    @BeforeEach
    fun setUp() {
        assertThat(converter).isNotNull
    }

    /**
     * Test method for [CategoryConverter.convert].
     */
    @Test
    fun convert_Entity() {
        val category = CategoryUtils.newCategory(1)
        val categoryDomain = converter!!.convert(category, Category::class.java)

        CategoryUtils.assertCategoryDeepEquals(categoryDomain, category)
    }

    /**
     * Test method for [DomainCategoryConverter.convert].
     */
    @Test
    fun convert_Domain() {
        val categoryDomain = CategoryUtils.newCategoryDomain(1)
        val category = converter!!.convert(categoryDomain, cz.vhromada.book.entity.Category::class.java)

        CategoryUtils.assertCategoryDeepEquals(categoryDomain, category)
    }

}

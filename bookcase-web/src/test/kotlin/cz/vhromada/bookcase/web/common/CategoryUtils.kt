package cz.vhromada.bookcase.web.common

import cz.vhromada.bookcase.entity.Category
import cz.vhromada.bookcase.web.fo.CategoryFO
import org.assertj.core.api.Assertions
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for categories.
 *
 * @author Vladimir Hromada
 */
object CategoryUtils {

    /**
     * Returns FO for category.
     *
     * @return FO for category
     */
    fun getCategoryFO(): CategoryFO {
        return CategoryFO(
                id = 1,
                name = "Name",
                position = 0)
    }

    /**
     * Returns category.
     *
     * @return category
     */
    fun getCategory(): Category {
        return Category(
                id = 1,
                name = "Name",
                position = 0)
    }

    /**
     * Asserts category deep equals.
     *
     * @param expected expected FO for category
     * @param actual   actual category
     */
    fun assertCategoryDeepEquals(expected: CategoryFO?, actual: Category?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(actual!!.id).isEqualTo(expected!!.id)
            it.assertThat(actual.name).isEqualTo(expected.name)
            it.assertThat(actual.position).isEqualTo(expected.position)
        }
    }

    /**
     * Asserts categories deep equals.
     *
     * @param expected expected list of categories
     * @param actual   actual list of categories
     */
    fun assertCategoriesDeepEquals(expected: List<Category?>?, actual: List<Int?>?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        Assertions.assertThat(actual!!.size).isEqualTo(expected!!.size)
        for (i in expected.indices) {
            assertCategoryDeepEquals(expected[i], actual[i])
        }
    }

    /**
     * Asserts category deep equals.
     *
     * @param expected expected category
     * @param actual   actual category
     */
    private fun assertCategoryDeepEquals(expected: Category?, actual: Int?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly { it.assertThat(actual).isEqualTo(expected!!.id) }
    }

}

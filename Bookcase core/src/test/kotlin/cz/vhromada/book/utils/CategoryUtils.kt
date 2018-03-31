package cz.vhromada.book.utils

import cz.vhromada.book.domain.Category
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import javax.persistence.EntityManager

/**
 * An object represents utility class for categories.
 *
 * @author Vladimir Hromada
 */
object CategoryUtils {

    /**
     * Count of categories
     */
    const val CATEGORIES_COUNT = 3

    /**
     * Returns category.
     *
     * @param id ID
     * @return category
     */
    fun newCategoryDomain(id: Int?): Category {
        val position = if (id == null) {
            0
        } else {
            id - 1
        }
        return newCategoryDomain(id, position)
    }

    /**
     * Returns category.
     *
     * @param id       ID
     * @param position position
     * @return category
     */
    fun newCategoryDomain(id: Int?, position: Int): Category {
        return Category(id, "Name", position)
    }

    /**
     * Returns categories.
     *
     * @return categories
     */
    fun getCategories(): List<Category> {
        return (0 until CATEGORIES_COUNT).map { getCategory(it + 1) }
    }

    /**
     * Returns category for index.
     *
     * @param index index
     * @return category for index
     */
    fun getCategory(index: Int): Category {
        return Category(index, "Category $index Name", index - 1)
    }

    /**
     * Returns category.
     *
     * @param entityManager entity manager
     * @param id            category's ID
     * @return category
     */
    fun getCategory(entityManager: EntityManager, id: Int): Category? {
        return entityManager.find(Category::class.java, id)
    }

    /**
     * Returns count of categories.
     *
     * @param entityManager entity manager
     * @return count of categories
     */
    fun getCategoriesCount(entityManager: EntityManager): Int {
        return entityManager.createQuery("SELECT COUNT(c.id) FROM Category c", java.lang.Long::class.java).singleResult.toInt()
    }

    /**
     * Asserts categories deep equals.
     *
     * @param expected expected categories
     * @param actual   actual categories
     */
    fun assertCategoriesDeepEquals(expected: List<Category>?, actual: List<Category>?) {
        assertSoftly { softly ->
            softly.assertThat(expected).isNotNull
            softly.assertThat(actual).isNotNull
        }

        assertThat(expected!!.size).isEqualTo(actual!!.size)
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
    fun assertCategoryDeepEquals(expected: Category?, actual: Category?) {
        assertSoftly { softly ->
            softly.assertThat(expected).isNotNull
            softly.assertThat(actual).isNotNull
        }

        assertSoftly { softly ->
            softly.assertThat(actual!!.id).isEqualTo(expected!!.id)
            softly.assertThat(actual.name).isEqualTo(expected.name)
            softly.assertThat(actual.position).isEqualTo(expected.position)
        }
    }

}

package cz.vhromada.bookcase.utils

import cz.vhromada.bookcase.entity.Category
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import javax.persistence.EntityManager

/**
 * Updates category fields.
 *
 * @return updated category
 */
fun cz.vhromada.bookcase.domain.Category.updated(): cz.vhromada.bookcase.domain.Category {
    return copy(name = "Name", audit = AuditUtils.newAudit())
}

/**
 * Updates category fields.
 *
 * @return updated category
 */
fun Category.updated(): Category {
    return copy(name = "Name")
}

/**
 * A class represents utility class for categories.
 *
 * @author Vladimir Hromada
 */
object CategoryUtils {

    /**
     * Count of categories
     */
    const val CATEGORIES_COUNT = 3

    /**
     * Position
     */
    const val POSITION = 10

    /**
     * Category name
     */
    private const val CATEGORY = "Category "

    /**
     * Returns categories.
     *
     * @return categories
     */
    fun getCategories(): List<cz.vhromada.bookcase.domain.Category> {
        val categories = mutableListOf<cz.vhromada.bookcase.domain.Category>()
        for (i in 0 until CATEGORIES_COUNT) {
            categories.add(getCategoryDomain(i + 1))
        }

        return categories
    }

    /**
     * Returns category.
     *
     * @param id ID
     * @return category
     */
    fun newCategoryDomain(id: Int?): cz.vhromada.bookcase.domain.Category {
        return cz.vhromada.bookcase.domain.Category(id = id, name = "", position = if (id == null) null else id - 1, audit = null)
                .updated()
    }

    /**
     * Returns category.
     *
     * @param id ID
     * @return category
     */
    fun newCategory(id: Int?): Category {
        return Category(id = id, name = "", position = if (id == null) null else id - 1)
                .updated()
    }

    /**
     * Returns category for index.
     *
     * @param index index
     * @return category for index
     */
    fun getCategoryDomain(index: Int): cz.vhromada.bookcase.domain.Category {
        return cz.vhromada.bookcase.domain.Category(
                id = index,
                name = "$CATEGORY$index Name",
                position = index - 1,
                audit = AuditUtils.getAudit())
    }

    /**
     * Returns category for index.
     *
     * @param index index
     * @return category for index
     */
    fun getCategory(index: Int): Category {
        return Category(
                id = index,
                name = "$CATEGORY$index Name",
                position = index - 1)
    }

    /**
     * Returns category.
     *
     * @param entityManager entity manager
     * @param id            category ID
     * @return category
     */
    fun getCategory(entityManager: EntityManager, id: Int): cz.vhromada.bookcase.domain.Category? {
        return entityManager.find(cz.vhromada.bookcase.domain.Category::class.java, id)
    }

    /**
     * Returns category with updated fields.
     *
     * @param entityManager entity manager
     * @param id            category ID
     * @return category with updated fields
     */
    fun updateCategory(entityManager: EntityManager, id: Int): cz.vhromada.bookcase.domain.Category {
        return getCategory(entityManager, id)!!
                .updated()
                .copy(position = POSITION)
    }

    /**
     * Returns count of categories.
     *
     * @param entityManager entity manager
     * @return count of categories
     */
    @Suppress("CheckStyle")
    fun getCategoriesCount(entityManager: EntityManager): Int {
        return entityManager.createQuery("SELECT COUNT(c.id) FROM Category c", java.lang.Long::class.java).singleResult.toInt()
    }

    /**
     * Asserts categories deep equals.
     *
     * @param expected expected categories
     * @param actual   actual categories
     */
    fun assertCategoriesDeepEquals(expected: List<cz.vhromada.bookcase.domain.Category?>?, actual: List<cz.vhromada.bookcase.domain.Category?>?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertThat(expected!!.size).isEqualTo(actual!!.size)
        if (expected.isNotEmpty()) {
            for (i in expected.indices) {
                assertCategoryDeepEquals(expected[i], actual[i])
            }
        }
    }

    /**
     * Asserts category deep equals.
     *
     * @param expected expected category
     * @param actual   actual category
     */
    fun assertCategoryDeepEquals(expected: cz.vhromada.bookcase.domain.Category?, actual: cz.vhromada.bookcase.domain.Category?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(expected!!.id).isEqualTo(actual!!.id)
            it.assertThat(expected.name).isEqualTo(actual.name)
            it.assertThat(expected.position).isEqualTo(actual.position)
        }
    }

    /**
     * Asserts categories deep equals.
     *
     * @param expected expected list of category
     * @param actual   actual categories
     */
    fun assertCategoryListDeepEquals(expected: List<Category?>?, actual: List<cz.vhromada.bookcase.domain.Category?>?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertThat(expected!!.size).isEqualTo(actual!!.size)
        if (expected.isNotEmpty()) {
            for (i in expected.indices) {
                assertCategoryDeepEquals(expected[i], actual[i])
            }
        }
    }

    /**
     * Asserts category deep equals.
     *
     * @param expected expected category
     * @param actual   actual category
     */
    fun assertCategoryDeepEquals(expected: Category?, actual: cz.vhromada.bookcase.domain.Category?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(expected!!.id).isEqualTo(actual!!.id)
            it.assertThat(expected.name).isEqualTo(actual.name)
            it.assertThat(expected.position).isEqualTo(actual.position)
        }
    }

}

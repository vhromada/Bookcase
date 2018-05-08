package cz.vhromada.book.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import cz.vhromada.book.entity.Category;

/**
 * A class represents utility class for categories.
 *
 * @author Vladimir Hromada
 */
public final class CategoryUtils {

    /**
     * Count of categories
     */
    public static final int CATEGORIES_COUNT = 3;

    /**
     * ID
     */
    public static final int ID = 1;

    /**
     * Position
     */
    public static final int POSITION = 10;

    /**
     * Category name
     */
    private static final String CATEGORY = "Category ";

    /**
     * Creates a new instance of CategoryUtils.
     */
    private CategoryUtils() {
    }

    /**
     * Returns category.
     *
     * @param id ID
     * @return category
     */
    public static cz.vhromada.book.domain.Category newCategoryDomain(final Integer id) {
        final cz.vhromada.book.domain.Category category = new cz.vhromada.book.domain.Category();
        updateCategory(category);
        if (id != null) {
            category.setId(id);
            category.setPosition(id - 1);
        }

        return category;
    }

    /**
     * Updates category fields.
     *
     * @param category category
     */
    @SuppressWarnings("Duplicates")
    public static void updateCategory(final cz.vhromada.book.domain.Category category) {
        category.setName("Name");
    }

    /**
     * Returns category.
     *
     * @param id ID
     * @return category
     */
    public static Category newCategory(final Integer id) {
        final Category category = new Category();
        updateCategory(category);
        if (id != null) {
            category.setId(id);
            category.setPosition(id - 1);
        }

        return category;
    }

    /**
     * Updates category fields.
     *
     * @param category category
     */
    @SuppressWarnings("Duplicates")
    public static void updateCategory(final Category category) {
        category.setName("Name");
    }

    /**
     * Returns categories.
     *
     * @return categories
     */
    public static List<cz.vhromada.book.domain.Category> getCategories() {
        final List<cz.vhromada.book.domain.Category> categories = new ArrayList<>();
        for (int i = 0; i < CATEGORIES_COUNT; i++) {
            categories.add(getCategoryDomain(i + 1));
        }

        return categories;
    }

    /**
     * Returns category for index.
     *
     * @param index index
     * @return category for index
     */
    public static cz.vhromada.book.domain.Category getCategoryDomain(final int index) {
        final cz.vhromada.book.domain.Category category = new cz.vhromada.book.domain.Category();
        category.setId(index);
        category.setName(CATEGORY + index + " Name");
        category.setPosition(index - 1);

        return category;
    }

    /**
     * Returns category for index.
     *
     * @param index index
     * @return category for index
     */
    public static Category getCategory(final int index) {
        final Category category = new Category();
        category.setId(index);
        category.setName(CATEGORY + index + " Name");
        category.setPosition(index - 1);

        return category;
    }

    /**
     * Returns category.
     *
     * @param entityManager entity manager
     * @param id            category ID
     * @return category
     */
    public static cz.vhromada.book.domain.Category getCategory(final EntityManager entityManager, final int id) {
        return entityManager.find(cz.vhromada.book.domain.Category.class, id);
    }

    /**
     * Returns category with updated fields.
     *
     * @param entityManager entity manager
     * @param id            category ID
     * @return category with updated fields
     */
    public static cz.vhromada.book.domain.Category updateCategory(final EntityManager entityManager, final int id) {
        final cz.vhromada.book.domain.Category category = getCategory(entityManager, id);
        updateCategory(category);
        category.setPosition(POSITION);

        return category;
    }

    /**
     * Returns count of categories.
     *
     * @param entityManager entity manager
     * @return count of categories
     */
    public static int getCategoriesCount(final EntityManager entityManager) {
        return entityManager.createQuery("SELECT COUNT(c.id) FROM Category c", Long.class).getSingleResult().intValue();
    }

    /**
     * Asserts categories deep equals.
     *
     * @param expected expected categories
     * @param actual   actual categories
     */
    public static void assertCategoriesDeepEquals(final List<cz.vhromada.book.domain.Category> expected, final List<cz.vhromada.book.domain.Category> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(expected.size()).isEqualTo(actual.size());
        if (!expected.isEmpty()) {
            for (int i = 0; i < expected.size(); i++) {
                assertCategoryDeepEquals(expected.get(i), actual.get(i));
            }
        }
    }

    /**
     * Asserts category deep equals.
     *
     * @param expected expected category
     * @param actual   actual category
     */
    @SuppressWarnings("Duplicates")
    public static void assertCategoryDeepEquals(final cz.vhromada.book.domain.Category expected, final cz.vhromada.book.domain.Category actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(expected.getId()).isEqualTo(actual.getId());
            softly.assertThat(expected.getName()).isEqualTo(actual.getName());
            softly.assertThat(expected.getPosition()).isEqualTo(actual.getPosition());
        });
    }

    /**
     * Asserts categories deep equals.
     *
     * @param expected expected list of category
     * @param actual   actual categories
     */
    public static void assertCategoryListDeepEquals(final List<Category> expected, final List<cz.vhromada.book.domain.Category> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(expected.size()).isEqualTo(actual.size());
        if (!expected.isEmpty()) {
            for (int i = 0; i < expected.size(); i++) {
                assertCategoryDeepEquals(expected.get(i), actual.get(i));
            }
        }
    }

    /**
     * Asserts category deep equals.
     *
     * @param expected expected category
     * @param actual   actual category
     */
    @SuppressWarnings("Duplicates")
    public static void assertCategoryDeepEquals(final Category expected, final cz.vhromada.book.domain.Category actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(expected.getName()).isEqualTo(actual.getName());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
        });
    }

}

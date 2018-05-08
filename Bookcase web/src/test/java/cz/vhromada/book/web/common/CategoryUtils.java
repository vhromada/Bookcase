package cz.vhromada.book.web.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;

import cz.vhromada.book.entity.Category;
import cz.vhromada.book.web.fo.CategoryFO;

/**
 * A class represents utility class for categories.
 *
 * @author Vladimir Hromada
 */
public final class CategoryUtils {

    /**
     * Creates a new instance of CategoryUtils.
     */
    private CategoryUtils() {
    }

    /**
     * Returns FO for category.
     *
     * @return FO for category
     */
    public static CategoryFO getCategoryFO() {
        final CategoryFO category = new CategoryFO();
        category.setId(1);
        category.setName("Name");
        category.setPosition(0);

        return category;
    }

    /**
     * Returns category.
     *
     * @return category
     */
    public static Category getCategory() {
        final Category category = new Category();
        category.setId(1);
        category.setName("Name");
        category.setPosition(0);

        return category;
    }

    /**
     * Asserts category deep equals.
     *
     * @param expected expected FO for category
     * @param actual   actual category
     */
    public static void assertCategoryDeepEquals(final CategoryFO expected, final Category actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(actual.getName()).isEqualTo(expected.getName());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
        });
    }

    /**
     * Asserts categories deep equals.
     *
     * @param expected expected list of categories
     * @param actual   actual list of category
     */
    public static void assertCategoriesDeepEquals(final List<Integer> expected, final List<Category> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(actual.size()).isEqualTo(expected.size());
        for (int i = 0; i < expected.size(); i++) {
            assertCategoryDeepEquals(expected.get(i), actual.get(i));
        }
    }

    /**
     * Asserts category deep equals.
     *
     * @param expected expected category
     * @param actual   actual category
     */
    public static void assertCategoryDeepEquals(final Integer expected, final Category actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected);
            softly.assertThat(actual.getName()).isNull();
            softly.assertThat(actual.getPosition()).isNull();
        });
    }

    /**
     * Asserts categories deep equals.
     *
     * @param expected expected list of category
     * @param actual   actual list of categories
     */
    public static void assertCategoryListDeepEquals(final List<Category> expected, final List<Integer> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(actual.size()).isEqualTo(expected.size());
        for (int i = 0; i < expected.size(); i++) {
            assertCategoryDeepEquals(expected.get(i), actual.get(i));
        }
    }

    /**
     * Asserts category deep equals.
     *
     * @param expected expected category
     * @param actual   actual category
     */
    public static void assertCategoryDeepEquals(final Category expected, final Integer actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> softly.assertThat(actual).isEqualTo(expected.getId()));
    }

}

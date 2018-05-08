package cz.vhromada.book.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.book.entity.Category;
import cz.vhromada.book.web.common.CategoryUtils;
import cz.vhromada.book.web.fo.CategoryFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link CategoryFO} to {@link Category}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class CategoryConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    void convertCategoryFO() {
        final CategoryFO categoryFO = CategoryUtils.getCategoryFO();

        final Category category = converter.convert(categoryFO, Category.class);

        CategoryUtils.assertCategoryDeepEquals(categoryFO, category);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for category.
     */
    @Test
    void convertCategoryFO_NullCategoryFO() {
        assertThat(converter.convert(null, Category.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    void convertCategory() {
        final Category category = CategoryUtils.getCategory();

        final CategoryFO categoryFO = converter.convert(category, CategoryFO.class);

        CategoryUtils.assertCategoryDeepEquals(categoryFO, category);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null category.
     */
    @Test
    void convertCategory_NullCategory() {
        assertThat(converter.convert(null, CategoryFO.class)).isNull();
    }

}

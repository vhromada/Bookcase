package cz.vhromada.book.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.book.entity.Category;
import cz.vhromada.book.web.common.CategoryUtils;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link Category} to {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class CategoryToIntegerConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to integer.
     */
    @Test
    void convertCategory() {
        final Category category = CategoryUtils.getCategory();

        final Integer result = converter.convert(category, Integer.class);

        CategoryUtils.assertCategoryDeepEquals(category, result);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to integer with null argument.
     */
    @Test
    void convertCategory_NullCategory() {
        assertThat(converter.convert(null, Integer.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to entity.
     */
    @Test
    void convertInteger() {
        final Category category = converter.convert(1, Category.class);

        CategoryUtils.assertCategoryDeepEquals(1, category);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to entity with null argument.
     */
    @Test
    void convertInteger_NullInteger() {
        assertThat(converter.convert(null, Integer.class)).isNull();
    }

}

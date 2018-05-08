package cz.vhromada.book.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.book.CoreTestConfiguration;
import cz.vhromada.book.entity.Category;
import cz.vhromada.book.utils.CategoryUtils;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter between {@link cz.vhromada.book.domain.Category} and {@link Category}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreTestConfiguration.class)
class CategoryConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from domain to entity.
     */
    @Test
    void convertCategoryDomain() {
        final cz.vhromada.book.domain.Category categoryDomain = CategoryUtils.newCategoryDomain(1);
        final Category category = converter.convert(categoryDomain, Category.class);

        CategoryUtils.assertCategoryDeepEquals(category, categoryDomain);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from domain to entity with null category.
     */
    @Test
    void convertCategoryDomain_NullCategory() {
        assertThat(converter.convert(null, Category.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to domain.
     */
    @Test
    void convertCategory() {
        final Category category = CategoryUtils.newCategory(1);
        final cz.vhromada.book.domain.Category categoryDomain = converter.convert(category, cz.vhromada.book.domain.Category.class);

        CategoryUtils.assertCategoryDeepEquals(category, categoryDomain);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to domain with null category.
     */
    @Test
    void convertCategory_NullCategory() {
        assertThat(converter.convert(null, cz.vhromada.book.domain.Category.class)).isNull();
    }

}

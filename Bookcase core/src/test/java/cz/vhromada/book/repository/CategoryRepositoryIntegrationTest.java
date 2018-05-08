package cz.vhromada.book.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;

import cz.vhromada.book.CoreTestConfiguration;
import cz.vhromada.book.domain.Category;
import cz.vhromada.book.utils.CategoryUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 * A class represents integration test for class {@link CategoryRepository}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreTestConfiguration.class)
@Transactional
@Rollback
class CategoryRepositoryIntegrationTest {

    /**
     * Instance of {@link EntityManager}
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private EntityManager entityManager;

    /**
     * Instance of {@link CategoryRepository}
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Test method for get categories.
     */
    @Test
    void getCategories() {
        final List<Category> categories = categoryRepository.findAll();

        CategoryUtils.assertCategoriesDeepEquals(CategoryUtils.getCategories(), categories);

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT);
    }

    /**
     * Test method for get category.
     */
    @Test
    void getCategory() {
        for (int i = 1; i < CategoryUtils.CATEGORIES_COUNT; i++) {
            final Category category = categoryRepository.findById(i).orElse(null);

            CategoryUtils.assertCategoryDeepEquals(CategoryUtils.getCategoryDomain(i), category);
        }

        assertThat(categoryRepository.findById(Integer.MAX_VALUE).isPresent()).isFalse();

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT);
    }

    /**
     * Test method for add category.
     */
    @Test
    void add() {
        final Category category = CategoryUtils.newCategoryDomain(null);
        category.setPosition(CategoryUtils.CATEGORIES_COUNT);

        categoryRepository.save(category);

        assertThat(category.getId()).isEqualTo(CategoryUtils.CATEGORIES_COUNT + 1);

        final Category addedCategory = CategoryUtils.getCategory(entityManager, CategoryUtils.CATEGORIES_COUNT + 1);
        final Category expectedAddCategory = CategoryUtils.newCategoryDomain(null);
        expectedAddCategory.setId(CategoryUtils.CATEGORIES_COUNT + 1);
        expectedAddCategory.setPosition(CategoryUtils.CATEGORIES_COUNT);
        CategoryUtils.assertCategoryDeepEquals(expectedAddCategory, addedCategory);

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT + 1);
    }

    /**
     * Test method for update category.
     */
    @Test
    void update() {
        final Category category = CategoryUtils.updateCategory(entityManager, 1);

        categoryRepository.save(category);

        final Category updatedCategory = CategoryUtils.getCategory(entityManager, 1);
        final Category expectedUpdatedCategory = CategoryUtils.getCategoryDomain(1);
        CategoryUtils.updateCategory(expectedUpdatedCategory);
        expectedUpdatedCategory.setPosition(CategoryUtils.POSITION);
        CategoryUtils.assertCategoryDeepEquals(expectedUpdatedCategory, updatedCategory);

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT);
    }

    /**
     * Test method for remove category.
     */
    @Test
    void remove() {
        entityManager.createNativeQuery("DELETE FROM book_categories").executeUpdate();

        categoryRepository.delete(CategoryUtils.getCategory(entityManager, 1));

        assertThat(CategoryUtils.getCategory(entityManager, 1)).isNull();

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(CategoryUtils.CATEGORIES_COUNT - 1);
    }

    /**
     * Test method for remove all categories.
     */
    @Test
    void removeAll() {
        entityManager.createNativeQuery("DELETE FROM book_categories").executeUpdate();

        categoryRepository.deleteAll();

        assertThat(CategoryUtils.getCategoriesCount(entityManager)).isEqualTo(0);
    }

}

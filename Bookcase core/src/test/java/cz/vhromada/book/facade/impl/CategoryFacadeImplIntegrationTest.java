package cz.vhromada.book.facade.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import cz.vhromada.book.CoreTestConfiguration;
import cz.vhromada.book.entity.Category;
import cz.vhromada.book.facade.CategoryFacade;
import cz.vhromada.book.utils.BookUtils;
import cz.vhromada.book.utils.CategoryUtils;
import cz.vhromada.common.facade.MovableParentFacade;
import cz.vhromada.common.test.facade.MovableParentFacadeIntegrationTest;
import cz.vhromada.result.Event;
import cz.vhromada.result.Result;
import cz.vhromada.result.Severity;
import cz.vhromada.result.Status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * A class represents integration test for class {@link CategoryFacadeImpl}.
 *
 * @author Vladimir Hromada
 */
@ContextConfiguration(classes = CoreTestConfiguration.class)
class CategoryFacadeImplIntegrationTest extends MovableParentFacadeIntegrationTest<Category, cz.vhromada.book.domain.Category> {

    /**
     * Instance of {@link EntityManager}
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private EntityManager entityManager;

    /**
     * Instance of {@link PlatformTransactionManager}
     */
    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * Instance of {@link CategoryFacade}
     */
    @Autowired
    private CategoryFacade categoryFacade;

    /**
     * Test method for {@link CategoryFacade#add(Category)} with category with null name.
     */
    @Test
    void add_NullName() {
        final Category category = newData(null);
        category.setName(null);

        final Result<Void> result = categoryFacade.add(category);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NAME_NULL", "Name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link CategoryFacade#add(Category)} with category with empty string as name.
     */
    @Test
    void add_EmptyName() {
        final Category category = newData(null);
        category.setName("");

        final Result<Void> result = categoryFacade.add(category);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link CategoryFacade#update(Category)} with category with null name.
     */
    @Test
    void update_NullName() {
        final Category category = newData(1);
        category.setName(null);

        final Result<Void> result = categoryFacade.update(category);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NAME_NULL", "Name mustn't be null.")));
        });

        assertDefaultRepositoryData();
    }

    /**
     * Test method for {@link CategoryFacade#update(Category)} with category with empty string as name.
     */
    @Test
    void update_EmptyName() {
        final Category category = newData(1);
        category.setName("");

        final Result<Void> result = categoryFacade.update(category);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")));
        });

        assertDefaultRepositoryData();
    }

    @Override
    protected MovableParentFacade<Category> getMovableParentFacade() {
        return categoryFacade;
    }

    @Override
    protected Integer getDefaultDataCount() {
        return CategoryUtils.CATEGORIES_COUNT;
    }

    @Override
    protected Integer getRepositoryDataCount() {
        return CategoryUtils.getCategoriesCount(entityManager);
    }

    @Override
    protected List<cz.vhromada.book.domain.Category> getDataList() {
        return CategoryUtils.getCategories();
    }

    @Override
    protected cz.vhromada.book.domain.Category getDomainData(final Integer index) {
        return CategoryUtils.getCategoryDomain(index);
    }

    @Override
    protected Category newData(final Integer id) {
        return CategoryUtils.newCategory(id);
    }

    @Override
    protected cz.vhromada.book.domain.Category newDomainData(final Integer id) {
        return CategoryUtils.newCategoryDomain(id);
    }

    @Override
    protected cz.vhromada.book.domain.Category getRepositoryData(final Integer id) {
        return CategoryUtils.getCategory(entityManager, id);
    }

    @Override
    protected String getName() {
        return "Category";
    }

    @Override
    protected void clearReferencedData() {
        final TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        entityManager.createNativeQuery("DELETE FROM book_categories").executeUpdate();
        transactionManager.commit(transactionStatus);
    }

    @Override
    protected void assertDataListDeepEquals(final List<Category> expected, final List<cz.vhromada.book.domain.Category> actual) {
        CategoryUtils.assertCategoryListDeepEquals(expected, actual);
    }

    @Override
    protected void assertDataDeepEquals(final Category expected, final cz.vhromada.book.domain.Category actual) {
        CategoryUtils.assertCategoryDeepEquals(expected, actual);
    }

    @Override
    protected void assertDataDomainDeepEquals(final cz.vhromada.book.domain.Category expected, final cz.vhromada.book.domain.Category actual) {
        CategoryUtils.assertCategoryDeepEquals(expected, actual);
    }

    @Override
    protected void assertDefaultRepositoryData() {
        super.assertDefaultRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertNewRepositoryData() {
        super.assertNewRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertAddRepositoryData() {
        super.assertAddRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertUpdateRepositoryData() {
        super.assertUpdateRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertRemoveRepositoryData() {
        super.assertRemoveRepositoryData();

        assertReferences();
    }

    @Override
    protected void assertDuplicateRepositoryData() {
        super.assertDuplicateRepositoryData();

        assertReferences();
    }

    /**
     * Asserts references.
     */
    private void assertReferences() {
        assertThat(BookUtils.getBooksCount(entityManager)).isEqualTo(BookUtils.BOOKS_COUNT);
    }

}

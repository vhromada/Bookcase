package cz.vhromada.book.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Collections;

import cz.vhromada.book.entity.Category;
import cz.vhromada.book.utils.CategoryUtils;
import cz.vhromada.common.Movable;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.test.validator.MovableValidatorTest;
import cz.vhromada.common.validator.MovableValidator;
import cz.vhromada.common.validator.ValidationType;
import cz.vhromada.result.Event;
import cz.vhromada.result.Result;
import cz.vhromada.result.Severity;
import cz.vhromada.result.Status;

import org.junit.jupiter.api.Test;

/**
 * A class represents test for class {@link CategoryValidator}.
 *
 * @author Vladimir Hromada
 */
class CategoryValidatorTest extends MovableValidatorTest<Category, cz.vhromada.book.domain.Category> {

    /**
     * Test method for {@link CategoryValidator#CategoryValidator(MovableService)} with null service for categories.
     */
    @Test
    void constructor_NullCategoryService() {
        assertThatThrownBy(() -> new CategoryValidator(null)).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link CategoryValidator#validate(Movable, ValidationType...)} with {@link ValidationType#DEEP} with data with null name.
     */
    @Test
    void validate_Deep_NullName() {
        final Category category = getValidatingData(1);
        category.setName(null);

        final Result<Void> result = getMovableValidator().validate(category, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NAME_NULL", "Name mustn't be null.")));
        });

        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link CategoryValidator#validate(Movable, ValidationType...)} with {@link ValidationType#DEEP} with data with empty name.
     */
    @Test
    void validate_Deep_EmptyName() {
        final Category category = getValidatingData(1);
        category.setName("");

        final Result<Void> result = getMovableValidator().validate(category, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string.")));
        });

        verifyZeroInteractions(getMovableService());
    }

    @Override
    protected MovableValidator<Category> getMovableValidator() {
        return new CategoryValidator(getMovableService());
    }

    @Override
    protected Category getValidatingData(final Integer id) {
        return CategoryUtils.newCategory(id);
    }

    @Override
    protected Category getValidatingData(final Integer id, final Integer position) {
        final Category category = CategoryUtils.newCategory(id);
        category.setPosition(position);

        return category;
    }

    @Override
    protected cz.vhromada.book.domain.Category getRepositoryData(final Category validatingData) {
        return CategoryUtils.newCategoryDomain(validatingData.getId());
    }

    @Override
    protected cz.vhromada.book.domain.Category getItem1() {
        return CategoryUtils.newCategoryDomain(1);
    }

    @Override
    protected cz.vhromada.book.domain.Category getItem2() {
        return CategoryUtils.newCategoryDomain(2);
    }

    @Override
    protected String getName() {
        return "Category";
    }

}

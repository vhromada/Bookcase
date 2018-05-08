package cz.vhromada.book.validator;

import cz.vhromada.book.entity.Category;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.validator.AbstractMovableValidator;
import cz.vhromada.result.Event;
import cz.vhromada.result.Result;
import cz.vhromada.result.Severity;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * A class represents implementation of validator for category.
 *
 * @author Vladimir Hromada
 */
@Component("categoryValidator")
public class CategoryValidator extends AbstractMovableValidator<Category, cz.vhromada.book.domain.Category> {

    /**
     * Creates a new instance of CategoryValidator.
     *
     * @param categoryService service for categories
     * @throws IllegalArgumentException if service for categories is null
     */
    public CategoryValidator(final MovableService<cz.vhromada.book.domain.Category> categoryService) {
        super("Category", categoryService);
    }

    /**
     * Validates category deeply.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Name is null</li>
     * <li>Name is empty string</li>
     * </ul>
     *
     * @param data   validating game
     * @param result result with validation errors
     */
    @Override
    protected void validateDataDeep(final Category data, final Result<Void> result) {
        if (data.getName() == null) {
            result.addEvent(new Event(Severity.ERROR, "CATEGORY_NAME_NULL", "Name mustn't be null."));
        } else if (!StringUtils.hasText(data.getName())) {
            result.addEvent(new Event(Severity.ERROR, "CATEGORY_NAME_EMPTY", "Name mustn't be empty string."));
        }
    }

}

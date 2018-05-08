package cz.vhromada.book.validator;

import cz.vhromada.book.entity.Author;
import cz.vhromada.common.service.MovableService;
import cz.vhromada.common.validator.AbstractMovableValidator;
import cz.vhromada.result.Event;
import cz.vhromada.result.Result;
import cz.vhromada.result.Severity;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * A class represents implementation of validator for author.
 *
 * @author Vladimir Hromada
 */
@Component("authorValidator")
public class AuthorValidator extends AbstractMovableValidator<Author, cz.vhromada.book.domain.Author> {

    /**
     * Creates a new instance of AuthorValidator.
     *
     * @param authorService service for authors
     * @throws IllegalArgumentException if service for authors is null
     */
    public AuthorValidator(final MovableService<cz.vhromada.book.domain.Author> authorService) {
        super("Author", authorService);
    }

    /**
     * Validates author deeply.
     * <br>
     * Validation errors:
     * <ul>
     * <li>First name is null</li>
     * <li>First name is empty string</li>
     * <li>Middle name is empty string</li>
     * <li>Last name is null</li>
     * <li>Last name is empty string</li>
     * </ul>
     *
     * @param data   validating game
     * @param result result with validation errors
     */
    @Override
    protected void validateDataDeep(final Author data, final Result<Void> result) {
        if (data.getFirstName() == null) {
            result.addEvent(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null."));
        } else if (!StringUtils.hasText(data.getFirstName())) {
            result.addEvent(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string."));
        }
        if (data.getMiddleName() != null && !StringUtils.hasText(data.getMiddleName().trim())) {
            result.addEvent(new Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string."));
        }
        if (data.getLastName() == null) {
            result.addEvent(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null."));
        } else if (!StringUtils.hasText(data.getLastName())) {
            result.addEvent(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string."));
        }
    }

}

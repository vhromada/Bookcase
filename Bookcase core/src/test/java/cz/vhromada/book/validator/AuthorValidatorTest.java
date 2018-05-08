package cz.vhromada.book.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Collections;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.utils.AuthorUtils;
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
 * A class represents test for class {@link AuthorValidator}.
 *
 * @author Vladimir Hromada
 */
class AuthorValidatorTest extends MovableValidatorTest<Author, cz.vhromada.book.domain.Author> {

    /**
     * Test method for {@link AuthorValidator#AuthorValidator(MovableService)} with null service for authors.
     */
    @Test
    void constructor_NullAuthorService() {
        assertThatThrownBy(() -> new AuthorValidator(null)).isInstanceOf(IllegalArgumentException.class);
    }

    /**
     * Test method for {@link AuthorValidator#validate(Movable, ValidationType...)} with {@link ValidationType#DEEP} with data with null first name.
     */
    @Test
    void validate_Deep_NullFirstName() {
        final Author author = getValidatingData(1);
        author.setFirstName(null);

        final Result<Void> result = getMovableValidator().validate(author, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_NULL", "First name mustn't be null.")));
        });

        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link AuthorValidator#validate(Movable, ValidationType...)} with {@link ValidationType#DEEP} with data with empty first name.
     */
    @Test
    void validate_Deep_EmptyFirstName() {
        final Author author = getValidatingData(1);
        author.setFirstName("");

        final Result<Void> result = getMovableValidator().validate(author, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_FIRST_NAME_EMPTY", "First name mustn't be empty string.")));
        });

        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link AuthorValidator#validate(Movable, ValidationType...)} with {@link ValidationType#DEEP} with data with empty middle name.
     */
    @Test
    void validate_Deep_EmptyMiddleName() {
        final Author author = getValidatingData(1);
        author.setMiddleName("");

        final Result<Void> result = getMovableValidator().validate(author, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_MIDDLE_NAME_EMPTY", "Middle name mustn't be empty string.")));
        });

        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link AuthorValidator#validate(Movable, ValidationType...)} with {@link ValidationType#DEEP} with data with null last name.
     */
    @Test
    void validate_Deep_NullLastName() {
        final Author author = getValidatingData(1);
        author.setLastName(null);

        final Result<Void> result = getMovableValidator().validate(author, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_NULL", "Last name mustn't be null.")));
        });

        verifyZeroInteractions(getMovableService());
    }

    /**
     * Test method for {@link AuthorValidator#validate(Movable, ValidationType...)} with {@link ValidationType#DEEP} with data with empty last name.
     */
    @Test
    void validate_Deep_EmptyLastName() {
        final Author author = getValidatingData(1);
        author.setLastName("");

        final Result<Void> result = getMovableValidator().validate(author, ValidationType.DEEP);

        assertSoftly(softly -> {
            softly.assertThat(result.getStatus()).isEqualTo(Status.ERROR);
            softly.assertThat(result.getEvents())
                .isEqualTo(Collections.singletonList(new Event(Severity.ERROR, "AUTHOR_LAST_NAME_EMPTY", "Last name mustn't be empty string.")));
        });

        verifyZeroInteractions(getMovableService());
    }

    @Override
    protected MovableValidator<Author> getMovableValidator() {
        return new AuthorValidator(getMovableService());
    }

    @Override
    protected Author getValidatingData(final Integer id) {
        return AuthorUtils.newAuthor(id);
    }

    @Override
    protected Author getValidatingData(final Integer id, final Integer position) {
        final Author author = AuthorUtils.newAuthor(id);
        author.setPosition(position);

        return author;
    }

    @Override
    protected cz.vhromada.book.domain.Author getRepositoryData(final Author validatingData) {
        return AuthorUtils.newAuthorDomain(validatingData.getId());
    }

    @Override
    protected cz.vhromada.book.domain.Author getItem1() {
        return AuthorUtils.newAuthorDomain(1);
    }

    @Override
    protected cz.vhromada.book.domain.Author getItem2() {
        return AuthorUtils.newAuthorDomain(2);
    }

    @Override
    protected String getName() {
        return "Author";
    }

}

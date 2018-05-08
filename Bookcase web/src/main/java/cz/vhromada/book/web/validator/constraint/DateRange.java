package cz.vhromada.book.web.validator.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cz.vhromada.book.web.validator.DateRangeValidator;

/**
 * An annotation represents date range constraint.
 *
 * @author Vladimir Hromada
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface DateRange {

    /**
     * Returns error message template.
     *
     * @return error message template
     */
    String message() default "{cz.vhromada.book.validator.DateRange.message}";

    /**
     * Returns groups constraint belongs to.
     *
     * @return groups constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * Returns payload associated to constraint.
     *
     * @return payload associated to constraint
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Returns valid minimal year.
     *
     * @return valid minimal year
     */
    int value();

}

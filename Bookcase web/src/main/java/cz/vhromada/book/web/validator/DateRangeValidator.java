package cz.vhromada.book.web.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cz.vhromada.book.web.validator.constraint.DateRange;
import cz.vhromada.common.utils.Constants;

/**
 * A class represents validator for date range constraint.
 *
 * @author Vladimir Hromada
 */
public class DateRangeValidator implements ConstraintValidator<DateRange, String> {

    /**
     * Date pattern
     */
    private static final Pattern PATTERN = Pattern.compile("\\d{4}");

    /**
     * Minimal date
     */
    private int minDate;

    /**
     * Maximal date
     */
    private int maxDate;

    @Override
    public void initialize(final DateRange dateRange) {
        minDate = dateRange.value();
        maxDate = Constants.CURRENT_YEAR;
    }

    @Override
    public boolean isValid(final String date, final ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        if (!PATTERN.matcher(date).matches()) {
            return false;
        }

        final int intValue = Integer.parseInt(date);
        return intValue >= minDate && intValue <= maxDate;
    }

}

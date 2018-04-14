package cz.vhromada.book.web.validator

import cz.vhromada.book.utils.Constants
import cz.vhromada.book.web.validator.constraint.DateRange
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * A class represents validator for date range constraint.
 *
 * @author Vladimir Hromada
 */
class DateRangeValidator : ConstraintValidator<DateRange, String> {

    /**
     * Date pattern
     */
    private val pattern = Pattern.compile("\\d{4}")

    /**
     * Minimal date
     */
    private var minDate: Int = 0

    /**
     * Maximal date
     */
    private var maxDate: Int = 0

    override fun initialize(dateRange: DateRange) {
        minDate = dateRange.value
        maxDate = Constants.CURRENT_YEAR
    }

    override fun isValid(date: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        if (date == null) {
            return false
        }
        if (!pattern.matcher(date).matches()) {
            return false
        }

        val intValue = Integer.parseInt(date)
        return intValue in minDate..maxDate
    }

}

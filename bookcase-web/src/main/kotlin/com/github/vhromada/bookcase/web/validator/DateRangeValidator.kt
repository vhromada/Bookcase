package com.github.vhromada.bookcase.web.validator

import com.github.vhromada.bookcase.web.validator.constraint.DateRange
import com.github.vhromada.common.utils.Constants
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * A class represents validator for date range constraint.
 *
 * @author Vladimir Hromada
 */
class DateRangeValidator : ConstraintValidator<DateRange, String?> {

    /**
     * Minimal date
     */
    private var minDate = 0

    override fun initialize(dateRange: DateRange) {
        minDate = dateRange.value
    }

    override fun isValid(date: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        if (date == null || !PATTERN.matcher(date).matches()) {
            return false
        }

        return date.toInt() in minDate..Constants.CURRENT_YEAR
    }

    @Suppress("CheckStyle")
    companion object {

        /**
         * Date pattern
         */
        private val PATTERN = Pattern.compile("\\d{4}")

    }

}

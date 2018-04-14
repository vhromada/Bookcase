package cz.vhromada.book.web.validator.constraint

import cz.vhromada.book.web.validator.DateRangeValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

/**
 * An annotation represents date range constraint.
 *
 * @author Vladimir Hromada
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DateRangeValidator::class])
@MustBeDocumented
annotation class DateRange(

    /**
     * Error message template.
     */
    val message: String = "{cz.vhromada.bookcase.validator.DateRange.message}",

    /**
     * Groups constraint belongs to
     */
    val groups: Array<KClass<out Any>> = [],

    /**
     * Payload associated to constraint
     */
    val payload: Array<KClass<out Any>> = [],

    /**
     * Valid minimal year
     */
    val value: Int)

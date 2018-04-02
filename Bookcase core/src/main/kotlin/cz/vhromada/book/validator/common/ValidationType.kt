package cz.vhromada.book.validator.common

/**
 * An enum represents type of validation.
 *
 * @author Vladimir Hromada
 */
enum class ValidationType {

    /**
     * New entity validation
     */
    NEW,

    /**
     * Existing entity validation
     */
    EXISTS,

    /**
     * Deep validation
     */
    DEEP,

    /**
     * Moving up entity validation
     */
    UP,

    /**
     * Moving down entity validation
     */
    DOWN

}

package cz.vhromada.book.web.fo

import cz.vhromada.book.common.Language
import cz.vhromada.book.utils.Constants
import cz.vhromada.book.web.validator.constraint.DateRange
import java.util.Objects
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * A class represents FO for book.
 *
 * @author Vladimir Hromada
 */
data class BookFO(

    /**
     * ID
     */
    val id: Int? = null,

    /**
     * Czech name
     */
    @field:NotBlank
    var czechName: String? = null,

    /**
     * Original name
     */
    @field:NotBlank
    var originalName: String? = null,

    /**
     * Languages
     */
    @field:Size(min = 1)
    val languages: List<Language> = mutableListOf(),

    /**
     * ISBN
     */
    var isbn: String? = null,

    /**
     * Issue year
     */
    @field:DateRange(value = Constants.MIN_YEAR)
    var issueYear: String? = null,

    /**
     * Description
     */
    @field:NotBlank
    var description: String? = null,

    /**
     * True if there is electronic version
     */
    var electronic: Boolean = false,

    /**
     * True if there is paper version
     */
    var paper: Boolean = false,

    /**
     * Note
     */
    var note: String? = null,

    /**
     * Position
     */
    val position: Int = 0,

    /**
     * Authors
     */
    @field:Size(min = 1)
    val authors: List<Int> = mutableListOf(),

    /**
     * Languages
     */
    @field:Size(min = 1)
    val categories: List<Int> = mutableListOf()) {

    /**
     * Normalizes data.
     */
    fun normalize() {
        if (isbn.isNullOrEmpty()) {
            isbn = null
        }
        if (note.isNullOrEmpty()) {
            note = null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return if (other !is BookFO || id == null) {
            false
        } else {
            id == other.id
        }
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

}

package cz.vhromada.bookcase.web.converter

import cz.vhromada.bookcase.entity.Author
import cz.vhromada.bookcase.entity.Category
import cz.vhromada.common.entity.Language

/**
 * A class represents utility for converting data.
 *
 * @author Vladimir Hromada
 */
object ConvertUtils {

    /**
     * Converts languages.
     *
     * @param languages languages
     * @return converted languages
     */
    @JvmStatic
    fun convertLanguages(languages: List<Language?>): String {
        if (languages.isEmpty()) {
            return ""
        }

        val result = StringBuilder()
        for (language in languages) {
            result.append(language)
            result.append(", ")
        }
        return result.substring(0, result.length - 2)
    }

    /**
     * Converts authors.
     *
     * @param authors authors
     * @return converted authors
     */
    @JvmStatic
    fun convertAuthors(authors: List<Author>): String {
        val result = StringBuilder()
        for (author in authors) {
            result.append(convertAuthor(author))
            result.append(", ")
        }
        return result.substring(0, result.length - 2)
    }

    /**
     * Converts author.
     *
     * @param author author
     * @return converted author
     */
    @JvmStatic
    fun convertAuthor(author: Author): String {
        val result = StringBuilder()
        result.append(author.firstName)
        result.append(' ')
        if (!author.middleName.isNullOrBlank()) {
            result.append(author.middleName)
            result.append(' ')
        }
        result.append(author.lastName)
        return result.toString()
    }

    /**
     * Converts categories.
     *
     * @param categories categories
     * @return converted categories
     */
    @JvmStatic
    fun convertCategories(categories: List<Category>): String {
        val result = StringBuilder()
        for (category in categories) {
            result.append(category.name)
            result.append(", ")
        }
        return result.substring(0, result.length - 2)
    }

}

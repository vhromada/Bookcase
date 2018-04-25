package cz.vhromada.book.web

import cz.vhromada.book.common.Language
import cz.vhromada.book.entity.Author
import cz.vhromada.book.entity.Book
import cz.vhromada.book.entity.Category

/**
 * A class represents utility for converting data.
 *
 * @author Vladimir Hromada
 */
class ConvertUtils {

    companion object {

        /**
         * Converts languages.
         *
         * @param languages languages
         * @return converted languages
         */
        @JvmStatic
        fun convertLanguages(languages: List<Language>): String {
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
            if (authors.isEmpty()) {
                return ""
            }

            val result = StringBuilder()
            for (author in authors) {
                result.append(author.firstName)
                if (!author.middleName.isNullOrBlank()) {
                    result.append(' ')
                    result.append(author.middleName)
                }
                result.append(' ')
                result.append(author.lastName)
                result.append(", ")
            }

            return result.substring(0, result.length - 2)
        }

        /**
         * Converts categories.
         *
         * @param categories categories
         * @return converted categories
         */
        @JvmStatic
        fun convertCategories(categories: List<Category>): String {
            if (categories.isEmpty()) {
                return ""
            }

            val result = StringBuilder()
            for (category in categories) {
                result.append(category.name)
                result.append(", ")
            }

            return result.substring(0, result.length - 2)
        }

        /**
         * Converts version.
         *
         * @param book book
         * @return converted version
         */
        @JvmStatic
        fun convertVersion(book: Book): String {
            val result = StringBuilder()
            if (book.electronic) {
                result.append("Electronic")
            }
            if (book.paper) {
                if (result.isEmpty()) {
                    result.append("Paper")
                } else {
                    result.append(", paper")
                }
            }

            return result.toString()
        }

    }

}

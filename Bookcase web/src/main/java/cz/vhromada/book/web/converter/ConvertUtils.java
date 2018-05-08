package cz.vhromada.book.web.converter;

import java.util.List;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.entity.Book;
import cz.vhromada.book.entity.Category;
import cz.vhromada.common.Language;

import org.springframework.util.StringUtils;

/**
 * A class represents utility for converting data.
 *
 * @author Vladimir Hromada
 */
public final class ConvertUtils {

    /**
     * Creates a new instance of ConvertUtils.
     */
    private ConvertUtils() {
    }

    /**
     * Converts languages.
     *
     * @param languages languages
     * @return converted languages
     */
    public static String convertLanguages(final List<Language> languages) {
        if (languages.isEmpty()) {
            return "";
        }

        final StringBuilder result = new StringBuilder();
        for (final Language language : languages) {
            result.append(language);
            result.append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

    /**
     * Converts authors.
     *
     * @param authors authors
     * @return converted authors
     */
    public static String convertAuthors(final List<Author> authors) {
        final StringBuilder result = new StringBuilder();
        for (final Author author : authors) {
            result.append(convertAuthor(author));
            result.append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

    /**
     * Converts author.
     *
     * @param author author
     * @return converted author
     */
    public static String convertAuthor(final Author author) {
        final StringBuilder result = new StringBuilder();
        result.append(author.getFirstName());
        result.append(' ');
        if (!StringUtils.isEmpty(author.getMiddleName())) {
            result.append(author.getMiddleName());
            result.append(' ');
        }
        result.append(author.getLastName());

        return result.toString();
    }

    /**
     * Converts categories.
     *
     * @param categories categories
     * @return converted categories
     */
    public static String convertCategories(final List<Category> categories) {
        final StringBuilder result = new StringBuilder();
        for (final Category category : categories) {
            result.append(category.getName());
            result.append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

    /**
     * Converts version.
     *
     * @param book book
     * @return converted version
     */
    public static String convertVersion(final Book book) {
        final StringBuilder result = new StringBuilder();
        if (Boolean.TRUE == book.getElectronic()) {
            result.append("Electronic");
        }
        if (Boolean.TRUE == book.getPaper()) {
            if (result.length() == 0) {
                result.append("Paper");
            } else {
                result.append(", paper");
            }
        }

        return result.toString();
    }

}

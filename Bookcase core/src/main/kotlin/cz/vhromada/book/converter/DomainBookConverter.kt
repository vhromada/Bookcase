package cz.vhromada.book.converter

import cz.vhromada.book.entity.Author
import cz.vhromada.book.entity.Book
import cz.vhromada.book.entity.Category
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * A class represent converter for book.
 *
 * @author Vladimir Hromada
 */
@Component("domainBookConverter")
class DomainBookConverter(

    /**
     * Converter for author
     */
    private val authorConverter: DomainAuthorConverter,

    /**
     * Converter for author
     */
    private val categoryConverter: DomainCategoryConverter

) : Converter<cz.vhromada.book.domain.Book, Book> {

    /**
     * Returns converted [Book] from [cz.vhromada.book.domain.Book].
     *
     * @param source source
     * @return converted book
     */
    override fun convert(source: cz.vhromada.book.domain.Book): Book {
        return Book(source.id, source.czechName, source.originalName, source.languages, source.isbn, source.issueYear, source.description, source.electronic, source.paper, source.note, source.position, convertAuthors(source.authors), convertCategories(source.categories))
    }

    private fun convertAuthors(source: List<cz.vhromada.book.domain.Author>): List<Author> {
        val authors = mutableListOf<Author>()
        for (author in source) {
            authors.add(authorConverter.convert(author))
        }
        return authors
    }

    private fun convertCategories(source: List<cz.vhromada.book.domain.Category>): List<Category> {
        val categories = mutableListOf<Category>()
        for (category in source) {
            categories.add(categoryConverter.convert(category))
        }
        return categories
    }

}

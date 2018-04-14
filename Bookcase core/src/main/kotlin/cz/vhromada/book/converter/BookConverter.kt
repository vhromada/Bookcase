package cz.vhromada.book.converter

import cz.vhromada.book.entity.Author
import cz.vhromada.book.entity.Book
import cz.vhromada.book.entity.Category
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * A class represent format for book.
 *
 * @author Vladimir Hromada
 */
@Component("bookConverter")
class BookConverter(

    /**
     * Converter for author
     */
    private val authorConverter: AuthorConverter,

    /**
     * Converter for author
     */
    private val categoryConverter: CategoryConverter

) : Converter<Book, cz.vhromada.book.domain.Book> {

    /**
     * Returns converted [cz.vhromada.book.domain.Book] from [Book].
     *
     * @param source source
     * @return converted book
     */
    override fun convert(source: Book): cz.vhromada.book.domain.Book {
        return cz.vhromada.book.domain.Book(source.id, source.czechName, source.originalName, source.languages, source.isbn, source.issueYear, source.description, source.electronic, source.paper, source.note, source.position, convertAuthors(source.authors), convertCategories(source.categories))
    }

    private fun convertAuthors(source: List<Author>): List<cz.vhromada.book.domain.Author> {
        val authors = mutableListOf<cz.vhromada.book.domain.Author>()
        for (author in source) {
            authors.add(authorConverter.convert(author))
        }
        return authors
    }

    private fun convertCategories(source: List<Category>): List<cz.vhromada.book.domain.Category> {
        val categories = mutableListOf<cz.vhromada.book.domain.Category>()
        for (category in source) {
            categories.add(categoryConverter.convert(category))
        }
        return categories
    }

}

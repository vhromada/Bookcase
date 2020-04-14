package cz.vhromada.bookcase.mapper

import cz.vhromada.bookcase.entity.Book
import cz.vhromada.common.mapper.Mapper
import org.springframework.stereotype.Component

/**
 * A class represents mapper for book.
 *
 * @author Vladimir Hromada
 */
@Component("bookMapper")
class BookMapper(
        private val authorMapper: AuthorMapper,
        private val categoryMapper: CategoryMapper) : Mapper<Book, cz.vhromada.bookcase.domain.Book> {

    override fun map(source: Book): cz.vhromada.bookcase.domain.Book {
        return cz.vhromada.bookcase.domain.Book(
                id = source.id,
                czechName = source.czechName!!,
                originalName = source.originalName!!,
                isbn = source.isbn,
                issueYear = source.issueYear!!,
                description = source.description!!,
                note = source.note,
                position = source.position,
                authors = authorMapper.map(source.authors!!.filterNotNull()),
                categories = categoryMapper.map(source.categories!!.filterNotNull()),
                items = emptyList(),
                audit = null)
    }

    override fun mapBack(source: cz.vhromada.bookcase.domain.Book): Book {
        return Book(
                id = source.id,
                czechName = source.czechName,
                originalName = source.originalName,
                isbn = source.isbn,
                issueYear = source.issueYear,
                description = source.description,
                note = source.note,
                position = source.position,
                authors = authorMapper.mapBack(source.authors),
                categories = categoryMapper.mapBack(source.categories))
    }

}

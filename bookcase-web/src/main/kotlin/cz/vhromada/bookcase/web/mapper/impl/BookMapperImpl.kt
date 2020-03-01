package cz.vhromada.bookcase.web.mapper.impl

import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.web.fo.BookFO
import cz.vhromada.bookcase.web.mapper.BookMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for books.
 *
 * @author Vladimir Hromada
 */
@Component("webBookMapper")
class BookMapperImpl : BookMapper {

    override fun map(source: Book): BookFO {
        return BookFO(
                id = source.id,
                czechName = source.czechName,
                originalName = source.originalName,
                isbn = source.isbn,
                issueYear = source.issueYear.toString(),
                description = source.description,
                note = source.note,
                position = source.position,
                authors = source.authors!!.map { it!!.id!! },
                categories = source.categories!!.map { it!!.id!! })
    }

    override fun mapBack(source: BookFO): Book {
        return Book(
                id = source.id,
                czechName = source.czechName,
                originalName = source.originalName,
                isbn = source.isbn,
                issueYear = source.issueYear!!.toInt(),
                description = source.description,
                note = source.note,
                position = source.position,
                authors = null,
                categories = null)
    }

}

package com.github.vhromada.bookcase.mapper

import com.github.vhromada.bookcase.entity.Book
import com.github.vhromada.common.mapper.Mapper
import org.springframework.stereotype.Component

/**
 * A class represents mapper for book.
 *
 * @author Vladimir Hromada
 */
@Component("bookMapper")
class BookMapper(private val authorMapper: AuthorMapper) : Mapper<Book, com.github.vhromada.bookcase.domain.Book> {

    override fun map(source: Book): com.github.vhromada.bookcase.domain.Book {
        return com.github.vhromada.bookcase.domain.Book(
                id = source.id,
                czechName = source.czechName!!,
                originalName = source.originalName!!,
                isbn = source.isbn,
                issueYear = source.issueYear!!,
                description = source.description!!,
                note = source.note,
                position = source.position,
                authors = authorMapper.map(source.authors!!.filterNotNull()),
                items = emptyList(),
                audit = null)
    }

    override fun mapBack(source: com.github.vhromada.bookcase.domain.Book): Book {
        return Book(
                id = source.id,
                czechName = source.czechName,
                originalName = source.originalName,
                isbn = source.isbn,
                issueYear = source.issueYear,
                description = source.description,
                note = source.note,
                position = source.position,
                authors = authorMapper.mapBack(source.authors))
    }

}

package cz.vhromada.bookcase.web.mapper

import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.web.fo.BookFO

/**
 * An interface represents mapper for books.
 *
 * @author Vladimir Hromada
 */
interface BookMapper {

    /**
     * Returns FO for book.
     *
     * @param source book
     * @return FO for book
     */
    fun map(source: Book): BookFO

    /**
     * Returns book.
     *
     * @param source FO for book
     * @return book
     */
    fun mapBack(source: BookFO): Book

}

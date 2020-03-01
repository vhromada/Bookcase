package cz.vhromada.bookcase.web.mapper

import cz.vhromada.bookcase.entity.Author
import cz.vhromada.bookcase.web.fo.AuthorFO

/**
 * An interface represents mapper for authors.
 *
 * @author Vladimir Hromada
 */
interface AuthorMapper {

    /**
     * Returns FO for author.
     *
     * @param source author
     * @return FO for author
     */
    fun map(source: Author): AuthorFO

    /**
     * Returns author.
     *
     * @param source FO for author
     * @return author
     */
    fun mapBack(source: AuthorFO): Author

}

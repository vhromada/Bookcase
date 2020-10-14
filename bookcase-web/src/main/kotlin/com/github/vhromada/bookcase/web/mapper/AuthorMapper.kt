package com.github.vhromada.bookcase.web.mapper

import com.github.vhromada.bookcase.entity.Author
import com.github.vhromada.bookcase.web.fo.AuthorFO

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

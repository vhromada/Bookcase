package com.github.vhromada.bookcase.web.mapper

import com.github.vhromada.bookcase.entity.Author
import com.github.vhromada.bookcase.web.BookcaseMapperTestConfiguration
import com.github.vhromada.bookcase.web.common.AuthorUtils
import com.github.vhromada.bookcase.web.fo.AuthorFO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [Author] and [AuthorFO].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [BookcaseMapperTestConfiguration::class])
class AuthorMapperTest {

    /**
     * Mapper for authors
     */
    @Autowired
    private lateinit var mapper: AuthorMapper

    /**
     * Test method for [AuthorMapper.map].
     */
    @Test
    fun map() {
        val author = AuthorUtils.getAuthor()

        val authorFO = mapper.map(author)

        AuthorUtils.assertAuthorDeepEquals(authorFO, author)
    }

    /**
     * Test method for [AuthorMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val authorFO = AuthorUtils.getAuthorFO()

        val author = mapper.mapBack(authorFO)

        AuthorUtils.assertAuthorDeepEquals(authorFO, author)
    }

}

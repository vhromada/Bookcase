package cz.vhromada.bookcase.mapper

import cz.vhromada.bookcase.CoreTestConfiguration
import cz.vhromada.bookcase.entity.Author
import cz.vhromada.bookcase.utils.AuthorUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [cz.vhromada.bookcase.domain.Author] and [Author].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CoreTestConfiguration::class])
class AuthorMapperTest {

    /**
     * Instance of [AuthorMapper]
     */
    @Autowired
    private lateinit var mapper: AuthorMapper

    /**
     * Test method for [AuthorMapper.map].
     */
    @Test
    fun map() {
        val author = AuthorUtils.newAuthor(1)
        val authorDomain = mapper.map(author)

        AuthorUtils.assertAuthorDeepEquals(author, authorDomain)
    }

    /**
     * Test method for [AuthorMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val authorDomain = AuthorUtils.newAuthorDomain(1)
        val author = mapper.mapBack(authorDomain)

        AuthorUtils.assertAuthorDeepEquals(author, authorDomain)
    }

}

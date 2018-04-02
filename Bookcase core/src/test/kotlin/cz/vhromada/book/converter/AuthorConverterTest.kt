package cz.vhromada.book.converter

import cz.vhromada.book.CoreTestConfiguration
import cz.vhromada.book.domain.Author
import cz.vhromada.book.utils.AuthorUtils
import cz.vhromada.converter.Converter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for class [AuthorConverter] and [DomainAuthorConverter].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CoreTestConfiguration::class])
class AuthorConverterTest {

    /**
     * Instance of [Converter]
     */
    @Autowired
    private val converter: Converter? = null

    /**
     * Checks autowired fields.
     */
    @BeforeEach
    fun setUp() {
        assertThat(converter).isNotNull
    }

    /**
     * Test method for [AuthorConverter.convert].
     */
    @Test
    fun convert_Entity() {
        val author = AuthorUtils.newAuthor(1)
        val authorDomain = converter!!.convert(author, Author::class.java)

        AuthorUtils.assertAuthorDeepEquals(authorDomain, author)
    }

    /**
     * Test method for [DomainAuthorConverter.convert].
     */
    @Test
    fun convert_Domain() {
        val authorDomain = AuthorUtils.newAuthorDomain(1)
        val author = converter!!.convert(authorDomain, cz.vhromada.book.entity.Author::class.java)

        AuthorUtils.assertAuthorDeepEquals(authorDomain, author)
    }

}

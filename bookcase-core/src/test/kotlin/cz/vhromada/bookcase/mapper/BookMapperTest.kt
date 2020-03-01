package cz.vhromada.bookcase.mapper

import cz.vhromada.bookcase.CoreTestConfiguration
import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.utils.BookUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [cz.vhromada.bookcase.domain.Book] and [Book].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CoreTestConfiguration::class])
class BookMapperTest {

    /**
     * Instance of [BookMapper]
     */
    @Autowired
    private lateinit var mapper: BookMapper

    /**
     * Test method for [BookMapper.map].
     */
    @Test
    fun map() {
        val book = BookUtils.newBook(1)
        val bookDomain = mapper.map(book)

        BookUtils.assertBookDeepEquals(book, bookDomain)
    }

    /**
     * Test method for [BookMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val bookDomain = BookUtils.newBookDomain(1)
        val book = mapper.mapBack(bookDomain)

        BookUtils.assertBookDeepEquals(book, bookDomain)
    }

}

package cz.vhromada.book.converter

import cz.vhromada.book.CoreTestConfiguration
import cz.vhromada.book.domain.Book
import cz.vhromada.book.utils.BookUtils
import cz.vhromada.converter.Converter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for class [BookConverter] and [DomainBookConverter].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CoreTestConfiguration::class])
class BookConverterTest {

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
     * Test method for [BookConverter.convert].
     */
    @Test
    fun convert_Entity() {
        val book = BookUtils.newBook(1)
        val bookDomain = converter!!.convert(book, Book::class.java)

        BookUtils.assertBookDeepEquals(bookDomain, book)
    }

    /**
     * Test method for [DomainBookConverter.convert].
     */
    @Test
    fun convert_Domain() {
        val bookDomain = BookUtils.newBookDomain(1)
        val book = converter!!.convert(bookDomain, cz.vhromada.book.entity.Book::class.java)

        BookUtils.assertBookDeepEquals(bookDomain, book)
    }

}

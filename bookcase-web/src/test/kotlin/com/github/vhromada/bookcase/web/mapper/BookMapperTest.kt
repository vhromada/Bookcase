package com.github.vhromada.bookcase.web.mapper

import com.github.vhromada.bookcase.entity.Book
import com.github.vhromada.bookcase.web.BookcaseMapperTestConfiguration
import com.github.vhromada.bookcase.web.common.BookUtils
import com.github.vhromada.bookcase.web.fo.BookFO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [Book] and [BookFO].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [BookcaseMapperTestConfiguration::class])
class BookMapperTest {

    /**
     * Mapper for books
     */
    @Autowired
    private lateinit var mapper: BookMapper

    /**
     * Test method for [BookMapper.map].
     */
    @Test
    fun map() {
        val book = BookUtils.getBook()

        val bookFO = mapper.map(book)

        BookUtils.assertBookDeepEquals(book, bookFO)
    }

    /**
     * Test method for [BookMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val bookFO = BookUtils.getBookFO()

        val book = mapper.mapBack(bookFO)

        BookUtils.assertBookDeepEquals(bookFO, book)
    }

}

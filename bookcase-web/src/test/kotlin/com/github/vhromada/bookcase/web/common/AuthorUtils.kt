package com.github.vhromada.bookcase.web.common

import com.github.vhromada.bookcase.entity.Author
import com.github.vhromada.bookcase.web.fo.AuthorFO
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for authors.
 *
 * @author Vladimir Hromada
 */
object AuthorUtils {

    /**
     * Returns FO for author.
     *
     * @return FO for author
     */
    fun getAuthorFO(): AuthorFO {
        return AuthorFO(
                id = 1,
                firstName = "First name",
                middleName = "Middle name",
                lastName = "Last name",
                position = 0)
    }

    /**
     * Returns author.
     *
     * @return author
     */
    fun getAuthor(): Author {
        return Author(
                id = 1,
                firstName = "First name",
                middleName = "Middle name",
                lastName = "Last name",
                position = 0)
    }

    /**
     * Asserts author deep equals.
     *
     * @param expected expected FO for author
     * @param actual   actual author
     */
    fun assertAuthorDeepEquals(expected: AuthorFO?, actual: Author?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(actual!!.id).isEqualTo(expected!!.id)
            it.assertThat(actual.firstName).isEqualTo(expected.firstName)
            it.assertThat(actual.middleName).isEqualTo(expected.middleName)
            it.assertThat(actual.lastName).isEqualTo(expected.lastName)
            it.assertThat(actual.position).isEqualTo(expected.position)
        }
    }

    /**
     * Asserts authors deep equals.
     *
     * @param expected expected list of authors
     * @param actual   actual list of authors
     */
    fun assertAuthorsDeepEquals(expected: List<Author?>?, actual: List<Int?>?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertThat(actual!!.size).isEqualTo(expected!!.size)
        for (i in expected.indices) {
            assertAuthorDeepEquals(expected[i], actual[i])
        }
    }

    /**
     * Asserts author deep equals.
     *
     * @param expected expected author
     * @param actual   actual author
     */
    private fun assertAuthorDeepEquals(expected: Author?, actual: Int?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly { it.assertThat(actual).isEqualTo(expected!!.id) }
    }

}

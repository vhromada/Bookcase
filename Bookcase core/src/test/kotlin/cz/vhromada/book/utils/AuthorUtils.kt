package cz.vhromada.book.utils

import cz.vhromada.book.domain.Author
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import javax.persistence.EntityManager

/**
 * An object represents utility class for authors.
 *
 * @author Vladimir Hromada
 */
object AuthorUtils {

    /**
     * Count of authors
     */
    const val AUTHORS_COUNT = 3

    /**
     * Returns author.
     *
     * @param id ID
     * @return author
     */
    fun newAuthor(id: Int?): cz.vhromada.book.entity.Author {
        val position = if (id == null) {
            0
        } else {
            id - 1
        }
        return cz.vhromada.book.entity.Author(id, "FirstName", "MiddleName", "LastName", position)
    }

    /**
     * Returns author.
     *
     * @param id ID
     * @return author
     */
    fun newAuthorDomain(id: Int?): Author {
        val position = if (id == null) {
            0
        } else {
            id - 1
        }
        return newAuthorDomain(id, position)
    }

    /**
     * Returns author.
     *
     * @param id       ID
     * @param position position
     * @return author
     */
    fun newAuthorDomain(id: Int?, position: Int): Author {
        return Author(id, "FirstName", "MiddleName", "LastName", position)
    }

    /**
     * Returns authors.
     *
     * @return authors
     */
    fun getAuthors(): List<Author> {
        return (0 until AUTHORS_COUNT).map { getAuthor(it + 1) }
    }

    /**
     * Returns author for index.
     *
     * @param index index
     * @return author for index
     */
    fun getAuthor(index: Int): Author {
        val middleName = if (index == 2) {
            "Author $index Middle Name"
        } else {
            null
        }
        return Author(index, "Author $index First Name", middleName, "Author $index Last Name", index - 1)
    }

    /**
     * Returns author for index.
     *
     * @param index index
     * @return author for index
     */
    fun getAuthorEntity(index: Int): cz.vhromada.book.entity.Author {
        val middleName = if (index == 2) {
            "Author $index Middle Name"
        } else {
            null
        }
        return cz.vhromada.book.entity.Author(index, "Author $index First Name", middleName, "Author $index Last Name", index - 1)
    }

    /**
     * Returns author.
     *
     * @param entityManager entity manager
     * @param id            author's ID
     * @return author
     */
    fun getAuthor(entityManager: EntityManager, id: Int): Author? {
        return entityManager.find(Author::class.java, id)
    }

    /**
     * Returns count of authors.
     *
     * @param entityManager entity manager
     * @return count of authors
     */
    fun getAuthorsCount(entityManager: EntityManager): Int {
        return entityManager.createQuery("SELECT COUNT(a.id) FROM Author a", java.lang.Long::class.java).singleResult.toInt()
    }

    /**
     * Asserts authors deep equals.
     *
     * @param expected expected authors
     * @param actual   actual authors
     */
    fun assertAuthorsDeepEquals(expected: List<Author>?, actual: List<Author>?) {
        assertSoftly { softly ->
            softly.assertThat(expected).isNotNull
            softly.assertThat(actual).isNotNull
        }

        assertThat(expected!!.size).isEqualTo(actual!!.size)
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
    fun assertAuthorDeepEquals(expected: Author?, actual: Author?) {
        assertSoftly { softly ->
            softly.assertThat(expected).isNotNull
            softly.assertThat(actual).isNotNull
        }

        assertSoftly { softly ->
            softly.assertThat(actual!!.id).isEqualTo(expected!!.id)
            softly.assertThat(actual.firstName).isEqualTo(expected.firstName)
            softly.assertThat(actual.middleName).isEqualTo(expected.middleName)
            softly.assertThat(actual.lastName).isEqualTo(expected.lastName)
            softly.assertThat(actual.position).isEqualTo(expected.position)
        }
    }

    /**
     * Asserts authors deep equals.
     *
     * @param expected expected authors
     * @param actual   actual authors
     */
    fun assertAuthorListDeepEquals(expected: List<Author>?, actual: List<cz.vhromada.book.entity.Author>?) {
        assertSoftly { softly ->
            softly.assertThat(expected).isNotNull
            softly.assertThat(actual).isNotNull
        }

        assertThat(expected!!.size).isEqualTo(actual!!.size)
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
    fun assertAuthorDeepEquals(expected: Author?, actual: cz.vhromada.book.entity.Author?) {
        assertSoftly { softly ->
            softly.assertThat(expected).isNotNull
            softly.assertThat(actual).isNotNull
        }

        assertSoftly { softly ->
            softly.assertThat(actual!!.id).isEqualTo(expected!!.id)
            softly.assertThat(actual.firstName).isEqualTo(expected.firstName)
            softly.assertThat(actual.middleName).isEqualTo(expected.middleName)
            softly.assertThat(actual.lastName).isEqualTo(expected.lastName)
            softly.assertThat(actual.position).isEqualTo(expected.position)
        }
    }

}

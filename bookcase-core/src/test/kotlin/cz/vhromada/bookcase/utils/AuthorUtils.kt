package cz.vhromada.bookcase.utils

import cz.vhromada.bookcase.entity.Author
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import javax.persistence.EntityManager

/**
 * Updates author fields.
 *
 * @return updated author
 */
fun cz.vhromada.bookcase.domain.Author.updated(): cz.vhromada.bookcase.domain.Author {
    return copy(firstName = "First name", middleName = "Middle name", lastName = "Last name")
}

/**
 * Updates author fields.
 *
 * @return updated author
 */
fun Author.updated(): Author {
    return copy(firstName = "First name", middleName = "Middle name", lastName = "Last name")
}

/**
 * A class represents utility class for authors.
 *
 * @author Vladimir Hromada
 */
object AuthorUtils {

    /**
     * Count of authors
     */
    const val AUTHORS_COUNT = 3

    /**
     * Position
     */
    const val POSITION = 10

    /**
     * Author name
     */
    private const val AUTHOR = "Author "


    /**
     * Returns authors.
     *
     * @return authors
     */
    fun getAuthors(): List<cz.vhromada.bookcase.domain.Author> {
        val authors = mutableListOf<cz.vhromada.bookcase.domain.Author>()
        for (i in 0 until AUTHORS_COUNT) {
            authors.add(getAuthorDomain(i + 1))
        }

        return authors
    }

    /**
     * Returns author.
     *
     * @param id ID
     * @return author
     */
    fun newAuthorDomain(id: Int?): cz.vhromada.bookcase.domain.Author {
        return cz.vhromada.bookcase.domain.Author(id = id, firstName = "", middleName = "", lastName = "", position = if (id == null) null else id - 1)
                .updated()
    }

    /**
     * Returns author.
     *
     * @param id ID
     * @return author
     */
    fun newAuthor(id: Int?): Author {
        return Author(id = id, firstName = "", middleName = "", lastName = "", position = if (id == null) null else id - 1)
                .updated()
    }

    /**
     * Returns author for index.
     *
     * @param index index
     * @return author for index
     */
    fun getAuthorDomain(index: Int): cz.vhromada.bookcase.domain.Author {
        return cz.vhromada.bookcase.domain.Author(
                id = index,
                firstName = "$AUTHOR$index First Name",
                middleName = if (index == 2) AUTHOR + "2 Middle Name" else "",
                lastName = "$AUTHOR$index Last Name",
                position = index - 1)
    }

    /**
     * Returns author for index.
     *
     * @param index index
     * @return author for index
     */
    fun getAuthor(index: Int): Author {
        return Author(
                id = index,
                firstName = "$AUTHOR$index First Name",
                middleName = if (index == 2) AUTHOR + "2 Middle Name" else "",
                lastName = "$AUTHOR$index Last Name",
                position = index - 1)
    }

    /**
     * Returns author.
     *
     * @param entityManager entity manager
     * @param id            author ID
     * @return author
     */
    fun getAuthor(entityManager: EntityManager, id: Int): cz.vhromada.bookcase.domain.Author? {
        return entityManager.find(cz.vhromada.bookcase.domain.Author::class.java, id)
    }

    /**
     * Returns author with updated fields.
     *
     * @param entityManager entity manager
     * @param id            author ID
     * @return author with updated fields
     */
    fun updateAuthor(entityManager: EntityManager, id: Int): cz.vhromada.bookcase.domain.Author {
        return getAuthor(entityManager, id)!!
                .updated()
                .copy(position = POSITION)
    }

    /**
     * Returns count of authors.
     *
     * @param entityManager entity manager
     * @return count of authors
     */
    @Suppress("CheckStyle")
    fun getAuthorsCount(entityManager: EntityManager): Int {
        return entityManager.createQuery("SELECT COUNT(a.id) FROM Author a", java.lang.Long::class.java).singleResult.toInt()
    }

    /**
     * Asserts authors deep equals.
     *
     * @param expected expected authors
     * @param actual   actual authors
     */
    fun assertAuthorsDeepEquals(expected: List<cz.vhromada.bookcase.domain.Author?>?, actual: List<cz.vhromada.bookcase.domain.Author?>?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertThat(expected!!.size).isEqualTo(actual!!.size)
        if (expected.isNotEmpty()) {
            for (i in expected.indices) {
                assertAuthorDeepEquals(expected[i], actual[i])
            }
        }
    }

    /**
     * Asserts author deep equals.
     *
     * @param expected expected author
     * @param actual   actual author
     */
    fun assertAuthorDeepEquals(expected: cz.vhromada.bookcase.domain.Author?, actual: cz.vhromada.bookcase.domain.Author?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(expected!!.id).isEqualTo(actual!!.id)
            it.assertThat(expected.firstName).isEqualTo(actual.firstName)
            it.assertThat(expected.middleName).isEqualTo(actual.middleName)
            it.assertThat(expected.lastName).isEqualTo(actual.lastName)
            it.assertThat(expected.position).isEqualTo(actual.position)
        }
    }

    /**
     * Asserts authors deep equals.
     *
     * @param expected expected list of author
     * @param actual   actual authors
     */
    fun assertAuthorListDeepEquals(expected: List<Author?>?, actual: List<cz.vhromada.bookcase.domain.Author?>?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertThat(expected!!.size).isEqualTo(actual!!.size)
        if (expected.isNotEmpty()) {
            for (i in expected.indices) {
                assertAuthorDeepEquals(expected[i], actual[i])
            }
        }
    }

    /**
     * Asserts author deep equals.
     *
     * @param expected expected author
     * @param actual   actual author
     */
    fun assertAuthorDeepEquals(expected: Author?, actual: cz.vhromada.bookcase.domain.Author?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(expected!!.id).isEqualTo(actual!!.id)
            it.assertThat(expected.firstName).isEqualTo(actual.firstName)
            it.assertThat(expected.middleName).isEqualTo(actual.middleName)
            it.assertThat(expected.lastName).isEqualTo(actual.lastName)
            it.assertThat(expected.position).isEqualTo(actual.position)
        }
    }

}

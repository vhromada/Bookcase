package cz.vhromada.book.service.impl

import cz.vhromada.book.common.Movable
import cz.vhromada.book.service.BookcaseService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.mockito.Mock
import org.springframework.cache.Cache
import org.springframework.data.jpa.repository.JpaRepository

/**
 * A class represents test for class [AbstractBookcaseService].
 *
 * @author Vladimir Hromada
 */
class AbstractBookcaseServiceTest : AbstractServiceTest<Movable>() {

    override val cacheKey: String = "data"

    override val itemClass: Class<Movable> = Movable::class.java

    /**
     * Instance of [JpaRepository]
     */
    @Mock
    private val repository: JpaRepository<Movable, Int>? = null

    override fun getRepository(): JpaRepository<Movable, Int> {
        assertThat(repository).isNotNull

        return repository!!
    }

    override fun getBookcaseService(): BookcaseService<Movable> {
        return AbstractBookcaseServiceStub(repository, cache, cacheKey)
    }

    override fun getItem1(): Movable {
        return MovableStub(1, 0)
    }

    override fun getItem2(): Movable {
        return MovableStub(2, 1)
    }

    override fun getAddItem(): Movable {
        return MovableStub(null, 4)
    }

    override fun getCopyItem(): Movable {
        return MovableStub(10, 10)
    }

    override fun assertDataDeepEquals(expected: Movable, actual: Movable) {
        assertSoftly { softly ->
            softly.assertThat(expected).isNotNull
            softly.assertThat(actual).isNotNull
        }
        assertSoftly { softly ->
            softly.assertThat(actual.id).isEqualTo(expected.id)
            softly.assertThat(actual.position).isEqualTo(expected.position)
        }
    }

    /**
     * A class represents abstract bookcase service stub.
     */
    private inner class AbstractBookcaseServiceStub(repository: JpaRepository<Movable, Int>?, cache: Cache?, key: String) : AbstractBookcaseService<Movable>(repository!!, cache!!, key) {

        override fun getCopy(data: Movable): Movable {
            return getCopyItem()
        }

    }

    /**
     * A class represents movable stub.
     */
    private inner class MovableStub(override var id: Int?, override var position: Int) : Movable

}

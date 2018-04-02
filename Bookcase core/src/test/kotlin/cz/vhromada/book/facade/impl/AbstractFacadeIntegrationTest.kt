package cz.vhromada.book.facade.impl

import cz.vhromada.book.CoreTestConfiguration
import cz.vhromada.book.common.Movable
import cz.vhromada.book.facade.BookcaseFacade
import cz.vhromada.result.Event
import cz.vhromada.result.Severity
import cz.vhromada.result.Status
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * An abstract class represents integration test for facade.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CoreTestConfiguration::class])
abstract class AbstractFacadeIntegrationTest<T : Movable, U : Movable> {

    /**
     * Default count of data
     */
    protected abstract val defaultDataCount: Int

    /**
     * Returns name of entity.
     *
     * @return name of entity
     */
    protected abstract val name: String

    /**
     * Prefix for validation keys
     */
    private val prefix get() = name.toUpperCase()

    /**
     * Event for data with null ID
     */
    private val nullDataIdEvent get() = Event(Severity.ERROR, prefix + "_ID_NULL", "ID mustn't be null.")

    /**
     * Event for not existing data
     */
    private val notExistingDataEvent get() = Event(Severity.ERROR, prefix + "_NOT_EXIST", "$name doesn't exist.")

    /**
     * Test method for [BookcaseFacade.newData].
     */
    @Test
    @DirtiesContext
    fun newData() {
        clearReferencedData()

        val result = getBookcaseFacade().newData()

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.OK)
            softly.assertThat(result.events).isEmpty()
        }

        assertThat(getRepositoryDataCount()).isEqualTo(0)
    }

    /**
     * Test method for [BookcaseFacade.getAll].
     */
    @Test
    fun getAll() {
        val result = getBookcaseFacade().getAll()

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.OK)
            assertDataListDeepEquals(result.data, getDataList())
            softly.assertThat(result.events).isEmpty()
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.get].
     */
    @Test
    fun get() {
        for (i in 1..defaultDataCount) {
            val result = getBookcaseFacade().get(i)

            assertSoftly { softly ->
                softly.assertThat(result.status).isEqualTo(Status.OK)
                assertDataDeepEquals(result.data, getDomainData(i))
                softly.assertThat(result.events).isEmpty()
            }
        }

        val result = getBookcaseFacade().get(Int.MAX_VALUE)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.OK)
            softly.assertThat(result.data).isNull()
            softly.assertThat(result.events).isEmpty()
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.add].
     */
    @Test
    @DirtiesContext
    fun add() {
        val result = getBookcaseFacade().add(newData(null))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.OK)
            softly.assertThat(result.events).isEmpty()
        }

        assertDataDomainDeepEquals(newDomainData(defaultDataCount + 1), getRepositoryData(defaultDataCount + 1)!!)
        assertThat(getRepositoryDataCount()).isEqualTo(defaultDataCount + 1)
    }

    /**
     * Test method for [BookcaseFacade.add] with data with not null ID.
     */
    @Test
    fun add_NotNullId() {
        val result = getBookcaseFacade().add(newData(1))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(Event(Severity.ERROR, prefix + "_ID_NOT_NULL", "ID must be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.update].
     */
    @Test
    @DirtiesContext
    fun update() {
        val data = newData(1)

        val result = getBookcaseFacade().update(data)

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.OK)
            softly.assertThat(result.events).isEmpty()
        }

        assertDataDeepEquals(data, getRepositoryData(1)!!)
        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.update] with data with null ID.
     */
    @Test
    fun update_NullId() {
        val result = getBookcaseFacade().update(newData(null))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(nullDataIdEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.update] with data with bad ID.
     */
    @Test
    fun update_BadId() {
        val result = getBookcaseFacade().update(newData(Int.MAX_VALUE))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(notExistingDataEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.remove].
     */
    @Test
    @DirtiesContext
    fun remove() {
        clearReferencedData()

        val result = getBookcaseFacade().remove(newData(1))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.OK)
            softly.assertThat(result.events).isEmpty()
        }

        assertThat(getRepositoryData(1)).isNull()
        assertThat(getRepositoryDataCount()).isEqualTo(defaultDataCount - 1)
    }

    /**
     * Test method for [BookcaseFacade.remove] with data with null ID.
     */
    @Test
    fun remove_NullId() {
        val result = getBookcaseFacade().remove(newData(null))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(nullDataIdEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.remove] with data with bad ID.
     */
    @Test
    fun remove_BadId() {
        val result = getBookcaseFacade().remove(newData(Int.MAX_VALUE))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(notExistingDataEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.duplicate].
     */
    @Test
    @DirtiesContext
    fun duplicate() {
        val result = getBookcaseFacade().duplicate(newData(defaultDataCount))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.OK)
            softly.assertThat(result.events).isEmpty()
        }

        assertDataDomainDeepEquals(getExpectedDuplicatedData(), getRepositoryData(defaultDataCount + 1)!!)
        assertThat(getRepositoryDataCount()).isEqualTo(defaultDataCount + 1)
    }

    /**
     * Test method for [BookcaseFacade.duplicate] with data with bad ID.
     */
    @Test
    fun duplicate_BadId() {
        val result = getBookcaseFacade().duplicate(newData(Int.MAX_VALUE))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(notExistingDataEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.moveUp].
     */
    @Test
    @DirtiesContext
    fun moveUp() {
        val result = getBookcaseFacade().moveUp(newData(2))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.OK)
            softly.assertThat(result.events).isEmpty()
        }

        val data1 = getDomainData(1)
        data1.position = 1
        val data2 = getDomainData(2)
        data2.position = 0
        assertDataDomainDeepEquals(data1, getRepositoryData(1)!!)
        assertDataDomainDeepEquals(data2, getRepositoryData(2)!!)
        for (i in 3..defaultDataCount) {
            assertDataDomainDeepEquals(getDomainData(i), getRepositoryData(i)!!)
        }
        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.moveUp] with data with null ID.
     */
    @Test
    fun moveUp_NullId() {
        val result = getBookcaseFacade().moveUp(newData(null))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(nullDataIdEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.moveUp] with not movable data.
     */
    @Test
    fun moveUp_NotMovableData() {
        val result = getBookcaseFacade().moveUp(newData(1))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(Event(Severity.ERROR, prefix + "_NOT_MOVABLE", "$name can't be moved up.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.moveUp] with data with bad ID.
     */
    @Test
    fun moveUp_BadId() {
        val result = getBookcaseFacade().moveUp(newData(Int.MAX_VALUE))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(notExistingDataEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.moveDown].
     */
    @Test
    @DirtiesContext
    fun moveDown() {
        val result = getBookcaseFacade().moveDown(newData(1))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.OK)
            softly.assertThat(result.events).isEmpty()
        }

        val data1 = getDomainData(1)
        data1.position = 1
        val data2 = getDomainData(2)
        data2.position = 0
        assertDataDomainDeepEquals(data1, getRepositoryData(1)!!)
        assertDataDomainDeepEquals(data2, getRepositoryData(2)!!)
        for (i in 3..defaultDataCount) {
            assertDataDomainDeepEquals(getDomainData(i), getRepositoryData(i)!!)
        }
        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.moveDown] with data with null ID.
     */
    @Test
    fun moveDown_NullId() {
        val result = getBookcaseFacade().moveDown(newData(null))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(nullDataIdEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.moveDown] with not movable data.
     */
    @Test
    fun moveDown_NotMovableData() {
        val result = getBookcaseFacade().moveDown(newData(defaultDataCount))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(Event(Severity.ERROR, prefix + "_NOT_MOVABLE", "$name can't be moved down.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.moveDown] with data with bad ID.
     */
    @Test
    fun moveDown_BadId() {
        val result = getBookcaseFacade().moveDown(newData(Int.MAX_VALUE))

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.ERROR)
            softly.assertThat(result.events).isEqualTo(listOf(notExistingDataEvent))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [BookcaseFacade.updatePositions].
     */
    @Test
    @DirtiesContext
    fun updatePositions() {
        val result = getBookcaseFacade().updatePositions()

        assertSoftly { softly ->
            softly.assertThat(result.status).isEqualTo(Status.OK)
            softly.assertThat(result.events).isEmpty()
        }

        for (i in 1..defaultDataCount) {
            assertDataDomainDeepEquals(getDomainData(i), getRepositoryData(i)!!)
        }
        assertDefaultRepositoryData()
    }

    /**
     * Returns facade for bookcase.
     *
     * @return facade for bookcase
     */
    protected abstract fun getBookcaseFacade(): BookcaseFacade<T>

    /**
     * Returns repository count of data.
     *
     * @return repository count of data
     */
    protected abstract fun getRepositoryDataCount(): Int

    /**
     * Returns list of data.
     *
     * @return list of data
     */
    protected abstract fun getDataList(): List<U>

    /**
     * Returns domain data.
     *
     * @param index index
     * @return domain data
     */
    protected abstract fun getDomainData(index: Int): U

    /**
     * Returns new data.
     *
     * @param id ID
     * @return new data
     */
    protected abstract fun newData(id: Int?): T

    /**
     * Returns domain data.
     *
     * @param id ID
     * @return domain data
     */
    protected abstract fun newDomainData(id: Int): U

    /**
     * Returns repository data.
     *
     * @param id ID
     * @return repository data
     */
    protected abstract fun getRepositoryData(id: Int): U?

    /**
     * Clears referenced data.
     */
    protected abstract fun clearReferencedData()

    /**
     * Asserts list of data deep equals.
     *
     * @param expected expected
     * @param actual   actual
     */
    protected abstract fun assertDataListDeepEquals(expected: List<T>, actual: List<U>)

    /**
     * Asserts data deep equals.
     *
     * @param expected expected
     * @param actual   actual
     */
    protected abstract fun assertDataDeepEquals(expected: T, actual: U)

    /**
     * Asserts domain data deep equals.
     *
     * @param expected expected
     * @param actual   actual
     */
    protected abstract fun assertDataDomainDeepEquals(expected: U, actual: U)

    /**
     * Asserts default repository data.
     */
    protected fun assertDefaultRepositoryData() {
        assertThat(getRepositoryDataCount()).isEqualTo(defaultDataCount)
    }

    /**
     * Returns expected duplicated data.
     *
     * @return expected duplicated data
     */
    private fun getExpectedDuplicatedData(): U {
        val data = getDomainData(defaultDataCount)
        data.id = defaultDataCount + 1

        return data
    }

}

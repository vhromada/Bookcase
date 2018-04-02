package cz.vhromada.book.service.impl

import cz.vhromada.book.common.Movable
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.stub.RepositoryStub
import cz.vhromada.book.stub.impl.CacheStub
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.cache.Cache
import org.springframework.cache.support.SimpleValueWrapper
import org.springframework.data.jpa.repository.JpaRepository

/**
 * An abstract class represents test for service.
 *
 * @author Vladimir Hromada
 */
abstract class AbstractServiceTest<T : Movable> {

    /**
     * Instance of [Cache]
     */
    protected val cache = CacheStub()

    /**
     * Cache key
     */
    protected abstract val cacheKey: String

    /**
     * Returns item class.
     *
     * @return item class
     */
    protected abstract val itemClass: Class<T>

    /**
     * ID
     */
    private val id = 5

    /**
     * Instance of [BookcaseService]
     */
    private lateinit var bookcaseService: BookcaseService<T>

    /**
     * Instance of [JpaRepository]
     */
    private lateinit var repository: RepositoryStub<T>

    /**
     * Data list
     */
    private lateinit var dataList: List<T>

    /**
     * Initializes data.
     */
    @BeforeEach
    fun setUp() {
        repository = getRepository()
        bookcaseService = getBookcaseService()
        dataList = mutableListOf(getItem1(), getItem2())

        repository.findAllResult = dataList
    }

    /**
     * Test method for [BookcaseService.newData].
     */
    @Test
    fun newData_CachedData() {
        bookcaseService.newData()

        repository.verifyDeleteAll()
        cache.verifyClear()
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.getAll] with cached data.
     */
    @Test
    fun getAll_CachedData() {
        cache.getResult = SimpleValueWrapper(dataList)

        val data = bookcaseService.getAll()

        assertThat(data).isEqualTo(dataList)

        cache.verifyGet(cacheKey)
        cache.verifyNoMoreInteractions()
        repository.verifyZeroInteractions()
    }

    /**
     * Test method for [BookcaseService.getAll] with not cached data.
     */
    @Test
    fun getAll_NotCachedData() {
        val data = bookcaseService.getAll()

        assertThat(data).isEqualTo(dataList)

        repository.verifyFindAll()
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.get] with cached existing data.
     */
    @Test
    fun get_CachedExistingData() {
        cache.getResult = SimpleValueWrapper(dataList)

        val data = bookcaseService.get(dataList[0].id!!)

        assertThat(data).isEqualTo(dataList[0])

        cache.verifyGet(cacheKey)
        cache.verifyNoMoreInteractions()
        repository.verifyZeroInteractions()
    }

    /**
     * Test method for [BookcaseService.get] with cached not existing data.
     */
    @Test
    fun get_CachedNotExistingData() {
        cache.getResult = SimpleValueWrapper(dataList)

        val data = bookcaseService.get(Int.MAX_VALUE)

        assertThat(data).isNull()

        cache.verifyGet(cacheKey)
        cache.verifyNoMoreInteractions()
        repository.verifyZeroInteractions()
    }

    /**
     * Test method for [BookcaseService.get] with not cached existing data.
     */
    @Test
    fun get_NotCachedExistingData() {
        val data = bookcaseService.get(dataList[0].id!!)

        assertThat(data).isEqualTo(dataList[0])

        repository.verifyFindAll()
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.get] with not cached not existing data.
     */
    @Test
    fun get_NotCachedNotExistingData() {
        val data = bookcaseService.get(Int.MAX_VALUE)

        assertThat(data).isNull()

        repository.verifyFindAll()
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.add] with cached data.
     */
    @Test
    fun add_CachedData() {
        val data = getAddItem()
        repository.id = id
        cache.getResult = SimpleValueWrapper(dataList)

        bookcaseService.add(data)

        assertAddResult(data)

        repository.verifySave(data)
        repository.verifySave(data)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.add] with not cached data.
     */
    @Test
    fun add_NotCachedData() {
        val data = getAddItem()
        repository.id = id

        bookcaseService.add(data)

        assertAddResult(data)

        repository.verifyFindAll()
        repository.verifySave(data)
        repository.verifySave(data)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.update] with cached data.
     */
    @Test
    fun update_CachedData() {
        val data = dataList[0]
        data.position = 10
        cache.getResult = SimpleValueWrapper(dataList)

        bookcaseService.update(data)

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(2)
            softly.assertThat(dataList[0]).isEqualTo(data)
        }

        repository.verifySave(data)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.update] with not cached data.
     */
    @Test
    fun update_NotCachedData() {
        val data = dataList[0]
        data.position = 10

        bookcaseService.update(data)

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(2)
            softly.assertThat(dataList[0]).isEqualTo(data)
        }

        repository.verifyFindAll()
        repository.verifySave(data)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.remove] with cached data.
     */
    @Test
    fun remove_CachedData() {
        val data = dataList[0]
        cache.getResult = SimpleValueWrapper(dataList)

        bookcaseService.remove(data)

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(1)
            softly.assertThat(dataList.contains(data)).isFalse
        }

        repository.verifyDelete(data)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.remove] with not cached data.
     */
    @Test
    fun remove_NotCachedData() {
        val data = dataList[0]

        bookcaseService.remove(data)

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(1)
            softly.assertThat(dataList.contains(data)).isFalse
        }

        repository.verifyFindAll()
        repository.verifyDelete(data)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.duplicate] with cached data.
     */
    @Test
    fun duplicate_CachedData() {
        val copy = getCopyItem()
        cache.getResult = SimpleValueWrapper(dataList)

        bookcaseService.duplicate(dataList[0])

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(3)
            assertDataDeepEquals(copy, dataList[2])
        }

        val copyArgument = repository.verifySave()
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()

        assertDataDeepEquals(copy, copyArgument)
    }

    /**
     * Test method for [BookcaseService.duplicate] with not cached data.
     */
    @Test
    fun duplicate_NotCachedData() {
        val copy = getCopyItem()

        bookcaseService.duplicate(dataList[0])

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(3)
            assertDataDeepEquals(copy, dataList[2])
        }

        repository.verifyFindAll()
        val copyArgument = repository.verifySave()
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()

        assertDataDeepEquals(copy, copyArgument)
    }

    /**
     * Test method for [BookcaseService.moveUp] with cached data.
     */
    @Test
    fun moveUp_CachedData() {
        val data1 = dataList[0]
        val position1 = data1.position
        val data2 = dataList[1]
        val position2 = data2.position
        cache.getResult = SimpleValueWrapper(dataList)

        bookcaseService.moveUp(data2)

        assertSoftly { softly ->
            softly.assertThat(data1.position).isEqualTo(position2)
            softly.assertThat(data2.position).isEqualTo(position1)
        }

        repository.verifySave(data1)
        repository.verifySave(data2)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.moveUp] with not cached data.
     */
    @Test
    fun moveUp_NotCachedData() {
        val data1 = dataList[0]
        val position1 = data1.position
        val data2 = dataList[1]
        val position2 = data2.position

        bookcaseService.moveUp(data2)

        assertSoftly { softly ->
            softly.assertThat(data1.position).isEqualTo(position2)
            softly.assertThat(data2.position).isEqualTo(position1)
        }

        repository.verifyFindAll()
        repository.verifySave(data1)
        repository.verifySave(data2)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.moveDown] with cached data.
     */
    @Test
    fun moveDown_CachedData() {
        val data1 = dataList[0]
        val position1 = data1.position
        val data2 = dataList[1]
        val position2 = data2.position
        cache.getResult = SimpleValueWrapper(dataList)

        bookcaseService.moveDown(data1)

        assertSoftly { softly ->
            softly.assertThat(data1.position).isEqualTo(position2)
            softly.assertThat(data2.position).isEqualTo(position1)
        }

        repository.verifySave(data1)
        repository.verifySave(data2)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.moveDown] with not cached data.
     */
    @Test
    fun moveDown_NotCachedData() {
        val data1 = dataList[0]
        val position1 = data1.position
        val data2 = dataList[1]
        val position2 = data2.position

        bookcaseService.moveDown(data1)

        assertSoftly { softly ->
            softly.assertThat(data1.position).isEqualTo(position2)
            softly.assertThat(data2.position).isEqualTo(position1)
        }

        repository.verifyFindAll()
        repository.verifySave(data1)
        repository.verifySave(data2)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.updatePositions] with cached data.
     */
    @Test
    fun updatePositions_CachedData() {
        cache.getResult = SimpleValueWrapper(dataList)

        bookcaseService.updatePositions()

        for (i in dataList.indices) {
            val data = dataList[i]
            assertThat(data.position).isEqualTo(i)
        }

        repository.verifySaveAll(dataList)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseService.updatePositions] with not cached data.
     */
    @Test
    fun updatePositions_NotCachedData() {
        bookcaseService.updatePositions()

        for (i in dataList.indices) {
            val data = dataList[i]
            assertThat(data.position).isEqualTo(i)
        }

        repository.verifyFindAll()
        repository.verifySaveAll(dataList)
        cache.verifyGet(cacheKey)
        cache.verifyPut(cacheKey, dataList)
        repository.verifyNoMoreInteractions()
        cache.verifyNoMoreInteractions()
    }

    /**
     * Returns instance of [RepositoryStub].
     *
     * @return instance of [RepositoryStub]
     */
    protected abstract fun getRepository(): RepositoryStub<T>

    /**
     * Returns instance of [BookcaseService].
     *
     * @return instance of [BookcaseService]
     */
    protected abstract fun getBookcaseService(): BookcaseService<T>

    /**
     * Returns 1st item in data list.
     *
     * @return 1st item in data list
     */
    protected abstract fun getItem1(): T

    /**
     * Returns 2nd item in data list.
     *
     * @return 2nd item in data list
     */
    protected abstract fun getItem2(): T

    /**
     * Returns add item
     *
     * @return add item
     */
    protected abstract fun getAddItem(): T

    /**
     * Returns copy item.
     *
     * @return copy item
     */
    protected abstract fun getCopyItem(): T

    /**
     * Asserts data deep equals.
     *
     * @param expected expected data
     * @param actual   actual data
     */
    protected abstract fun assertDataDeepEquals(expected: T, actual: T)

    /**
     * Asserts result of [BookcaseService.add]
     *
     * @param data add item
     */
    private fun assertAddResult(data: T) {
        assertSoftly { softly ->
            softly.assertThat(data.id).isEqualTo(id)
            softly.assertThat(data.position).isEqualTo(id - 1)
            softly.assertThat(dataList.size).isEqualTo(3)
            softly.assertThat(dataList[2]).isEqualTo(data)
        }
    }

}

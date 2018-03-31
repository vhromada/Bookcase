package cz.vhromada.book.service.impl

import cz.vhromada.book.common.Movable
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.test.MockitoExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.stubbing.Answer
import org.springframework.cache.Cache
import org.springframework.cache.support.SimpleValueWrapper
import org.springframework.data.jpa.repository.JpaRepository

/**
 * An abstract class represents test for service.
 *
 * @param <T> type of data
 * @author Vladimir Hromada
 */
@ExtendWith(MockitoExtension::class)
abstract class AbstractServiceTest<T : Movable> {

    /**
     * ID
     */
    val id = 5

    /**
     * Instance of [Cache]
     */
    @Mock
    protected val cache: Cache? = null

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
     * Instance of [BookcaseService]
     */
    private lateinit var bookcaseService: BookcaseService<T>

    /**
     * Instance of [JpaRepository]
     */
    private lateinit var repository: JpaRepository<T, Int>

    /**
     * Data list
     */
    private lateinit var dataList: List<T>

    /**
     * Initializes data.
     */
    @BeforeEach
    fun setUp() {
        assertThat(cache).isNotNull

        repository = getRepository()
        bookcaseService = getBookcaseService()
        dataList = mutableListOf(getItem1(), getItem2())

        `when`(repository.findAll()).thenReturn(dataList)
    }

    /**
     * Test method for [BookcaseService.newData].
     */
    @Test
    fun newData_CachedData() {
        bookcaseService.newData()

        verify<JpaRepository<T, Int>>(repository).deleteAll()
        verify<Cache>(cache).clear()
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.getAll] with cached data.
     */
    @Test
    fun getAll_CachedData() {
        `when`(cache!!.get(any(String::class.java))).thenReturn(SimpleValueWrapper(dataList))

        val data = bookcaseService.getAll()

        assertThat(data).isEqualTo(dataList)

        verify(cache).get(cacheKey)
        verifyNoMoreInteractions(cache)
        verifyZeroInteractions(repository)
    }

    /**
     * Test method for [BookcaseService.getAll] with not cached data.
     */
    @Test
    fun getAll_NotCachedData() {
        `when`(cache!!.get(any(String::class.java))).thenReturn(null)

        val data = bookcaseService.getAll()

        assertThat(data).isEqualTo(dataList)

        verify<JpaRepository<T, Int>>(repository).findAll()
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.get] with cached existing data.
     */
    @Test
    fun get_CachedExistingData() {
        `when`(cache!!.get(any(String::class.java))).thenReturn(SimpleValueWrapper(dataList))

        val data = bookcaseService.get(dataList[0].id!!)

        assertThat(data).isEqualTo(dataList[0])

        verify(cache).get(cacheKey)
        verifyNoMoreInteractions(cache)
        verifyZeroInteractions(repository)
    }

    /**
     * Test method for [BookcaseService.get] with cached not existing data.
     */
    @Test
    fun get_CachedNotExistingData() {
        `when`(cache!!.get(any(String::class.java))).thenReturn(SimpleValueWrapper(dataList))

        val data = bookcaseService.get(Integer.MAX_VALUE)

        assertThat(data).isNull()

        verify(cache).get(cacheKey)
        verifyNoMoreInteractions(cache)
        verifyZeroInteractions(repository)
    }

    /**
     * Test method for [BookcaseService.get] with not cached existing data.
     */
    @Test
    fun get_NotCachedExistingData() {
        `when`(cache!!.get(any(String::class.java))).thenReturn(null)

        val data = bookcaseService.get(dataList[0].id!!)

        assertThat(data).isEqualTo(dataList[0])

        verify<JpaRepository<T, Int>>(repository).findAll()
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.get] with not cached not existing data.
     */
    @Test
    fun get_NotCachedNotExistingData() {
        `when`(cache!!.get(any(String::class.java))).thenReturn(null)

        val data = bookcaseService.get(Integer.MAX_VALUE)

        assertThat(data).isNull()

        verify<JpaRepository<T, Int>>(repository).findAll()
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.add] with cached data.
     */
    @Test
    fun add_CachedData() {
        val data = getAddItem()

        `when`(repository.save(any(itemClass))).thenAnswer(setId())
        `when`(cache!!.get(any(String::class.java))).thenReturn(SimpleValueWrapper(dataList))

        bookcaseService.add(data)

        assertAddResult(data)

        verify<JpaRepository<T, Int>>(repository, times(2)).save(data)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.add] with not cached data.
     */
    @Test
    fun add_NotCachedData() {
        val data = getAddItem()

        `when`(repository.save(any(itemClass))).thenAnswer(setId())
        `when`(cache!!.get(any(String::class.java))).thenReturn(null)

        bookcaseService.add(data)

        assertAddResult(data)

        verify<JpaRepository<T, Int>>(repository).findAll()
        verify<JpaRepository<T, Int>>(repository, times(2)).save(data)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.update] with cached data.
     */
    @Test
    fun update_CachedData() {
        val data = dataList[0]
        data.position = 10

        `when`(repository.save(any(itemClass))).thenAnswer { invocation -> invocation.arguments[0] }
        `when`(cache!!.get(any(String::class.java))).thenReturn(SimpleValueWrapper(dataList))

        bookcaseService.update(data)

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(2)
            softly.assertThat(dataList[0]).isEqualTo(data)
        }

        verify<JpaRepository<T, Int>>(repository).save(data)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.update] with not cached data.
     */
    @Test
    fun update_NotCachedData() {
        val data = dataList[0]
        data.position = 10

        `when`(repository.save(any(itemClass))).thenAnswer { invocation -> invocation.arguments[0] }
        `when`(cache!!.get(any(String::class.java))).thenReturn(null)

        bookcaseService.update(data)

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(2)
            softly.assertThat(dataList[0]).isEqualTo(data)
        }

        verify<JpaRepository<T, Int>>(repository).findAll()
        verify<JpaRepository<T, Int>>(repository).save(data)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.remove] with cached data.
     */
    @Test
    fun remove_CachedData() {
        val data = dataList[0]

        `when`(cache!!.get(any(String::class.java))).thenReturn(SimpleValueWrapper(dataList))

        bookcaseService.remove(data)

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(1)
            softly.assertThat(dataList.contains(data)).isFalse
        }

        verify<JpaRepository<T, Int>>(repository).delete(data)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.remove] with not cached data.
     */
    @Test
    fun remove_NotCachedData() {
        val data = dataList[0]

        `when`(cache!!.get(any(String::class.java))).thenReturn(null)

        bookcaseService.remove(data)

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(1)
            softly.assertThat(dataList.contains(data)).isFalse
        }

        verify<JpaRepository<T, Int>>(repository).findAll()
        verify<JpaRepository<T, Int>>(repository).delete(data)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.duplicate] with cached data.
     */
    @Test
    fun duplicate_CachedData() {
        val copy = getCopyItem()
        val copyArgumentCaptor = ArgumentCaptor.forClass(itemClass)

        `when`(repository.save(any(itemClass))).thenAnswer { invocation -> invocation.arguments[0] }
        `when`(cache!!.get(any(String::class.java))).thenReturn(SimpleValueWrapper(dataList))

        bookcaseService.duplicate(dataList[0])

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(3)
            assertDataDeepEquals(copy, dataList[2])
        }

        verify<JpaRepository<T, Int>>(repository).save(copyArgumentCaptor.capture())
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)

        val copyArgument = copyArgumentCaptor.value
        assertDataDeepEquals(copy, copyArgument)
    }

    /**
     * Test method for [BookcaseService.duplicate] with not cached data.
     */
    @Test
    fun duplicate_NotCachedData() {
        val copy = getCopyItem()
        val copyArgumentCaptor = ArgumentCaptor.forClass(itemClass)

        `when`(repository.save(any(itemClass))).thenAnswer { invocation -> invocation.arguments[0] }
        `when`(cache!!.get(any(String::class.java))).thenReturn(null)

        bookcaseService.duplicate(dataList[0])

        assertSoftly { softly ->
            softly.assertThat(dataList.size).isEqualTo(3)
            assertDataDeepEquals(copy, dataList[2])
        }

        verify<JpaRepository<T, Int>>(repository).findAll()
        verify<JpaRepository<T, Int>>(repository).save(copyArgumentCaptor.capture())
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)

        val copyArgument = copyArgumentCaptor.value
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

        `when`(repository.save(any(itemClass))).thenAnswer { invocation -> invocation.arguments[0] }
        `when`(cache!!.get(any(String::class.java))).thenReturn(SimpleValueWrapper(dataList))

        bookcaseService.moveUp(data2)

        assertSoftly { softly ->
            softly.assertThat(data1.position).isEqualTo(position2)
            softly.assertThat(data2.position).isEqualTo(position1)
        }

        verify<JpaRepository<T, Int>>(repository).save(data1)
        verify<JpaRepository<T, Int>>(repository).save(data2)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
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

        `when`(repository.save(any(itemClass))).thenAnswer { invocation -> invocation.arguments[0] }
        `when`(cache!!.get(any(String::class.java))).thenReturn(null)

        bookcaseService.moveUp(data2)

        assertSoftly { softly ->
            softly.assertThat(data1.position).isEqualTo(position2)
            softly.assertThat(data2.position).isEqualTo(position1)
        }

        verify<JpaRepository<T, Int>>(repository).findAll()
        verify<JpaRepository<T, Int>>(repository).save(data1)
        verify<JpaRepository<T, Int>>(repository).save(data2)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
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

        `when`(repository.save(any(itemClass))).thenAnswer { invocation -> invocation.arguments[0] }
        `when`(cache!!.get(any(String::class.java))).thenReturn(SimpleValueWrapper(dataList))

        bookcaseService.moveDown(data1)

        assertSoftly { softly ->
            softly.assertThat(data1.position).isEqualTo(position2)
            softly.assertThat(data2.position).isEqualTo(position1)
        }

        verify<JpaRepository<T, Int>>(repository).save(data1)
        verify<JpaRepository<T, Int>>(repository).save(data2)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
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

        `when`(repository.save(any(itemClass))).thenAnswer { invocation -> invocation.arguments[0] }
        `when`(cache!!.get(any(String::class.java))).thenReturn(null)

        bookcaseService.moveDown(data1)

        assertSoftly { softly ->
            softly.assertThat(data1.position).isEqualTo(position2)
            softly.assertThat(data2.position).isEqualTo(position1)
        }

        verify<JpaRepository<T, Int>>(repository).findAll()
        verify<JpaRepository<T, Int>>(repository).save(data1)
        verify<JpaRepository<T, Int>>(repository).save(data2)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.updatePositions] with cached data.
     */
    @Test
    fun updatePositions_CachedData() {
        `when`(repository.saveAll(dataList)).thenAnswer { invocation -> invocation.arguments[0] }
        `when`(cache!!.get(any(String::class.java))).thenReturn(SimpleValueWrapper(dataList))

        bookcaseService.updatePositions()

        for (i in dataList.indices) {
            val data = dataList[i]
            assertThat(data.position).isEqualTo(i)
        }

        verify<JpaRepository<T, Int>>(repository).saveAll(dataList)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Test method for [BookcaseService.updatePositions] with not cached data.
     */
    @Test
    fun updatePositions_NotCachedData() {
        `when`(repository.saveAll(dataList)).thenAnswer { invocation -> invocation.arguments[0] }
        `when`(cache!!.get(any(String::class.java))).thenReturn(null)

        bookcaseService.updatePositions()

        for (i in dataList.indices) {
            val data = dataList[i]
            assertThat(data.position).isEqualTo(i)
        }

        verify<JpaRepository<T, Int>>(repository).findAll()
        verify<JpaRepository<T, Int>>(repository).saveAll(dataList)
        verify(cache).get(cacheKey)
        verify(cache).put(cacheKey, dataList)
        verifyNoMoreInteractions(repository, cache)
    }

    /**
     * Returns instance of [JpaRepository].
     *
     * @return instance of [JpaRepository]
     */
    protected abstract fun getRepository(): JpaRepository<T, Int>

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
     * Sets ID.
     *
     * @return mocked answer
     */
    private fun setId(): Answer<T> {
        return Answer { invocation ->
            @Suppress("UNCHECKED_CAST")
            val movable: T = invocation.arguments[0] as T
            movable.id = id
            return@Answer movable
        }
    }

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

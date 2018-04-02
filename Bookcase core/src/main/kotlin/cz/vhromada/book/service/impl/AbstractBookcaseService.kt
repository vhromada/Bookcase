package cz.vhromada.book.service.impl

import cz.vhromada.book.common.Movable
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.utils.CollectionUtils
import org.springframework.cache.Cache
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

/**
 * An abstract class represents service for data.
 *
 * @author Vladimir Hromada
 */
@Transactional
abstract class AbstractBookcaseService<T : Movable>(

    /**
     * Repository for data
     */
    private val repository: JpaRepository<T, Int>,

    /**
     * Cache for data
     */
    private val cache: Cache,

    /**
     * Cache key
     */
    private val key: String) : BookcaseService<T> {

    override fun newData() {
        repository.deleteAll()

        cache.clear()
    }

    @Transactional(readOnly = true)
    override fun getAll(): List<T> {
        return CollectionUtils.getSortedData(getCachedData(true))
    }

    @Transactional(readOnly = true)
    override fun get(id: Int): T? {
        return getCachedData(true).firstOrNull { id == it.id }
    }

    override fun add(data: T) {
        val savedData = repository.save(data)
        savedData.position = savedData.id!! - 1
        repository.save(savedData)

        val dataList = getCachedData(false)
        dataList.add(savedData)
        cache.put(key, dataList)
    }

    override fun update(data: T) {
        val savedData = repository.save(data)

        val dataList = getCachedData(false)
        updateItem(dataList, savedData)
        cache.put(key, dataList)
    }

    override fun remove(data: T) {
        repository.delete(data)

        val dataList = getCachedData(false)
        dataList.remove(data)
        cache.put(key, dataList)
    }

    override fun duplicate(data: T) {
        val savedDataCopy = repository.save(getCopy(data))

        val dataList = getCachedData(false)
        dataList.add(savedDataCopy)
        cache.put(key, dataList)
    }

    override fun moveUp(data: T) {
        move(data, true)
    }

    override fun moveDown(data: T) {
        move(data, false)
    }

    override fun updatePositions() {
        val data = CollectionUtils.getSortedData(getCachedData(false))
        updatePositions(data)

        val savedData = repository.saveAll(data)

        cache.put(key, savedData)
    }

    /**
     * Returns copy of data.
     *
     * @param data data
     * @return copy of data
     */
    protected abstract fun getCopy(data: T): T

    /**
     * Updates positions.
     *
     * @param data data
     */
    protected fun updatePositions(data: List<T>) {
        for (i in data.indices) {
            data[i].position = i
        }
    }

    /**
     * Returns list of data.
     *
     * @param cached true if returned data from repository should be cached
     * @return list of data
     */
    private fun getCachedData(cached: Boolean): MutableList<T> {
        val cacheValue = cache.get(key)
        if (cacheValue == null) {
            val data = repository.findAll()
            if (cached) {
                cache.put(key, data)
            }

            return data
        }

        @Suppress("UNCHECKED_CAST")
        return cacheValue.get() as MutableList<T>
    }

    /**
     * Moves data in list one position up or down.
     *
     * @param data data
     * @param up   if moving data up
     * @throws IllegalArgumentException if data is null
     */
    private fun move(data: T, up: Boolean) {
        val dataList = CollectionUtils.getSortedData(getCachedData(false))
        var index = dataList.indexOf(data)
        if (up) {
            index--
        } else {
            index++
        }
        val other = dataList[index]
        val position = data.position
        data.position = other.position
        other.position = position

        val savedData = repository.save(data)
        val savedOther = repository.save(other)

        updateItem(dataList, savedData)
        updateItem(dataList, savedOther)
        cache.put(key, dataList)
    }

    /**
     * Updates item if list of data.
     *
     * @param data list of data
     * @param item updating item
     */
    private fun updateItem(data: MutableList<T>, item: T) {
        val index = data.indexOf(item)
        data.remove(item)
        data.add(index, item)
    }

}

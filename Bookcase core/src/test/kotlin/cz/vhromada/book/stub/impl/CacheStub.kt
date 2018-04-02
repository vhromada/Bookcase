package cz.vhromada.book.stub.impl

import cz.vhromada.book.stub.BookcaseStub
import org.springframework.cache.Cache
import java.util.concurrent.Callable

/**
 * A class represents implementation of stub for cache.
 *
 * @author Vladimir Hromada
 */
class CacheStub : Cache, BookcaseStub {

    /**
     * Result for [Cache.get].
     */
    var getResult: Cache.ValueWrapper? = null

    /**
     * Data for [Cache.get]
     */
    private var getData = ArrayList<Any>()

    /**
     * Data for [Cache.put]
     */
    private var putData = HashMap<Any, Any?>()

    /**
     * Data for [Cache.clear]
     */
    private var clearData = 0

    /**
     * Count of interactions
     */
    private var interactions = 0

    override fun getName(): String {
        throw UnsupportedOperationException("Method getName is not supported.")
    }

    override fun getNativeCache(): Any {
        throw UnsupportedOperationException("Method getNativeCache is not supported.")
    }

    override fun get(key: Any): Cache.ValueWrapper? {
        getData.add(key)
        interactions++
        return getResult
    }

    override fun <T : Any?> get(key: Any, type: Class<T>?): T? {
        throw UnsupportedOperationException("Method get(Any, Class) is not supported.")
    }

    override fun <T : Any?> get(key: Any, valueLoader: Callable<T>): T? {
        throw UnsupportedOperationException("Method get(Any, Callable) is not supported.")
    }

    override fun put(key: Any, value: Any?) {
        putData[key] = value
        interactions++
    }

    override fun putIfAbsent(key: Any, value: Any?): Cache.ValueWrapper? {
        throw UnsupportedOperationException("Method putIfAbsent is not supported.")
    }

    override fun clear() {
        clearData++
        interactions++
    }

    override fun evict(key: Any) {
        throw UnsupportedOperationException("Method evict is not supported.")
    }

    /**
     * Verifies calling [Cache.get].
     *
     * @param key key
     */
    fun verifyGet(key: Any) {
        if (!getData.remove(key)) {
            throw AssertionError("Method get for $key was not called.")
        }
    }

    /**
     * Verifies calling [Cache.put].
     *
     * @param key   key
     * @param value value
     */
    fun verifyPut(key: Any, value: Any?) {
        if (!putData.containsKey(key)) {
            throw AssertionError("Method put for $key was not called.")
        }
        val putDataValue = putData[key]
        if (putDataValue == value) {
            putData.remove(key)
        }
        if (putDataValue!! != value) {
            throw AssertionError("Method put for $key and $value was not called.")
        }
    }

    /**
     * Verifies calling [Cache.clear].
     */
    fun verifyClear() {
        if (clearData <= 0) {
            throw AssertionError("Method clear was not called.")
        }
        clearData--
    }

    override fun verifyNoMoreInteractions() {
        if (interactions == 0) {
            throw AssertionError("Cache has never been called.")
        }
        if (getData.isNotEmpty()) {
            throw AssertionError("Method get was called for $getData.")
        }
        if (putData.isNotEmpty()) {
            throw AssertionError("Method put was called for $putData.")
        }
        if (clearData > 0) {
            throw AssertionError("Method clear was called $clearData x.")
        }
    }

    override fun verifyZeroInteractions() {
        if (interactions > 0) {
            throw AssertionError("Cache has been called.")
        }
    }

}

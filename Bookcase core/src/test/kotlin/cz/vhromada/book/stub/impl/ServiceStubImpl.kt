package cz.vhromada.book.stub.impl

import cz.vhromada.book.common.Movable
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.stub.ServiceStub

/**
 * A class represents implementation of stub for service for bookcase.
 *
 * @author Vladimir Hromada
 */
class ServiceStubImpl<T : Movable> : ServiceStub<T> {

    override var getAllResult = emptyList<T>()

    override var getResult: T? = null;

    /**
     * Data for [BookcaseService.getAll]
     */
    private var getAllData = 0

    /**
     * Data for [BookcaseService.get]
     */
    private val getData = mutableListOf<Int>()

    /**
     * Count of interactions
     */
    private var interactions = 0

    override fun newData() {
        throw UnsupportedOperationException("Method newData is not supported.")
    }

    override fun getAll(): List<T> {
        getAllData++
        interactions++
        return getAllResult
    }

    override fun get(id: Int): T? {
        getData.add(id)
        interactions++
        return getResult
    }

    override fun add(data: T) {
        throw UnsupportedOperationException("Method add is not supported.")
    }

    override fun update(data: T) {
        throw UnsupportedOperationException("Method update is not supported.")
    }

    override fun remove(data: T) {
        throw UnsupportedOperationException("Method remove is not supported.")
    }

    override fun duplicate(data: T) {
        throw UnsupportedOperationException("Method duplicate is not supported.")
    }

    override fun moveUp(data: T) {
        throw UnsupportedOperationException("Method moveUp is not supported.")
    }

    override fun moveDown(data: T) {
        throw UnsupportedOperationException("Method moveDown is not supported.")
    }

    override fun updatePositions() {
        throw UnsupportedOperationException("Method updatePositions is not supported.")
    }

    override fun verifyGetAll() {
        if (getAllData <= 0) {
            throw AssertionError("Method getAll was not called.")
        }
        getAllData--
    }

    override fun verifyGet(id: Int) {
        if (!getData.remove(id)) {
            throw AssertionError("Method get for $id was not called.")
        }
    }

    override fun verifyNoMoreInteractions() {
        if (interactions == 0) {
            throw AssertionError("Service has never been called.")
        }
        if (getAllData > 0) {
            throw AssertionError("Method getAll was called $getAllData x.")
        }
        if (getData.isNotEmpty()) {
            throw AssertionError("Method get was called for $getData.")
        }
    }

    override fun verifyZeroInteractions() {
        if (interactions > 0) {
            throw AssertionError("Service has been called.")
        }
    }

}

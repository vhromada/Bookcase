package cz.vhromada.book.stub

import cz.vhromada.book.common.Movable
import cz.vhromada.book.service.BookcaseService

/**
 * An interface represents stub for service for bookcase.
 *
 * @author Vladimir Hromada
 */
interface ServiceStub<T : Movable> : BookcaseStub, BookcaseService<T> {

    /**
     * Result for [BookcaseService.getAll]
     */
    var getAllResult: List<T>

    /**
     * Result for [BookcaseService.get]
     */
    var getResult: T?

    /**
     * Verifies calling [BookcaseService.getAll].
     */
    fun verifyGetAll()

    /**
     * Verifies calling [BookcaseService.get].
     *
     * @param id ID
     */
    fun verifyGet(id: Int)

}

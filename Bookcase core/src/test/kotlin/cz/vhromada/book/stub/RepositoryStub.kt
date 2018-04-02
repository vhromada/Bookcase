package cz.vhromada.book.stub

import cz.vhromada.book.common.Movable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * An interface represents stub for repository for bookcase.
 *
 * @author Vladimir Hromada
 */
interface RepositoryStub<T : Movable> : BookcaseStub, JpaRepository<T, Int> {

    /**
     * Result for [JpaRepository.findAll]
     */
    var findAllResult: List<T>

    /**
     * ID
     */
    var id: Int?

    /**
     * Verifies calling [JpaRepository.findAll].
     */
    fun verifyFindAll()

    /**
     * Verifies calling [JpaRepository.save].
     *
     * @param entity entity
     */
    fun verifySave(entity: T)

    /**
     * Verifies calling [JpaRepository.save].
     *
     * @return called entity
     */
    fun verifySave(): T

    /**
     * Verifies calling [JpaRepository.saveAll].
     *
     * @param entities entities
     */
    fun verifySaveAll(entities: List<T>)

    /**
     * Verifies calling [JpaRepository.delete].
     *
     * @param entity entity
     */
    fun verifyDelete(entity: T)

    /**
     * Verifies calling [JpaRepository.deleteAll].
     */
    fun verifyDeleteAll()

}

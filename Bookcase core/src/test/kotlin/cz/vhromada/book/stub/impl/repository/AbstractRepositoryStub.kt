package cz.vhromada.book.stub.impl.repository

import cz.vhromada.book.common.Movable
import cz.vhromada.book.stub.RepositoryStub
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

/**
 * An abstract class represents stub for repository for bookcase.
 *
 * @author Vladimir Hromada
 */
abstract class AbstractRepositoryStub<T : Movable> : RepositoryStub<T> {

    override var findAllResult = emptyList<T>()

    override var id: Int? = null

    /**
     * Data for [JpaRepository.findAll]
     */
    private var findAllData = 0

    /**
     * Data for [JpaRepository.save]
     */
    private var saveData = ArrayList<T>()

    /**
     * Data for [JpaRepository.saveAll]
     */
    private var saveAllData = ArrayList<Iterable<T>>()

    /**
     * Data for [JpaRepository.delete]
     */
    private var deleteData = ArrayList<T>()

    /**
     * Data for [JpaRepository.deleteAll]
     */
    private var deleteAllData = 0

    /**
     * Count of interactions
     */
    private var interactions = 0

    override fun findAll(): List<T> {
        findAllData++
        interactions++
        return findAllResult
    }

    override fun findAll(sort: Sort): List<T> {
        throw UnsupportedOperationException("Method findAll(Sort) is not supported.")
    }

    override fun <S : T> findAll(example: Example<S>): List<S> {
        throw UnsupportedOperationException("Method findAll(Example) is not supported.")
    }

    override fun <S : T> findAll(example: Example<S>, sort: Sort): List<S> {
        throw UnsupportedOperationException("Method findAll(Example, Sort) is not supported.")
    }

    override fun findAll(pageable: Pageable): Page<T> {
        throw UnsupportedOperationException("Method findAll(Pageable) is not supported.")
    }

    override fun <S : T> findAll(example: Example<S>, pageable: Pageable): Page<S> {
        throw UnsupportedOperationException("Method findAll(Example, Pageable) is not supported.")
    }

    override fun findAllById(ids: Iterable<Int>): List<T> {
        throw UnsupportedOperationException("Method findAllById is not supported.")
    }

    override fun <S : T> findOne(example: Example<S>): Optional<S> {
        throw UnsupportedOperationException("Method findOne is not supported.")
    }

    override fun findById(id: Int): Optional<T> {
        throw UnsupportedOperationException("Method findById is not supported.")
    }

    override fun getOne(id: Int): T {
        throw UnsupportedOperationException("Method getOne is not supported.")
    }

    override fun <S : T> exists(example: Example<S>): Boolean {
        throw UnsupportedOperationException("Method exists is not supported.")
    }

    override fun existsById(id: Int): Boolean {
        throw UnsupportedOperationException("Method existsById is not supported.")
    }

    override fun count(): Long {
        throw UnsupportedOperationException("Method count is not supported.")
    }

    override fun <S : T> count(example: Example<S>): Long {
        throw UnsupportedOperationException("Method count(Example) is not supported.")
    }

    override fun <S : T> save(entity: S): S {
        saveData.add(entity)
        interactions++
        if (id != null) {
            entity.id = id
        }
        return entity
    }

    override fun <S : T> saveAll(entities: Iterable<S>): List<S> {
        saveAllData.add(entities)
        interactions++
        return entities as List<S>
    }

    override fun <S : T> saveAndFlush(entity: S): S {
        throw UnsupportedOperationException("Method saveAndFlush is not supported.")
    }

    override fun flush() {
        throw UnsupportedOperationException("Method flush is not supported.")
    }

    override fun delete(entity: T) {
        deleteData.add(entity)
        interactions++
    }

    override fun deleteById(id: Int) {
        throw UnsupportedOperationException("Method deleteById is not supported.")
    }

    override fun deleteAll() {
        deleteAllData++
        interactions++
    }

    override fun deleteAll(entities: Iterable<T>) {
        throw UnsupportedOperationException("Method deleteAll(Iterable) is not supported.")
    }

    override fun deleteInBatch(entities: Iterable<T>) {
        throw UnsupportedOperationException("Method deleteInBatch is not supported.")
    }

    override fun deleteAllInBatch() {
        throw UnsupportedOperationException("Method deleteAllInBatch is not supported.")
    }

    override fun verifyFindAll() {
        if (findAllData <= 0) {
            throw AssertionError("Method findAll was not called.")
        }
        findAllData--
    }

    override fun verifySave(entity: T) {
        if (!saveData.remove(entity)) {
            throw AssertionError("Method save for $entity was not called.")
        }
    }

    override fun verifySave(): T {
        if (saveData.size != 1) {
            throw AssertionError("Method save not called only once.")
        }
        return saveData.removeAt(0)
    }

    override fun verifySaveAll(entities: List<T>) {
        if (!saveAllData.remove(entities)) {
            throw AssertionError("Method saveAll for $entities was not called.")
        }
    }

    override fun verifyDelete(entity: T) {
        if (!deleteData.remove(entity)) {
            throw AssertionError("Method delete for $entity was not called.")
        }
    }

    override fun verifyDeleteAll() {
        if (deleteAllData <= 0) {
            throw AssertionError("Method deleteAll was not called.")
        }
        deleteAllData--
    }

    override fun verifyNoMoreInteractions() {
        if (interactions == 0) {
            throw AssertionError("Repository has never been called.")
        }
        if (findAllData > 0) {
            throw AssertionError("Method findAll was called $findAllData x.")
        }
        if (saveData.isNotEmpty()) {
            throw AssertionError("Method save was called for $saveData.")
        }
        if (saveAllData.isNotEmpty()) {
            throw AssertionError("Method saveAll was called for $saveAllData.")
        }
        if (deleteData.isNotEmpty()) {
            throw AssertionError("Method delete was called for $deleteData.")
        }
        if (deleteAllData > 0) {
            throw AssertionError("Method deleteAll was called $deleteAllData x.")
        }
    }

    override fun verifyZeroInteractions() {
        if (interactions > 0) {
            throw AssertionError("Repository has been called.")
        }
    }

}

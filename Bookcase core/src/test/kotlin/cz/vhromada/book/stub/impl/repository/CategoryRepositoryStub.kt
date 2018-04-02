package cz.vhromada.book.stub.impl.repository

import cz.vhromada.book.domain.Category
import cz.vhromada.book.repository.CategoryRepository
import java.util.Optional

/**
 * A class represents stub for repository for categories.
 *
 * @author Vladimir Hromada
 */
class CategoryRepositoryStub : CategoryRepository, AbstractRepositoryStub<Category>() {

    override fun deleteById(id: Int) {
        super.deleteById(id)
    }

    override fun getOne(id: Int): Category {
        return super.getOne(id)
    }

    override fun existsById(id: Int): Boolean {
        return super.existsById(id)
    }

    override fun findById(id: Int): Optional<Category> {
        return super.findById(id)
    }

}

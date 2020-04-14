package cz.vhromada.bookcase.service

import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import cz.vhromada.bookcase.domain.Category
import cz.vhromada.bookcase.repository.CategoryRepository
import cz.vhromada.bookcase.utils.CategoryUtils
import cz.vhromada.common.provider.AccountProvider
import cz.vhromada.common.provider.TimeProvider
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.test.service.MovableServiceTest
import cz.vhromada.common.test.utils.TestConstants
import org.mockito.Mock
import org.springframework.data.jpa.repository.JpaRepository

/**
 * A class represents test for class [CategoryService].
 *
 * @author Vladimir Hromada
 */
class CategoryServiceTest : MovableServiceTest<Category>() {

    /**
     * Instance of [CategoryRepository]
     */
    @Mock
    private lateinit var repository: CategoryRepository

    /**
     * Instance of [AccountProvider]
     */
    @Mock
    private lateinit var accountProvider: AccountProvider

    /**
     * Instance of [TimeProvider]
     */
    @Mock
    private lateinit var timeProvider: TimeProvider

    override fun getRepository(): JpaRepository<Category, Int> {
        return repository
    }

    override fun getAccountProvider(): AccountProvider {
        return accountProvider
    }

    override fun getTimeProvider(): TimeProvider {
        return timeProvider
    }

    override fun getService(): MovableService<Category> {
        return CategoryService(repository, accountProvider, timeProvider, cache)
    }

    override fun getCacheKey(): String {
        return "categories${TestConstants.ACCOUNT.id}"
    }

    override fun getItem1(): Category {
        return CategoryUtils.newCategoryDomain(1)
    }

    override fun getItem2(): Category {
        return CategoryUtils.newCategoryDomain(2)
    }

    override fun getAddItem(): Category {
        return CategoryUtils.newCategoryDomain(null)
    }

    override fun getCopyItem(): Category {
        return CategoryUtils.newCategoryDomain(null)
                .copy(position = 0)
    }

    override fun initAllDataMock(data: List<Category>) {
        whenever(repository.findByAuditCreatedUser(any())).thenReturn(data)
    }

    override fun verifyAllDataMock() {
        verify(repository).findByAuditCreatedUser(TestConstants.ACCOUNT_ID)
        verifyNoMoreInteractions(repository)
    }

    override fun anyItem(): Category {
        return any()
    }

    override fun argumentCaptorItem(): KArgumentCaptor<Category> {
        return argumentCaptor()
    }

    override fun assertDataDeepEquals(expected: Category, actual: Category) {
        CategoryUtils.assertCategoryDeepEquals(expected, actual)
    }

}

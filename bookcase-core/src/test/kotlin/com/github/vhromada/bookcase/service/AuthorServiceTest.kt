package com.github.vhromada.bookcase.service

import com.github.vhromada.bookcase.domain.Author
import com.github.vhromada.bookcase.repository.AuthorRepository
import com.github.vhromada.bookcase.utils.AuthorUtils
import com.github.vhromada.common.provider.AccountProvider
import com.github.vhromada.common.provider.TimeProvider
import com.github.vhromada.common.service.MovableService
import com.github.vhromada.common.test.service.MovableServiceTest
import com.github.vhromada.common.test.utils.TestConstants
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.mockito.Mock
import org.springframework.data.jpa.repository.JpaRepository

/**
 * A class represents test for class [AuthorService].
 *
 * @author Vladimir Hromada
 */
class AuthorServiceTest : MovableServiceTest<Author>() {

    /**
     * Instance of [AuthorRepository]
     */
    @Mock
    private lateinit var repository: AuthorRepository

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

    override fun getRepository(): JpaRepository<Author, Int> {
        return repository
    }

    override fun getAccountProvider(): AccountProvider {
        return accountProvider
    }

    override fun getTimeProvider(): TimeProvider {
        return timeProvider
    }

    override fun getService(): MovableService<Author> {
        return AuthorService(repository, accountProvider, timeProvider, cache)
    }

    override fun getCacheKey(): String {
        return "authors${TestConstants.ACCOUNT.id}"
    }

    override fun getItem1(): Author {
        return AuthorUtils.newAuthorDomain(1)
    }

    override fun getItem2(): Author {
        return AuthorUtils.newAuthorDomain(2)
    }

    override fun getAddItem(): Author {
        return AuthorUtils.newAuthorDomain(null)
    }

    override fun getCopyItem(): Author {
        return AuthorUtils.newAuthorDomain(null)
                .copy(position = 0)
    }

    override fun initAllDataMock(data: List<Author>) {
        whenever(repository.findByAuditCreatedUser(any())).thenReturn(data)
    }

    override fun verifyAllDataMock() {
        verify(repository).findByAuditCreatedUser(TestConstants.ACCOUNT_UUID)
        verifyNoMoreInteractions(repository)
    }

    override fun anyItem(): Author {
        return any()
    }

    override fun argumentCaptorItem(): KArgumentCaptor<Author> {
        return argumentCaptor()
    }

    override fun assertDataDeepEquals(expected: Author, actual: Author) {
        AuthorUtils.assertAuthorDeepEquals(expected, actual)
    }

}

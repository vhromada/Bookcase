package com.github.vhromada.bookcase.service

import com.github.vhromada.bookcase.domain.Book
import com.github.vhromada.bookcase.repository.BookRepository
import com.github.vhromada.bookcase.utils.BookUtils
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
 * A class represents test for class [BookService].
 *
 * @author Vladimir Hromada
 */
class BookServiceTest : MovableServiceTest<Book>() {

    /**
     * Instance of [BookRepository]
     */
    @Mock
    private lateinit var repository: BookRepository

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

    override fun getRepository(): JpaRepository<Book, Int> {
        return repository
    }

    override fun getAccountProvider(): AccountProvider {
        return accountProvider
    }

    override fun getTimeProvider(): TimeProvider {
        return timeProvider
    }

    override fun getService(): MovableService<Book> {
        return BookService(repository, accountProvider, timeProvider, cache)
    }

    override fun getCacheKey(): String {
        return "books${TestConstants.ACCOUNT.id}"
    }

    override fun getItem1(): Book {
        return BookUtils.newBookDomain(1)
    }

    override fun getItem2(): Book {
        return BookUtils.newBookDomain(2)
    }

    override fun getAddItem(): Book {
        return BookUtils.newBookDomain(null)
    }

    override fun getCopyItem(): Book {
        return BookUtils.newBookDomain(1)
                .copy(id = null)
    }

    override fun initAllDataMock(data: List<Book>) {
        whenever(repository.findByAuditCreatedUser(any())).thenReturn(data)
    }

    override fun verifyAllDataMock() {
        verify(repository).findByAuditCreatedUser(TestConstants.ACCOUNT_UUID)
        verifyNoMoreInteractions(repository)
    }

    override fun anyItem(): Book {
        return any()
    }

    override fun argumentCaptorItem(): KArgumentCaptor<Book> {
        return argumentCaptor()
    }

    override fun assertDataDeepEquals(expected: Book, actual: Book) {
        BookUtils.assertBookDeepEquals(expected, actual)
    }

}

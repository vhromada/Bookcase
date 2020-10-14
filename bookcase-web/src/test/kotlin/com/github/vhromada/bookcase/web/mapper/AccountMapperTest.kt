package com.github.vhromada.bookcase.web.mapper

import com.github.vhromada.bookcase.web.BookcaseMapperTestConfiguration
import com.github.vhromada.bookcase.web.common.AccountUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for accounts.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [BookcaseMapperTestConfiguration::class])
class AccountMapperTest {

    /**
     * Mapper for accounts
     */
    @Autowired
    private lateinit var mapper: AccountMapper

    /**
     * Test method for [AccountMapper.map].
     */
    @Test
    fun map() {
        val account = AccountUtils.getAccount()

        val credentials = mapper.map(account)

        AccountUtils.assertAccountDeepEquals(account, credentials)
    }

}

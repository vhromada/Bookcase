package com.github.vhromada.bookcase.web.mapper.impl

import com.github.vhromada.bookcase.web.fo.AccountFO
import com.github.vhromada.bookcase.web.mapper.AccountMapper
import com.github.vhromada.common.account.entity.Credentials
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for accounts.
 *
 * @author Vladimir Hromada
 */
@Component("webAccountMapper")
class AccountMapperImpl : AccountMapper {

    override fun map(source: AccountFO): Credentials {
        return Credentials(username = source.username,
                password = source.password)
    }

}

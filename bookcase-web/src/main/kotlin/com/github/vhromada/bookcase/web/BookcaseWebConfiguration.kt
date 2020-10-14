package com.github.vhromada.bookcase.web

import com.github.vhromada.bookcase.BookcaseConfiguration
import com.github.vhromada.common.entity.Account
import com.github.vhromada.common.provider.AccountProvider
import com.github.vhromada.common.provider.TimeProvider
import com.github.vhromada.common.provider.UuidProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.LocalDateTime
import java.util.UUID

/**
 * A class represents Spring configuration for bookcase.
 *
 * @author Vladimir Hromada
 */
@Configuration
@Import(BookcaseConfiguration::class)
class BookcaseWebConfiguration : WebMvcConfigurer {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun accountProvider(): AccountProvider {
        return object : AccountProvider {

            override fun getAccount(): Account {
                return SecurityContextHolder.getContext().authentication.principal as Account
            }

        }
    }

    @Bean
    fun timeProvider(): TimeProvider {
        return object : TimeProvider {

            override fun getTime(): LocalDateTime {
                return LocalDateTime.now()
            }

        }
    }

    @Bean
    fun uuidProvider(): UuidProvider {
        return object : UuidProvider {

            override fun getUuid(): String {
                return UUID.randomUUID().toString()
            }

        }
    }

}

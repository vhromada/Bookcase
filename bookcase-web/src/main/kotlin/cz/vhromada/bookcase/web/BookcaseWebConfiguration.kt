package cz.vhromada.bookcase.web

import cz.vhromada.bookcase.CoreConfiguration
import cz.vhromada.common.entity.Account
import cz.vhromada.common.provider.AccountProvider
import cz.vhromada.common.provider.TimeProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.LocalDateTime

/**
 * A class represents Spring configuration for bookcase.
 *
 * @author Vladimir Hromada
 */
@Configuration
@Import(CoreConfiguration::class)
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

}

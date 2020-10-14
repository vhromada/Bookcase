package com.github.vhromada.bookcase

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * A class represents Spring configuration for bookcase.
 *
 * @author Vladimir Hromada
 */
@Configuration
@ComponentScan("com.github.vhromada.bookcase")
@EnableAspectJAutoProxy
@EnableCaching
@EnableJpaRepositories("com.github.vhromada.bookcase.repository")
@EntityScan("com.github.vhromada.bookcase.domain")
class BookcaseConfiguration

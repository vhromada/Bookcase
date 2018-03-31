package cz.vhromada.book

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * A class represents Spring configuration.
 *
 * @author Vladimir Hromada
 */
@Configuration
@ComponentScan("cz.vhromada.book")
@EnableJpaRepositories("cz.vhromada.book.repository")
@EntityScan("cz.vhromada.book.domain")
@EnableCaching
class CoreConfiguration

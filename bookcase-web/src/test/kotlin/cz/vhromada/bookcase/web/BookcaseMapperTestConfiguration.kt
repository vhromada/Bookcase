package cz.vhromada.bookcase.web

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * A class represents Spring configuration for tests.
 *
 * @author Vladimir Hromada
 */
@Configuration
@ComponentScan("cz.vhromada.bookcase.web.mapper")
class BookcaseMapperTestConfiguration

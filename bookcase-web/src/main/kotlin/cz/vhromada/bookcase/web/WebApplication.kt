package cz.vhromada.bookcase.web

import cz.vhromada.bookcase.CoreConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * A class represents Spring boot application.
 *
 * @author Vladimir Hromada
 */
@SpringBootApplication
@Import(CoreConfiguration::class)
class WebApplication : WebMvcConfigurer

fun main(args: Array<String>) {
    SpringApplication.run(WebApplication::class.java, *args)
}

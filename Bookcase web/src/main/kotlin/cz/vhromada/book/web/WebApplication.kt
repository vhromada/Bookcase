package cz.vhromada.book.web

import cz.vhromada.book.CoreConfiguration
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

/**
 * Main method.
 *
 * @param args the command line arguments
 */
fun main(args: Array<String>) {
    SpringApplication.run(WebApplication::class.java, *args)
}

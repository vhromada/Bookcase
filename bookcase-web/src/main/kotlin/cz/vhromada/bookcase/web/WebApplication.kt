package cz.vhromada.bookcase.web

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * A class represents Spring boot application.
 *
 * @author Vladimir Hromada
 */
@SpringBootApplication
class WebApplication

fun main(args: Array<String>) {
    SpringApplication.run(WebApplication::class.java, *args)
}

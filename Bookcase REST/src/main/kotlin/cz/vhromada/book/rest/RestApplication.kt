package cz.vhromada.book.rest

import cz.vhromada.book.CoreConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * A class represents Spring boot application.
 *
 * @author Vladimir Hromada
 */
@SpringBootApplication
@EnableSwagger2
@Import(CoreConfiguration::class)
class RestApplication {

    @Bean
    fun applicationApi(): Docket {
        val info = ApiInfoBuilder()
            .title("Bookcase")
            .description("Bookcase")
            .version("1.0.0")
            .build()
        return Docket(DocumentationType.SWAGGER_2)
            .groupName("bookcase")
            .apiInfo(info)
            .select()
            .paths(PathSelectors.regex("/bookcase.*"))
            .build()
    }

}

/**
 * Main method.
 *
 * @param args the command line arguments
 */
fun main(args: Array<String>) {
    SpringApplication.run(RestApplication::class.java, *args)
}

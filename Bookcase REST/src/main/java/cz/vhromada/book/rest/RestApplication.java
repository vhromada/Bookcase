package cz.vhromada.book.rest;

import cz.vhromada.book.CoreConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * A class represents Spring boot application.
 *
 * @author Vladimir Hromada
 */
@SpringBootApplication
@EnableSwagger2
@Import(CoreConfiguration.class)
public class RestApplication {

    /**
     * Main method.
     *
     * @param args the command line arguments
     */
    //CHECKSTYLE.OFF: UncommentedMain
    public static void main(final String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
    //CHECKSTYLE.OFF: UncommentedMain

    @Bean
    public Docket applicationApi() {
        final ApiInfo info = new ApiInfoBuilder()
            .title("Bookcase")
            .description("Bookcase")
            .version("1.0.0")
            .build();
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("bookcase")
            .apiInfo(info)
            .select()
            .paths(PathSelectors.regex("/bookcase.*"))
            .build();
    }

}

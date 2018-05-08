package cz.vhromada.book.web;

import cz.vhromada.book.CoreConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * A class represents Spring boot application.
 *
 * @author Vladimir Hromada
 */
//CHECKSTYLE.OFF: HideUtilityClassConstructor
@SpringBootApplication
@Import(CoreConfiguration.class)
public class WebApplication implements WebMvcConfigurer {

    /**
     * Main method.
     *
     * @param args the command line arguments
     */
    //CHECKSTYLE.OFF: UncommentedMain
    public static void main(final String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
    //CHECKSTYLE.ON: UncommentedMain

}
//CHECKSTYLE.ON: HideUtilityClassConstructor

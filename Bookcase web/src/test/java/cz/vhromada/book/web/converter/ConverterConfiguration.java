package cz.vhromada.book.web.converter;

import cz.vhromada.converter.orika.OrikaConfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * A class represents Spring configuration for tests.
 *
 * @author Vladimir Hromada
 */
@Configuration
@ComponentScan("cz.vhromada.book.web.converter")
@Import(OrikaConfiguration.class)
public class ConverterConfiguration {
}

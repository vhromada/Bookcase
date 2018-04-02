package cz.vhromada.book

import cz.vhromada.book.converter.AuthorConverter
import cz.vhromada.book.converter.BookConverter
import cz.vhromada.book.converter.CategoryConverter
import cz.vhromada.book.converter.DomainAuthorConverter
import cz.vhromada.book.converter.DomainBookConverter
import cz.vhromada.book.converter.DomainCategoryConverter
import cz.vhromada.converter.Converter
import cz.vhromada.converter.spring.SpringConverter
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ConversionServiceFactoryBean
import org.springframework.core.convert.ConversionService
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
class CoreConfiguration {

    @Bean
    fun conversionService(authorConverter: AuthorConverter, domainAuthorConverter: DomainAuthorConverter,
                          categoryConverter: CategoryConverter, domainCategoryConverter: DomainCategoryConverter,
                          bookConverter: BookConverter, domainBookConverter: DomainBookConverter): ConversionService? {
        val conversionServiceFactoryBean = ConversionServiceFactoryBean()
        conversionServiceFactoryBean.setConverters(mutableSetOf(authorConverter, domainAuthorConverter, categoryConverter, domainCategoryConverter, bookConverter, domainBookConverter))
        conversionServiceFactoryBean.afterPropertiesSet()

        return conversionServiceFactoryBean.`object`
    }

    @Bean
    fun converter(conversionService: ConversionService): Converter {
        return SpringConverter(conversionService)
    }

}

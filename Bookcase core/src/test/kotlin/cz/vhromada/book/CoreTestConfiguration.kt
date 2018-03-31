package cz.vhromada.book

import net.sf.ehcache.CacheManager
import org.springframework.cache.ehcache.EhCacheCacheManager
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.support.SharedEntityManagerBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

/**
 * A class represents Spring configuration for tests.
 *
 * @author Vladimir Hromada
 */
@Configuration
@Import(CoreConfiguration::class)
class CoreTestConfiguration {

    @Bean
    fun dataSource(): DataSource {
        return EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScripts("books.sql", "data.sql")
            .build()
    }

    @Bean
    fun entityManagerFactory(dataSource: DataSource): EntityManagerFactory? {
        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.jpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactoryBean.setPackagesToScan("cz.vhromada.book.domain")
        entityManagerFactoryBean.dataSource = dataSource
        entityManagerFactoryBean.afterPropertiesSet()

        return entityManagerFactoryBean.`object`
    }

    @Bean
    fun containerManagedEntityManager(entityManagerFactory: EntityManagerFactory): SharedEntityManagerBean {
        val entityManagerBean = SharedEntityManagerBean()
        entityManagerBean.entityManagerFactory = entityManagerFactory

        return entityManagerBean
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }

    @Bean
    fun cacheManagerFactory(): CacheManager? {
        val classPathResource = ClassPathResource("ehcache.xml")

        val cacheManagerFactoryBean = EhCacheManagerFactoryBean()
        cacheManagerFactoryBean.setConfigLocation(classPathResource)
        cacheManagerFactoryBean.setShared(true)
        cacheManagerFactoryBean.afterPropertiesSet()

        return cacheManagerFactoryBean.`object`
    }

    @Bean
    fun cacheManager(cacheManagerFactory: CacheManager): EhCacheCacheManager {
        return EhCacheCacheManager(cacheManagerFactory)
    }

}

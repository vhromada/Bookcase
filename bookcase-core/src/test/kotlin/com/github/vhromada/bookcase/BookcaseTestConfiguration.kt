package com.github.vhromada.bookcase

import com.github.vhromada.common.entity.Account
import com.github.vhromada.common.provider.AccountProvider
import com.github.vhromada.common.provider.TimeProvider
import com.github.vhromada.common.test.utils.TestConstants
import net.sf.ehcache.CacheManager
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
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
import java.time.LocalDateTime
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

/**
 * A class represents Spring configuration for tests.
 *
 * @author Vladimir Hromada
 */
@Configuration
@Import(BookcaseConfiguration::class, FlywayAutoConfiguration.FlywayConfiguration::class)
class BookcaseTestConfiguration {

    @Bean
    fun dataSource(): DataSource {
        return EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build()
    }

    @Bean
    fun entityManagerFactory(dataSource: DataSource): EntityManagerFactory? {
        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.jpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactoryBean.setPackagesToScan("com.github.vhromada.bookcase.domain")
        entityManagerFactoryBean.dataSource = dataSource
        entityManagerFactoryBean.afterPropertiesSet()
        return entityManagerFactoryBean.getObject()
    }

    @Bean
    fun containerManagedEntityManager(entityManagerFactory: EntityManagerFactory): SharedEntityManagerBean {
        val entityManagerBean = SharedEntityManagerBean()
        entityManagerBean.entityManagerFactory = entityManagerFactory
        return entityManagerBean
    }

    @Bean
    fun transactionManager(dataSource: DataSource): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory(dataSource)
        return transactionManager
    }

    @Bean
    fun cacheManagerFactory(): CacheManager? {
        val cacheManagerFactoryBean = EhCacheManagerFactoryBean()
        cacheManagerFactoryBean.setConfigLocation(ClassPathResource("ehcache.xml"))
        cacheManagerFactoryBean.setShared(true)
        cacheManagerFactoryBean.afterPropertiesSet()
        return cacheManagerFactoryBean.getObject()
    }

    @Bean
    fun cacheManager(cacheManagerFactory: CacheManager): EhCacheCacheManager {
        val cacheManager = EhCacheCacheManager()
        cacheManager.cacheManager = cacheManagerFactory
        return cacheManager
    }

    @Bean
    fun accountProvider(): AccountProvider {
        return object : AccountProvider {

            override fun getAccount(): Account {
                return TestConstants.ACCOUNT
            }

        }
    }

    @Bean
    fun timeProvider(): TimeProvider {
        return object : TimeProvider {

            override fun getTime(): LocalDateTime {
                return TestConstants.TIME
            }

        }
    }

}

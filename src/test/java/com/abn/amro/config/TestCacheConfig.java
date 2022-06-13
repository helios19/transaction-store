package com.abn.amro.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.abn.amro.transaction.service", "com.abn.amro.transaction.model"})
@EnableAutoConfiguration
@EnableCaching
public class TestCacheConfig {
//    @Bean
//    CacheManager cacheManager() {
//        return new ConcurrentMapCacheManager(ClassUtils.TRANSACTIONS_COLLECTION_NAME);
//    }
}

package com.abn.amro.config;

import com.abn.amro.common.utils.ClassUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.abn.amro.transaction.service", "com.abn.amro.transaction.model"})
@EnableAutoConfiguration
@EnableCaching
public class TestCacheConfig {
    @Bean
    CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(ClassUtils.TRANSACTIONS_COLLECTION_NAME);
    }
}

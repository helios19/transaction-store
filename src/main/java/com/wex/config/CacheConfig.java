package com.wex.config;

import com.wex.common.utils.ClassUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class enabling cache features and creating cache components such as the {@link KeyGenerator}.
 *
 * @see EnableCaching
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    /**
     * Returns a {@link KeyGenerator} instance that can be used by the implementation cache to store elements.
     *
     * @return KeyGenerator instance
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            // This will generate a unique key of the class name, the method name,
            // and all method parameters appended.
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName());
            sb.append(method.getName());
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(ClassUtils.TRANSACTIONS_COLLECTION_NAME, ClassUtils.RATE_EXCHANGE_COLLECTION_NAME);
    }
}

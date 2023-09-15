package com.wex.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.wex.transaction.service", "com.wex.transaction.model"})
@EnableAutoConfiguration
@EnableCaching
public class TestCacheConfig {
}

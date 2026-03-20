package com.kbalazsworks.simple_oidc

import com.kbalazsworks.simple_oidc.factories.OkHttpFactory
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class BeanConfig {
    @Bean
    open fun OkHttpClient() = OkHttpFactory().createOkHttpClient()
}

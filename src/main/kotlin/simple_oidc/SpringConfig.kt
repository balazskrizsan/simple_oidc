package com.kbalazsworks.simple_oidc

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ComponentScan("com.kbalazsworks.simple_oidc")
@EnableConfigurationProperties(ApplicationProperties::class)
@PropertySource("classpath:application.properties")
open class SpringConfig

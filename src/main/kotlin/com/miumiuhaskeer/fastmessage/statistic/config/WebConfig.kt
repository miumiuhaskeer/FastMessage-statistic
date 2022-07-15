package com.miumiuhaskeer.fastmessage.statistic.config

import co.elastic.clients.json.jackson.JacksonJsonpMapper
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class WebConfig {

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()

    /**
     * Enable jackson-datatype-jsr310 module for mapping LocalDateTime class
     * Enable ignoring nullable variables
     *
     * @return objectMapper
     */
    @Bean
    @Primary
    fun objectMapper() = ObjectMapper().apply {
        setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
        registerModule(JavaTimeModule())
    }
}
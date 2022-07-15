package com.miumiuhaskeer.fastmessage.statistic.properties.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "fastmesssage.elasticsearch")
data class ElasticsearchConfig(
    val host: String,
    val port: Int,
    val headers: Headers
) {
    fun getUrl() = "$host:$port"
}

data class Headers (
    val accept: String,
    val contentType: String
)
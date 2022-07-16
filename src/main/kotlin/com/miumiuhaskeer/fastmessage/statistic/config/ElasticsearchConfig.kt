package com.miumiuhaskeer.fastmessage.statistic.config

import com.miumiuhaskeer.fastmessage.statistic.properties.config.ElasticsearchConfig
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import org.springframework.http.HttpHeaders

@Configuration
@EnableElasticsearchRepositories(basePackages = ["com.miumiuhaskeer.fastmessage.statistic.repository"])
class ElasticsearchConfig(
    private val elasticsearchConfig: ElasticsearchConfig
) : ElasticsearchConfiguration() {

    override fun clientConfiguration(): ClientConfiguration {
        val defaultHeaders = HttpHeaders().apply {
            add(HttpHeaders.ACCEPT, elasticsearchConfig.headers.accept)
            add(HttpHeaders.CONTENT_TYPE, elasticsearchConfig.headers.contentType)
        }

        return ClientConfiguration.builder()
            .connectedTo(elasticsearchConfig.getUrl())
            .withDefaultHeaders(defaultHeaders)
            .build()
    }
}
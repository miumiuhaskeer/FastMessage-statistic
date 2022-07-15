package com.miumiuhaskeer.fastmessage.statistic.config

import co.elastic.clients.transport.TransportOptions
import com.miumiuhaskeer.fastmessage.statistic.properties.config.ElasticsearchConfig
import javafx.util.converter.LocalDateTimeStringConverter
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.Jsr310Converters
import org.springframework.data.convert.WritingConverter
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import org.springframework.http.HttpHeaders
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

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
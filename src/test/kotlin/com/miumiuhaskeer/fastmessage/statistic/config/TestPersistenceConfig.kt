package com.miumiuhaskeer.fastmessage.statistic.config

import lombok.RequiredArgsConstructor
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.test.context.TestPropertySource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import java.io.IOException
import java.util.*
import kotlin.streams.toList

@EnableKafka
@TestConfiguration
@RequiredArgsConstructor
@TestPropertySource(locations = ["classpath:application.yml"])
class TestPersistenceConfig(
    private val resourcePatternResolver: ResourcePatternResolver
) {

    @Bean
    @Primary
    fun embeddedDataSource() = EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts(*getInitSqlFiles())
        .build()

    @Bean
    fun localValidatorFactoryBean() = LocalValidatorFactoryBean()

    private fun getInitSqlFiles(): Array<String> {
        val resources: MutableList<String> = ArrayList()

        try {
            val schema = getFilesPathList("sql/schema", "*.sql")
            val data = getFilesPathList("sql/data", "*.sql")

            resources.addAll(schema)
            resources.addAll(data)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return resources.toTypedArray()
    }

    @Throws(IOException::class)
    private fun getFilesPathList(path: String, fileName: String): List<String> {
        val resourcePath = if (path.last() == '/') path else "$path/"
        val locationPattern = "classpath:$resourcePath$fileName"
        val resources = arrayListOf(*resourcePatternResolver.getResources(locationPattern))

        return resources.stream()
            .map { resource ->
                try {
                    return@map resourcePath + resource.file.name
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                ""
            }
            .filter { filePath: String -> filePath.isNotEmpty() }
            .toList()
    }
}
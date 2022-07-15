package com.miumiuhaskeer.fastmessage.statistic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [ElasticsearchDataAutoConfiguration::class])
class FastMessageStatisticApplication

fun main(args: Array<String>) {
    runApplication<FastMessageStatisticApplication>(*args)
}

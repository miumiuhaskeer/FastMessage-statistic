package com.miumiuhaskeer.fastmessage.statistic.model.entity

import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

class Message {

    var chatId: String? = null
    var fromId: Long? = null
    var content: String? = null

    /**
     * Convert ZoneDateTime to LocalDateTime
     */
    @Field(type = FieldType.Date, format = [], pattern = ["uuuu-MM-dd'T'HH:mm:ss.SSS'Z'"])
    var creationDateTime: LocalDateTime? = null
}
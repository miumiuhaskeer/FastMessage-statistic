package com.miumiuhaskeer.fastmessage.statistic.model.entity

import co.elastic.clients.util.DateTime
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime
import java.util.*

class Message {

//    @JsonProperty("id")
//    @Field(name = "document._id")
//    var id: Long? = null

    var chatId: String? = null
    var fromId: Long? = null
    var content: String? = null

    /**
     * Convert ZoneDateTime to LocalDateTime
     */
    @Field(type = FieldType.Date, format = [], pattern = ["uuuu-MM-dd'T'HH:mm:ss.SSS'Z'"])
//    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
//    @JsonSerialize(using = LocalDateTimeSerializer::class)
    var creationDateTime: LocalDateTime? = null
}
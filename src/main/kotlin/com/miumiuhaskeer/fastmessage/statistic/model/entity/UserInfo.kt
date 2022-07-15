package com.miumiuhaskeer.fastmessage.statistic.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.miumiuhaskeer.fastmessage.statistic.model.entity.UserInfo.Companion.DOCUMENT_NAME
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Document(indexName = DOCUMENT_NAME)
class UserInfo {

    @Id
    @JsonIgnore
    var id: String? = null

    var userId: Long? = null
    var email: String? = null
    var messageCount: Int? = null
    var chatCount: Int? = null

    @Field(type = FieldType.Date, format = [DateFormat.date_hour_minute_second_millis], pattern = [])
    var creationDateTime: LocalDateTime? = null

    companion object {
        const val DOCUMENT_NAME = "user_info"
    }
}
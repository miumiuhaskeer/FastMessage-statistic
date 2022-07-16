package com.miumiuhaskeer.fastmessage.statistic.model.entity

import com.miumiuhaskeer.fastmessage.statistic.util.LocalDateTimeUtil
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
    @Field(type = FieldType.Date, format = [], pattern = [LocalDateTimeUtil.ZONED_TO_LOCAL_PATTERN])
    var creationDateTime: LocalDateTime? = null
}
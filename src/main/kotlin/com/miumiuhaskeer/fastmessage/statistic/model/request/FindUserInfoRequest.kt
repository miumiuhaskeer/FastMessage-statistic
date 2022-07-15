package com.miumiuhaskeer.fastmessage.statistic.model.request

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@NoArgsConstructor
@AllArgsConstructor
class FindUserInfoRequest {

    var userId: Long? = null
    var email: String? = null
    var messageCountStart: Int? = null
    var messageCountEnd: Int? = null
    var chatCountStart: Int? = null
    var chatCountEnd: Int? = null

    @Field(type = FieldType.Date, format = [DateFormat.date_hour_minute_second_millis], pattern = [])
    var creationDateTimeStart: LocalDateTime? = null

    @Field(type = FieldType.Date, format = [DateFormat.date_hour_minute_second_millis], pattern = [])
    var creationDateTimeEnd: LocalDateTime? = null

    @NotNull
    @Min(0)
    var page: Int? = 0

    @NotNull
    @Min(1)
    var size: Int? = 10
}
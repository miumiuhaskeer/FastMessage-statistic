package com.miumiuhaskeer.fastmessage.statistic.model.request

import com.miumiuhaskeer.fastmessage.statistic.util.LocalDateTimeUtil
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@NoArgsConstructor
@AllArgsConstructor
class FindMessagesRequest {

    var chatId: String? = null
    var fromId: Long? = null
    var content: String? = null

    @Field(type = FieldType.Date, format = [], pattern = [LocalDateTimeUtil.ZONED_TO_LOCAL_PATTERN])
    var sendTimeStart: LocalDateTime? = null

    @Field(type = FieldType.Date, format = [], pattern = [LocalDateTimeUtil.ZONED_TO_LOCAL_PATTERN])
    var sendTimeEnd: LocalDateTime? = null

    @NotNull
    @Min(0)
    var page: Int? = 0

    @NotNull
    @Min(1)
    var size: Int? = 10
}
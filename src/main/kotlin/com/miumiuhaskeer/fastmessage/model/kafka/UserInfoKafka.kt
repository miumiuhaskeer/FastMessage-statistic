package com.miumiuhaskeer.fastmessage.model.kafka

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@NoArgsConstructor
@AllArgsConstructor
class UserInfoKafka {

    @Email
    var email: String? = null

    @Min(1)
    @Max(100)
    var messageCount: Int? = null

    @Min(1)
    @Max(100)
    var chatCount: Int? = null

    @NotNull
    var creationDateTime: LocalDateTime? = null
}
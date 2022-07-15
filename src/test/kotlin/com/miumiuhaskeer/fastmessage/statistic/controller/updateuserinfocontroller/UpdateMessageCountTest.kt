package com.miumiuhaskeer.fastmessage.statistic.controller.updateuserinfocontroller

import com.miumiuhaskeer.fastmessage.statistic.AbstractKafkaTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UpdateMessageCountTest: AbstractKafkaTest() {

    @Test
    fun simpleTest() {
        val messageCount = 10
        val userId = createUserInfo()

        sendWithSleep(integerKafkaTemplate, "updateUserInfoMessageCount", userId, messageCount)

        val userInfo = userInfoRepository.findByUserId(userId).orElseThrow(::AssertionError)

        assertEquals(messageCount, userInfo.messageCount)
    }
}
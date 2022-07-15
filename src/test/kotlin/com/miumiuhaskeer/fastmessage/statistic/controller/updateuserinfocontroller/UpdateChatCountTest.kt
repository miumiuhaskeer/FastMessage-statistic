package com.miumiuhaskeer.fastmessage.statistic.controller.updateuserinfocontroller

import com.miumiuhaskeer.fastmessage.statistic.AbstractKafkaTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UpdateChatCountTest: AbstractKafkaTest() {

    @Test
    fun simpleTest() {
        val chatCount = 10
        val userId = createUserInfo()

        sendWithSleep(integerKafkaTemplate, "updateUserInfoChatCount", userId, chatCount)

        val userInfo = userInfoRepository.findByUserId(userId).orElseThrow(::AssertionError)

        assertEquals(chatCount, userInfo.chatCount)
    }
}
package com.miumiuhaskeer.fastmessage.statistic.controller.updateuserinfocontroller

import com.miumiuhaskeer.fastmessage.statistic.AbstractKafkaTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateUserInfoTest: AbstractKafkaTest() {

    @Test
    fun simpleTest() {
        val userId = createUserInfo()

        val userInfo = userInfoRepository.findByUserId(userId).orElseThrow(::AssertionError)

        assertEquals(1L, userInfo.userId)
    }
}
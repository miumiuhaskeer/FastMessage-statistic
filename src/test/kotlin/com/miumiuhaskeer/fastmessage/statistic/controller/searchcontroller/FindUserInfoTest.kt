package com.miumiuhaskeer.fastmessage.statistic.controller.searchcontroller

import com.miumiuhaskeer.fastmessage.statistic.AbstractTest
import com.miumiuhaskeer.fastmessage.statistic.MockMvcQuery
import com.miumiuhaskeer.fastmessage.statistic.model.entity.UserInfo
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindUserInfoRequest
import com.miumiuhaskeer.fastmessage.statistic.model.response.FindUserInfoResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicLong

class FindUserInfoTest: AbstractTest() {

    private lateinit var query: MockMvcQuery
    private val userId = AtomicLong(0)

    @BeforeEach
    fun setMockMvcQuery() {
        query = MockMvcQuery.createPostQuery(
            "/find/user/info",
            adminHeader
        )
    }

    @Test
    fun simpleTest() {
        createUserInfo()

        val response = performOkRequest(FindUserInfoRequest())

        assertEquals(1, response.userInfos.size)
    }

    @Test
    fun startDateZeroUsersTest() {
        for (i in 1..3) {
            createUserInfo()
        }

        val response = performOkRequest(FindUserInfoRequest().apply {
            creationDateTimeStart = LocalDateTime.now().plusDays(1)
        })

        assertEquals(0, response.userInfos.size)
    }

    @Test
    fun startDateAllUsersTest() {
        for (i in 1..3) {
            createUserInfo()
        }

        val response = performOkRequest(FindUserInfoRequest().apply {
            creationDateTimeStart = LocalDateTime.now().minusDays(1)
        })

        assertEquals(3, response.userInfos.size)
    }

    @Test
    fun endDateAllUsersTest() {
        for (i in 1..3) {
            createUserInfo()
        }

        val response = performOkRequest(FindUserInfoRequest().apply {
            creationDateTimeEnd = LocalDateTime.now().plusDays(1)
        })

        assertEquals(3, response.userInfos.size)
    }

    @Test
    fun endDateZeroUsersTest() {
        for (i in 1..3) {
            createUserInfo()
        }

        val response = performOkRequest(FindUserInfoRequest().apply {
            creationDateTimeEnd = LocalDateTime.now().minusDays(1)
        })

        assertEquals(0, response.userInfos.size)
    }

    @Test
    fun findByUserIdTest() {
        for (i in 1..3) {
            createUserInfo()
        }

        val response = performOkRequest(FindUserInfoRequest().apply {
            userId = 1
        })

        assertEquals(1, response.userInfos.size)
    }

    @Test
    fun startChatCountZeroUsersTest() {
        for (i in 1..3) {
            createUserInfo()
        }

        val response = performOkRequest(FindUserInfoRequest().apply {
            chatCountStart = 100
        })

        assertEquals(0, response.userInfos.size)
    }

    @Test
    fun startChatCountAllUsersTest() {
        for (i in 1..3) {
            createUserInfo()
        }

        val response = performOkRequest(FindUserInfoRequest().apply {
            chatCountStart = -1
        })

        assertEquals(3, response.userInfos.size)
    }

    @Test
    fun endChatCountAllUsersTest() {
        for (i in 1..3) {
            createUserInfo()
        }

        val response = performOkRequest(FindUserInfoRequest().apply {
            chatCountEnd = 100
        })

        assertEquals(3, response.userInfos.size)
    }

    @Test
    fun endChatCountZeroUsersTest() {
        for (i in 1..3) {
            createUserInfo()
        }

        val response = performOkRequest(FindUserInfoRequest().apply {
            chatCountEnd = -1
        })

        assertEquals(0, response.userInfos.size)
    }

    @Test
    fun multiChatCountTest() {
        createUserInfo(newChatCount = 0)
        createUserInfo(newChatCount = 20)
        createUserInfo(newChatCount = 17)
        createUserInfo(newChatCount = 3)
        createUserInfo(newChatCount = 10)

        val response = performOkRequest(FindUserInfoRequest().apply {
            chatCountStart = 11
            chatCountEnd = 21
        })

        assertEquals(2, response.userInfos.size)
    }

    @Test
    fun multiChatCountStartGreaterTest() {
        createUserInfo(newChatCount = 0)
        createUserInfo(newChatCount = 20)
        createUserInfo(newChatCount = 17)
        createUserInfo(newChatCount = 3)
        createUserInfo(newChatCount = 10)

        val response = performOkRequest(FindUserInfoRequest().apply {
            chatCountStart = 21
            chatCountEnd = 11
        })

        assertEquals(0, response.userInfos.size)
    }

    @Test
    fun negativePageTest() {
        createUserInfo()

        val request = FindUserInfoRequest().apply {
            page = -1
        }

        performBadRequest(query, request)
    }

    @Test
    fun negativeSizeTest() {
        createUserInfo()

        val request = FindUserInfoRequest().apply {
            size = -1
        }

        performBadRequest(query, request)
    }

    @Test
    fun unauthorizedTest() {
        createUserInfo()
        performUnauthorizedRequest(query.apply { authToken = null }, FindUserInfoRequest())
    }

    private fun performOkRequest(request: FindUserInfoRequest) = performOkRequest(query, request, FindUserInfoResponse::class.java)

    private fun createUserInfo(newMessageCount: Int = 0, newChatCount: Int = 0) {
        userInfoRepository.save(UserInfo().apply {
            val newUserId = this@FindUserInfoTest.userId.incrementAndGet()

            userId = newUserId
            email = "testUser$newUserId@mail.ru"
            messageCount = newMessageCount
            chatCount = newChatCount
            creationDateTime = LocalDateTime.now()
        })
    }
}
package com.miumiuhaskeer.fastmessage.statistic.controller.searchcontroller

import com.miumiuhaskeer.fastmessage.statistic.AbstractTest
import com.miumiuhaskeer.fastmessage.statistic.model.entity.Message
import com.miumiuhaskeer.fastmessage.statistic.model.entity.MongoMessage
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindMessagesRequest
import com.miumiuhaskeer.fastmessage.statistic.model.response.FindMessagesResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.shaded.com.google.common.net.HttpHeaders
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicLong

// TODO add check for response body of some query
class FindMessagesTest: AbstractTest() {

    companion object {
        const val MESSAGE = "Hello World!"
    }

    private val userId = AtomicLong(0)

    @Test
    fun simpleTest() {
        createMessage()

        val response = performOkRequest(FindMessagesRequest())

        Assertions.assertEquals(1, response.messages.size)
    }

    @Test
    fun startDateZeroMessagesTest() {
        for (i in 1..3) {
            createMessage()
        }

        val response = performOkRequest(FindMessagesRequest().apply {
            sendTimeStart = LocalDateTime.now().plusDays(1)
        })

        Assertions.assertEquals(0, response.messages.size)
    }

    @Test
    fun startDateAllMessagesTest() {
        for (i in 1..3) {
            createMessage()
        }

        val response = performOkRequest(FindMessagesRequest().apply {
            sendTimeStart = LocalDateTime.now().minusDays(1)
        })

        Assertions.assertEquals(3, response.messages.size)
    }

    @Test
    fun endDateAllMessagesTest() {
        for (i in 1..3) {
            createMessage()
        }

        val response = performOkRequest(FindMessagesRequest().apply {
            sendTimeEnd = LocalDateTime.now().plusDays(1)
        })

        Assertions.assertEquals(3, response.messages.size)
    }

    @Test
    fun endDateZeroMessagesTest() {
        for (i in 1..3) {
            createMessage()
        }

        val response = performOkRequest(FindMessagesRequest().apply {
            sendTimeEnd = LocalDateTime.now().minusDays(1)
        })

        Assertions.assertEquals(0, response.messages.size)
    }

    @Test
    fun findByUserIdTest() {
        for (i in 1..3) {
            createMessage()
        }

        val response = performOkRequest(FindMessagesRequest().apply {
            fromId = 1
        })

        Assertions.assertEquals(1, response.messages.size)
    }

    @Test
    fun findContentEqualsTest() {
        createMessage()

        val response = performOkRequest(FindMessagesRequest().apply {
            content = MESSAGE
        })

        Assertions.assertEquals(1, response.messages.size)
    }

    @Test
    fun findContentMatchTest() {
        createMessage()

        val response = performOkRequest(FindMessagesRequest().apply {
            content = MESSAGE.split(' ')[0]
        })

        Assertions.assertEquals(1, response.messages.size)
    }

    @Test
    fun negativePageTest() {
        createMessage()

        val request = FindMessagesRequest().apply {
            page = -1
        }

        mockMvc.perform(getRequest(request))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun negativeSizeTest() {
        createMessage()

        val request = FindMessagesRequest().apply {
            size = -1
        }

        mockMvc.perform(getRequest(request))
            .andExpect(status().isBadRequest)
    }

    private fun performOkRequest(request: FindMessagesRequest): FindMessagesResponse {
        val mvcResult = mockMvc.perform(getRequest(request))
            .andExpect(status().isOk)
            .andReturn()
        val contentResult = mvcResult.response.contentAsString

        return jsonConverter.fromJson(contentResult, FindMessagesResponse::class.java)
    }

    private fun getRequest(request: FindMessagesRequest, containsAuthHeader: Boolean = true): MockHttpServletRequestBuilder {
        val builder = post("/find/messages")

        if (containsAuthHeader) {
            builder.header(HttpHeaders.AUTHORIZATION, adminHeader)
        }

        return builder.contentType(MediaType.APPLICATION_JSON)
            .content(jsonConverter.toJsonSafe(request))
    }

    private fun createMessage() {
        val mongoMessage = MongoMessage().apply {
            document = Message().apply {
                val newUserId = userId.incrementAndGet()

                chatId = "some_chat_id"
                fromId = newUserId
                content = MESSAGE
                creationDateTime = LocalDateTime.now()
            }
        }

        messageSearchRepository.save(mongoMessage)
    }
}
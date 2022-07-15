package com.miumiuhaskeer.fastmessage.statistic.controller

import com.miumiuhaskeer.fastmessage.statistic.AbstractTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HealthCheckControllerTest: AbstractTest() {

    @Test
    fun healthCheckTest() {
        val mvcResult = mockMvc.perform(getRequest())
            .andExpect(status().isOk)
            .andReturn()
        val contentResult = mvcResult.response.contentAsString

        // TODO change OK text to constant
        assertEquals("OK", contentResult)
    }

    fun getRequest() = get("/health")
}
package com.miumiuhaskeer.fastmessage.statistic

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value

class ApplicationPropertiesTest: AbstractTest() {

    @Value("\${spring.datasource.driver-class-name}")
    private val driverName: String? = null

    @Test
    fun hasH2DriverTest() {
        Assertions.assertEquals(driverName, "org.h2.Driver")
    }
}
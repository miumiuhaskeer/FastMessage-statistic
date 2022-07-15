package com.miumiuhaskeer.fastmessage.statistic.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthCheckController {

    @RequestMapping
    fun check() = "OK"
}
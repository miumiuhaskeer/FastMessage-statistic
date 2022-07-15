package com.miumiuhaskeer.fastmessage.statistic.properties.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "fastmesssage.jwt.token")
data class JWTokenProp (
    val fmsSecret: String,
    var fmsExpirationSeconds: Long
)
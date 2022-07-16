package com.miumiuhaskeer.fastmessage.statistic.exception.handler

import com.miumiuhaskeer.fastmessage.statistic.JsonConverter
import com.miumiuhaskeer.fastmessage.statistic.model.response.ResponseEntityBuilder
import com.miumiuhaskeer.fastmessage.statistic.properties.bundle.ErrorBundle
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationErrorHandler(
    private val jsonConverter: JsonConverter
): AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val simpleResponse = ResponseEntityBuilder.SimpleResponse(
            HttpStatus.UNAUTHORIZED.value(),
            ErrorBundle["error.authenticationError.message"]
        )

        response.apply {
            contentType = MediaType.APPLICATION_JSON_VALUE
            characterEncoding = "UTF-8"
            status = HttpStatus.UNAUTHORIZED.value()
            writer.append(jsonConverter.toJson(simpleResponse))
        }
    }
}
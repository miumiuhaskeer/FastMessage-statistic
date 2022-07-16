package com.miumiuhaskeer.fastmessage.statistic.exception.handler

import com.miumiuhaskeer.fastmessage.statistic.exception.AuthenticationFailedException
import com.miumiuhaskeer.fastmessage.statistic.model.response.ResponseEntityBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [
        IllegalArgumentException::class,
        EntityNotFoundException::class,
        UsernameNotFoundException::class,
        ConstraintViolationException::class,
        MethodArgumentNotValidException::class
    ])
    fun handleBadRequestException(e: Exception): ResponseEntity<ResponseEntityBuilder.SimpleResponse> {
        e.printStackTrace()

        return ResponseEntityBuilder()
            .status(HttpStatus.BAD_REQUEST)
            .message(e.message)
            .create()
    }

    @ExceptionHandler(value = [
        AuthenticationFailedException::class
    ])
    fun handleAuthenticationFailedException(e: AuthenticationFailedException): ResponseEntity<ResponseEntityBuilder.SimpleResponse> {
        e.printStackTrace()

        return ResponseEntityBuilder()
            .status(HttpStatus.UNAUTHORIZED)
            .message(e.message)
            .create()
    }

    @ExceptionHandler(value = [
        RuntimeException::class
    ])
    fun handleRuntimeExceptionException(e: Exception): ResponseEntity<ResponseEntityBuilder.SimpleResponse> {
        e.printStackTrace()

        return ResponseEntityBuilder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .message(e.message)
            .create()
    }
}
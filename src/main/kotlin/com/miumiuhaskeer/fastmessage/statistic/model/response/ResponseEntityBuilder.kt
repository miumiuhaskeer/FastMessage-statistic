package com.miumiuhaskeer.fastmessage.statistic.model.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

// TODO Change to correct builder with default object (ok, bad_request)
class ResponseEntityBuilder {

    var status = HttpStatus.OK
    var message: String? = HttpStatus.OK.reasonPhrase

    /**
     * Set http status for response entity (default OK)
     *
     * @param httpStatus response status
     * @return object builder
     */
    fun status(httpStatus: HttpStatus) = apply {
        this.status = httpStatus
    }

    /**
     * Set message for response entity (default message for OK http status)
     *
     * @param message description for http status
     * @return object builder
     */
    fun message(message: String?) = apply {
        this.message = message
    }

    /**
     * Finish creating of response object. Default status OK
     *
     * @return response entity object with description for http status
     */
    fun create() = ResponseEntity.status(status).body(
        SimpleResponse(status.value(), message)
    )

    data class SimpleResponse(
        var status: Int,
        var message: String?
    )
}
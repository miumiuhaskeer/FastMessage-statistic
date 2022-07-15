package com.miumiuhaskeer.fastmessage.statistic.controller

import com.miumiuhaskeer.fastmessage.statistic.JsonConverter
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindMessagesRequest
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindUserInfoRequest
import com.miumiuhaskeer.fastmessage.statistic.model.response.FindMessagesResponse
import com.miumiuhaskeer.fastmessage.statistic.model.response.FindUserInfoResponse
import com.miumiuhaskeer.fastmessage.statistic.service.MessageSearchServiceImpl
import com.miumiuhaskeer.fastmessage.statistic.service.UserInfoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/find")
class SearchController(
    private val messageSearchService: MessageSearchServiceImpl,
    private val userInfoService: UserInfoService
) {

    // TODO change to GetMapping
    @PostMapping("/messages")
    fun findMessages(@RequestBody @Valid filter: FindMessagesRequest): ResponseEntity<FindMessagesResponse> {
        val messagesResult = messageSearchService.findByFilter(filter)

        return ResponseEntity.ok(FindMessagesResponse().apply {
            messages = messagesResult
        })
    }

    @PostMapping("/user/info")
    fun findUserInfo(@RequestBody @Valid filter: FindUserInfoRequest): ResponseEntity<FindUserInfoResponse> {
        val userInfosResult = userInfoService.findByFilter(filter)

        return ResponseEntity.ok(FindUserInfoResponse().apply {
            userInfos = userInfosResult
        })
    }
}
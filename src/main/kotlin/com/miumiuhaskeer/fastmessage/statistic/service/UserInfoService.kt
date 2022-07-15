package com.miumiuhaskeer.fastmessage.statistic.service

import com.miumiuhaskeer.fastmessage.model.kafka.UserInfoKafka
import com.miumiuhaskeer.fastmessage.statistic.model.entity.UserInfo
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindUserInfoRequest

interface UserInfoService {
    fun addNewChatCount(userId: Long, chatCount: Int)
    fun addNewMessageCount(userId: Long, messageCount: Int)
    fun create(userId: Long, userInfoKafka: UserInfoKafka)
    fun findById(userId: Long): UserInfo
    fun findByFilter(filter: FindUserInfoRequest): List<UserInfo>
}
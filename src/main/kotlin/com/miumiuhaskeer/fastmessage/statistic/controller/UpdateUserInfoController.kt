package com.miumiuhaskeer.fastmessage.statistic.controller

import com.miumiuhaskeer.fastmessage.model.kafka.UserInfoKafka
import com.miumiuhaskeer.fastmessage.statistic.service.UserInfoService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@EnableKafka
class UpdateUserInfoController(
    private val userInfoService: UserInfoService
) {

    @KafkaListener(topics = ["updateUserInfoChatCount"])
    fun updateChatCount(record: ConsumerRecord<Long, Int>) {
        userInfoService.addNewChatCount(record.key(), record.value())
    }

    @KafkaListener(topics = ["updateUserInfoMessageCount"])
    fun updateMessageCount(record: ConsumerRecord<Long, Int>) {
        userInfoService.addNewMessageCount(record.key(), record.value())
    }

    @KafkaListener(topics = ["createUserInfo"])
    fun createUserInfo(record: ConsumerRecord<Long, UserInfoKafka>) {
        userInfoService.create(record.key(), record.value())
    }
}
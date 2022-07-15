package com.miumiuhaskeer.fastmessage.statistic

import com.miumiuhaskeer.fastmessage.model.kafka.UserInfoKafka
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

abstract class AbstractKafkaTest: AbstractTest() {

    @Autowired
    protected lateinit var userInfoKafkaTemplate: KafkaTemplate<Long, UserInfoKafka>

    @Autowired
    protected lateinit var integerKafkaTemplate: KafkaTemplate<Long, Int>

    private val userId = AtomicLong(0)

    protected fun createUserInfo() = createUserInfo(getSimpleUserInfo())

    protected fun createUserInfo(userInfoKafka: UserInfoKafka): Long {
        val userId = userId.incrementAndGet()

        sendWithSleep(userInfoKafkaTemplate, "createUserInfo", userId, userInfoKafka)

        return userId
    }

    protected fun getSimpleUserInfo() = UserInfoKafka().apply {
        email = "admin@mail.ru"
        messageCount = 0
        chatCount = 0
        creationDateTime = LocalDateTime.now()
    }

    /**
     * Methods created to using with send method (KafkaTemplate)
     *
     * @param kafkaTemplate template with some kay and value
     * @param topic for KafkaTemplate
     * @param key for KafkaTemplate
     * @param value for KafkaTemplate
     */
    protected fun <K: Any, V: Any> sendWithSleep(kafkaTemplate: KafkaTemplate<K, V>, topic: String, key: K, value: V) {
        kafkaTemplate.send(topic, key, value).apply {
            addCallback(System.out::println, System.err::println)
            kafkaTemplate.flush()
        }.get(1, TimeUnit.SECONDS)
        Thread.sleep(5000)
    }
}
package com.miumiuhaskeer.fastmessage.statistic.service

import com.miumiuhaskeer.fastmessage.model.kafka.UserInfoKafka
import com.miumiuhaskeer.fastmessage.statistic.extension._Int.zeroIfNull
import com.miumiuhaskeer.fastmessage.statistic.model.entity.UserInfo
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindUserInfoRequest
import com.miumiuhaskeer.fastmessage.statistic.properties.bundle.ErrorBundle
import com.miumiuhaskeer.fastmessage.statistic.repository.UserInfoRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UserInfoServiceImpl(
    private val userInfoRepository: UserInfoRepository
): UserInfoService {

    /**
     * Add new chats to user info
     *
     * @param userId id for user
     * @param chatCount new chats count
     * @throws EntityNotFoundException if user not found
     */
    override fun addNewChatCount(userId: Long, chatCount: Int) = getAndUpdate(userId) { userInfo ->
        userInfo.chatCount = userInfo.chatCount.zeroIfNull() + chatCount
    }

    /**
     * Add new messages to user info
     *
     * @param userId id for user
     * @param messageCount new messages count
     * @throws EntityNotFoundException if user not found
     */
    override fun addNewMessageCount(userId: Long, messageCount: Int) = getAndUpdate(userId) { userInfo ->
        userInfo.messageCount = userInfo.messageCount.zeroIfNull() + messageCount
    }

    /**
     * Creates new UserInfo statistic for user
     *
     * @param userId id for user
     * @param userInfoKafka user info object
     */
    override fun create(userId: Long, userInfoKafka: UserInfoKafka) {
        val userInfo = UserInfo().apply {
            this.userId = userId
            email = userInfoKafka.email
            messageCount = userInfoKafka.messageCount.zeroIfNull()
            chatCount = userInfoKafka.chatCount.zeroIfNull()
            creationDateTime = userInfoKafka.creationDateTime
        }

        userInfoRepository.save(userInfo)
    }

    @Throws(EntityNotFoundException::class)
    override fun findById(userId: Long): UserInfo {
        val result = userInfoRepository.findByUserId(userId)

        if (!result.isPresent) {
            throw EntityNotFoundException(ErrorBundle.get("error.entityNotFoundException.user.message"));
        }

        return result.get()
    }

    override fun findByFilter(filter: FindUserInfoRequest) = userInfoRepository.findByFilter(filter)

    /**
     * Get user, change some properties by action and then update it
     *
     * @param userId id for user
     * @param action modify UserInfo
     * @throws EntityNotFoundException if user not found
     */
    private inline fun getAndUpdate(userId: Long, action: (userInfo: UserInfo) -> Unit) {
        val userInfo = findById(userId)

        action(userInfo)
        userInfoRepository.save(userInfo)
    }
}
package com.miumiuhaskeer.fastmessage.statistic.service

import com.miumiuhaskeer.fastmessage.statistic.model.UserDetailsImpl
import com.miumiuhaskeer.fastmessage.statistic.properties.bundle.ErrorBundle
import com.miumiuhaskeer.fastmessage.statistic.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username).orElseThrow {
            UsernameNotFoundException(ErrorBundle["error.usernameNotFoundException.message"])
        }

        return UserDetailsImpl.from(user)
    }
}
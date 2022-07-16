package com.miumiuhaskeer.fastmessage.statistic.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.miumiuhaskeer.fastmessage.statistic.model.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors

class UserDetailsImpl(
    override val id: Long?,
    override val email: String?,
    @JsonIgnore private val password: String?,
    private val authorities: Collection<GrantedAuthority?>
): ExtendedUserDetails() {

    companion object {

        // TODO remove after migrating to Kotlin
        @JvmStatic
        fun from(user: User): UserDetailsImpl {
            val authorities: List<GrantedAuthority> = user.roles.stream().map {
                    SimpleGrantedAuthority(it.name!!.name)
                }.collect(Collectors.toList())

            return UserDetailsImpl(
                user.id,
                user.email,
                user.password,
                authorities
            )
        }
    }

    override fun getPassword() = password
    override fun getAuthorities() = authorities

    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}
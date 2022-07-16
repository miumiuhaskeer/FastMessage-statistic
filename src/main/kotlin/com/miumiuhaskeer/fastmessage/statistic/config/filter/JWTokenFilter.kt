package com.miumiuhaskeer.fastmessage.statistic.config.filter

import com.miumiuhaskeer.fastmessage.statistic.properties.bundle.ErrorBundle
import com.miumiuhaskeer.fastmessage.statistic.util.JWTokenUtil
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.text.ParseException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTokenFilter(
    private val userDetailsService: UserDetailsService,
    private val jwTokenUtil: JWTokenUtil
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
            doAuthentication(request)
        }

        filterChain.doFilter(request, response)
    }

    /**
     * Authenticate user by HttpServletRequest
     *
     * @param request for authenticate
     */
    private fun doAuthentication(request: HttpServletRequest) {
        try {
            val token: String = parseToken(request)

            if (jwTokenUtil.validateToken(token)) {
                val email = jwTokenUtil.getEmailFromToken(token)
                val userDetails = userDetailsService.loadUserByUsername(email)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                ).apply {
                    details = userDetails
                }

                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Parse token by request
     *
     * @param request to get token
     * @return result of parsing token
     * @throws ParseException if error occurred while parsing jwt token
     */
    @Throws(ParseException::class)
    private fun parseToken(request: HttpServletRequest): String {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (!jwTokenUtil.headerIsToken(header)) {
            throw ParseException(ErrorBundle["error.parseException.token.bearer.message"], 0)
        }

        return jwTokenUtil.getTokenFromHeader(header)
    }
}
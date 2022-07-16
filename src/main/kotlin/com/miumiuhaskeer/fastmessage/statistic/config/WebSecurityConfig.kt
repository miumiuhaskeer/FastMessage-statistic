package com.miumiuhaskeer.fastmessage.statistic.config

import com.miumiuhaskeer.fastmessage.statistic.JsonConverter
import com.miumiuhaskeer.fastmessage.statistic.config.filter.JWTokenFilter
import com.miumiuhaskeer.fastmessage.statistic.exception.handler.AuthenticationErrorHandler
import lombok.RequiredArgsConstructor
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@EnableKafka
@Configuration
@RequiredArgsConstructor
@ConfigurationPropertiesScan("com.miumiuhaskeer.fastmessage.statistic.properties.config")
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
class WebSecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwTokenFilter: JWTokenFilter,
    private val jsonConverter: JsonConverter
): WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/health/**", "/health/fms/**")
            .permitAll()
            .anyRequest()
            .authenticated()

        http.exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint())
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.cors()

        // not required for api
        http.csrf().disable()

        http.addFilterBefore(jwTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun authenticationEntryPoint() = AuthenticationErrorHandler(jsonConverter)

    @Bean
    fun corsFilter(): CorsFilter {
        val configuration = CorsConfiguration().apply {
            allowCredentials = true
            addAllowedOrigin("*")
            addAllowedHeader("*")
            addAllowedMethod("*")
        }
        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }

        return CorsFilter(source)
    }
}
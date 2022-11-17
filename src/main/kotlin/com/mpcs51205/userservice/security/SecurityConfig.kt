package com.mpcs51205.userservice.security

import com.mpcs51205.userservice.service.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(val jwtService: JwtService, val auctionUserDetailService: AuctionUserDetailService) {

    @Bean
    fun jwtAuthFilter(): JwtAuthFilter? {
        return JwtAuthFilter(jwtService, auctionUserDetailService)
    }

    @Bean
    fun configure(http: HttpSecurity?): SecurityFilterChain {
        return http!!
            .csrf { it.disable() }
            .authorizeRequests {
                it.antMatchers("/authenticate", "/register").permitAll()
                it.antMatchers("/user/**").authenticated()
                it.antMatchers("/admin/user/**").authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .build()


    }
}
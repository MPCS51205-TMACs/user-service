package com.mpcs51205.userservice.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun configure(http: HttpSecurity?): SecurityFilterChain {
        return http!!
            .csrf { it.disable() }
            .authorizeRequests {
                it.antMatchers("/authenticate", "/register").permitAll()
                it.antMatchers("/user/**").hasRole("USER")
                it.antMatchers("/admin/user/**").hasRole("ADMIN")
            }
            .build()


    }
}
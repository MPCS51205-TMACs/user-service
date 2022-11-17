package com.mpcs51205.userservice.service

import com.mpcs51205.userservice.models.AuthRequest
import com.mpcs51205.userservice.models.AuthResponse
import org.springframework.stereotype.Service

@Service
class AuthService(val userService: UserService, val jwtService: JwtService) {
    fun authenticate(authenticationRequest: AuthRequest): AuthResponse {
        val user = userService.getUserByAuthRequest(authenticationRequest)
        val jwt = jwtService.getToken(user)
        return AuthResponse(jwt)
    }
}
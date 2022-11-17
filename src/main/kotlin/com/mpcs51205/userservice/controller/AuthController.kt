package com.mpcs51205.userservice.controller

import com.mpcs51205.userservice.models.AuthRequest
import com.mpcs51205.userservice.models.AuthResponse
import com.mpcs51205.userservice.service.AuthService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authenticate")
class AuthController(val authService: AuthService) {
    @PostMapping("")
    fun authenticate(@RequestBody authRequest: AuthRequest): AuthResponse = authService.authenticate(authRequest)
}
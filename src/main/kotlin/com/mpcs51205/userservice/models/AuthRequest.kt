package com.mpcs51205.userservice.models

data class AuthRequest(val email: String, val password: String)
data class AuthResponse(val token: String)

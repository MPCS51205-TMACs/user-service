package com.mpcs51205.userservice.service

import com.mpcs51205.userservice.exception.AccountSuspendedException
import com.mpcs51205.userservice.models.AuthRequest
import com.mpcs51205.userservice.models.AuthResponse
import com.mpcs51205.userservice.models.User
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
internal class AuthServiceTest {

    @Autowired
    lateinit var authService: AuthService

    @MockBean
    lateinit var jwtService: JwtService

    @MockBean
    lateinit var userService: UserService

    val authRequest = AuthRequest("email", "password")
    val activeUser = User().apply {
        id = UUID.randomUUID()
        email = "email"
        name = "user"
    }
    val jwt = "somejwt"
    val suspendedUser = User().apply {
        id = UUID.randomUUID()
        email = "email"
        name = "user"
        suspended = true
    }

    @Test
    fun `should issue jwt when given correct credentials`() {
        Mockito.`when`(userService.getUserByAuthRequest(authRequest)).thenReturn(activeUser)
        Mockito.`when`(jwtService.getToken(activeUser)).thenReturn("somejwt")

        val result = authService.authenticate(authRequest)
        assertEquals(result, AuthResponse(jwt))
    }

    @Test
    fun `should error when given incorrect credentials`() {
    }

    @Test
    fun `should error when user doesn't exist`() {
        Mockito.`when`(userService.getUserByAuthRequest(authRequest)).thenThrow(RuntimeException())
    }

}
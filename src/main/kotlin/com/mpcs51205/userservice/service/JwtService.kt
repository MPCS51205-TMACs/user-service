package com.mpcs51205.userservice.service

import arrow.core.Option
import arrow.core.getOrElse
import com.mpcs51205.userservice.models.User
import io.github.nefilim.kjwt.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset


@Service
class JwtService {
    @Value("\${jwt.secret}")
    lateinit var secret: String

    @Value("\${jwt.duration}")
    lateinit var duration: Duration


    fun getToken(user: User): String {
        val issuedAt = Instant.now()
        val expiresAt = issuedAt.plus(duration)
        val jwt = JWT.hs256 {
            subject(user.id.toString())
            issuer("user-service")
            audience("mpcs51205")
            claim("email", user.email)
            claim("name", user.name)
            claim("authorities", user.getRoles())
            issuedAt(LocalDateTime.ofInstant(issuedAt, ZoneOffset.UTC))
            expiresAt(LocalDateTime.ofInstant(expiresAt, ZoneOffset.UTC))
        }
            return jwt.sign(secret).fold({ throw Exception() }, { it.rendered })
    }

    fun decode(token: String): DecodedJWT<out JWSAlgorithm> {
        return JWT.decode(token).fold({ throw Exception() }, { it })
    }

    fun validateToken(token: String): Boolean = verifySignature<JWSHMACAlgorithm>(token, secret).isRight()

    fun getUsername(token: String): String {
        return decode(token).claimValue("EMAIL").getOrElse { throw Exception() }
    }
}

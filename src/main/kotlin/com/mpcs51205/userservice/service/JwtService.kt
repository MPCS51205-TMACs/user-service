package com.mpcs51205.userservice.service

import com.mpcs51205.userservice.models.User
import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.ECDSASigner
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jwt.JWT
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*


@Service
class JwtService {
    @Value("\${jwt.secret}")
    lateinit var secret: String

    @Value("\${jwt.duration}")
    lateinit var duration: Duration


    fun getToken(user: User): String {
        val issuedAt = Instant.now()
        val expiresAt = issuedAt.plus(duration)

        // https://www.scottbrady91.com/kotlin/creating-signed-jwts-using-nimbus-jose-jwt
        val header = JWSHeader.Builder(JWSAlgorithm.HS256)
            .type(JOSEObjectType.JWT)
            .build()

        val payload = JWTClaimsSet.Builder()
            .subject(user.id.toString())
            .issuer("user-service")
            .audience("mpcs51205")
            .claim("email", user.email)
            .claim("name", user.name)
            .claim("authorities", user.getRoles())
            .issueTime(Date.from(issuedAt))
            .expirationTime(Date.from(expiresAt))
            .build()

        val signedJWT = SignedJWT(header, payload)
        signedJWT.sign(MACSigner(secret))
        return signedJWT.serialize()
    }
}

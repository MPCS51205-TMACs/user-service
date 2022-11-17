package com.mpcs51205.userservice.security

import com.mpcs51205.userservice.service.JwtService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthFilter(private val jwtService: JwtService, private val auctionUserDetailService: AuctionUserDetailService) :
    OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = getJwtFromRequest(request)
            if (jwtService.validateToken(jwt)) {
                val name = jwtService.getUsername(jwt)
                val auctionUser = auctionUserDetailService.loadUserByUsername(name.toString())
                val authentication = UsernamePasswordAuthenticationToken(
                    auctionUser, null, auctionUser.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }

        } catch (e: Exception) {
            println("Could not authorize JWT")
        }
        filterChain.doFilter(request, response)
    }

    fun getJwtFromRequest(request: HttpServletRequest): String {
        val bearerToken = request.getHeader("Authorization")
        return bearerToken.substring(7, bearerToken.length)


    }
}
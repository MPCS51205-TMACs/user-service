package com.mpcs51205.userservice.security
import com.mpcs51205.userservice.exception.ResourceDoesNotExistException
import com.mpcs51205.userservice.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class AuctionUserDetailService(val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.getUserByEmail(username?:"") ?: throw ResourceDoesNotExistException()
        return AuctionUserDetail(user)
    }
}
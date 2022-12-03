package com.mpcs51205.userservice.repository

import com.mpcs51205.userservice.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, UUID> {
    fun getUserByEmail(email:String): User?

    fun getUserByActiveIsFalse(): Collection<User>

}
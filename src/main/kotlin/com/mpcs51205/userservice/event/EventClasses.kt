package com.mpcs51205.userservice.event

import com.mpcs51205.userservice.models.UserUpdate
import java.io.Serializable
import java.util.*

class UserUpdateEvent(val userId: UUID, val update: UserUpdate): Serializable
class UserDeleteEvent(val userId: UUID): Serializable
class UserActivationEvent(val userId: UUID, val isActive: Boolean): Serializable
package com.mpcs51205.userservice.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.mpcs51205.userservice.event.UserUpdateEvent
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "auction_user", indexes = [Index(columnList = "email")])
class User: Serializable, NameAndId {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(nullable = false)
    override var id: UUID? = null

    @Column(nullable = false)
    override lateinit var name: String

    @Column(unique=true, nullable = false)
    lateinit var email: String

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    lateinit var password: String

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    lateinit var paymentMethod: String

    @Column
    var admin: Boolean = false

    @Column
    var active: Boolean = true

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    lateinit var revocationId: UUID

    fun getRoles(): List<String> = if (admin) listOf("ROLE_USER","ROLE_ADMIN") else listOf("ROLE_USER")

    fun miniaturize(): UserMini = UserMini(id!!,name)
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserUpdate: Serializable {
    var name: String? = null
    var email: String? = null
    var isAdmin: Boolean? = null
    var paymentMethod: String? = null

    fun update(user: User): UserUpdateEvent {
        user.name = this.name ?: user.name
        user.email = this.email ?: user.email
        user.admin = this.isAdmin ?: user.admin
        user.paymentMethod = this.paymentMethod ?: user.paymentMethod
        return UserUpdateEvent(user.id!!, this)
    }

}

data class UserMini(override val id: UUID, override val name: String): NameAndId

interface NameAndId{
    val id: UUID?
    val name: String
}

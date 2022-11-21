package com.mpcs51205.userservice.event

import com.mpcs51205.userservice.models.User
import org.springframework.amqp.rabbit.core.RabbitTemplate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*

@Component
class RabbitPublisher {
    @Autowired
    lateinit var template: RabbitTemplate

    @Value("\${spring.rabbitmq.template.exchange-create}")
    lateinit var createExchange: String
    @Value("\${spring.rabbitmq.template.exchange-update}")
    lateinit var updateExchange: String
    @Value("\${spring.rabbitmq.template.exchange-delete}")
    lateinit var deleteExchange: String

    fun sendCreateEvent(user: User) = send(exchange = createExchange, payload = user)
    fun sendUpdateEvent(userUpdateEvent: UserUpdateEvent) = send(exchange = updateExchange, payload = userUpdateEvent)
    fun sendDeleteEvent(userId: UUID) = send(exchange = deleteExchange, payload = UserDeleteEvent(userId))

    private fun send(exchange: String, payload: Serializable) {
        template.convertAndSend(exchange, "", payload)
    }


}
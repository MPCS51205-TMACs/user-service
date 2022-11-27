package com.mpcs51205.userservice.event


import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    @Bean
    fun jsonMessageConverter(): MessageConverter? {
        return Jackson2JsonMessageConverter()
    }


    @Value("\${spring.rabbitmq.template.exchange-create}")
    lateinit var createExchange: String
    @Value("\${spring.rabbitmq.template.exchange-update}")
    lateinit var updateExchange: String
    @Value("\${spring.rabbitmq.template.exchange-delete}")
    lateinit var deleteExchange: String
    @Value("\${spring.rabbitmq.template.exchange-activation}")
    lateinit var activationExchange: String

    @Bean
    fun userCreateExchange() = FanoutExchange(createExchange, true, false)

    @Bean
    fun userUpdateExchange() = FanoutExchange(updateExchange, true, false)

    @Bean
    fun userDeleteExchange() = FanoutExchange(deleteExchange, true, false)

    @Bean
    fun userActivateExchange() = FanoutExchange(activationExchange, true, false)

}
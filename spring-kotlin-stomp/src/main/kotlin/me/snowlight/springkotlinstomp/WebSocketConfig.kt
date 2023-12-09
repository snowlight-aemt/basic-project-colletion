package me.snowlight.springkotlinstomp

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    companion object {
        private const val ENDPOINT = "/hello-endpoint"
        private const val SIMPLE_BROKER = "/topic"
        private const val PUBLISH = "/app"
    }

    @Override
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker(SIMPLE_BROKER)
        registry.setApplicationDestinationPrefixes(PUBLISH)
    }

    @Override
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint(ENDPOINT)
    }
}
package me.snow.springkotlinwebsocket

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfiguration : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(simpleWebSocketHandler(), "/hello")
            .setAllowedOriginPatterns("*")
    }

    fun simpleWebSocketHandler(): WebSocketHandler {
        return HelloWebSocketHandler()
    }
}
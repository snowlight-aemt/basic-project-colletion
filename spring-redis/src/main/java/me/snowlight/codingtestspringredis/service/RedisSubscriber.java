package me.snowlight.codingtestspringredis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String body = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
        try {
            RoomMessage roomMessage = objectMapper.readValue(body, RoomMessage.class);
            System.out.println(roomMessage.getRoomId());
            System.out.println(roomMessage.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

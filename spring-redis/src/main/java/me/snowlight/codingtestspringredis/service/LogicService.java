package me.snowlight.codingtestspringredis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogicService {
    private final RedisTemplate<String, String> redisTemplate;

    public void run(ChannelTopic topic, RoomMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}

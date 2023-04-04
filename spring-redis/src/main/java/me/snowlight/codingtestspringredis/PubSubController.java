package me.snowlight.codingtestspringredis;

import lombok.RequiredArgsConstructor;
import me.snowlight.codingtestspringredis.service.RedisSubscriber;
import me.snowlight.codingtestspringredis.service.Room;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequiredArgsConstructor
public class PubSubController {
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;

    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/room")
    public void createRoom(@RequestBody Room room) {

        ChannelTopic channelTopic = new ChannelTopic(room.getRoomId());
        redisMessageListener.addMessageListener(redisSubscriber, channelTopic);

    }
    @PostMapping("/topics")
    public void pushMessage() {

        redisTemplate.convertAndSend("ch1", "Hello");

//        ChannelTopic channel = channels.get(name);
//        redisPublisher.publish(channel, message);
    }
}

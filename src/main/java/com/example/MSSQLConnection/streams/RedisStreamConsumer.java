package com.example.MSSQLConnection.streams;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RedisStreamConsumer {

    public final static String STREAMS_KEY = "weather_sensor:wind";

    public String consumeMessage(){
        StringBuilder consumedMessage = new StringBuilder();

        RedisClient redisClient = RedisClient.create("redis://localhost:6379");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        try {
            syncCommands.xgroupCreate( XReadArgs.StreamOffset.from(STREAMS_KEY, "0-0"), "application_1"  );
        }
        catch (RedisBusyException redisBusyException) {
            System.out.printf("\t Group '%s' already exists%n", "application_1");
        }


        System.out.println("Waiting for new messages");

        List<StreamMessage<String, String>> messages = syncCommands.xreadgroup(
                Consumer.from("application_1", "consumer_1"),
                XReadArgs.StreamOffset.lastConsumed(STREAMS_KEY)
        );

        if (!messages.isEmpty()) {
            for (StreamMessage<String, String> message : messages) {
                System.out.println(message);
                syncCommands.xack(STREAMS_KEY, "application_1",  message.getId());
                consumedMessage.append(message);
            }
        }

        return consumedMessage.toString();
    }

    @GetMapping("/consumeEvent")
    public String publishEvent(){
        return consumeMessage();
    }

}

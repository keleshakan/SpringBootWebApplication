package com.example.MSSQLConnection.streams;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RedisStreamProducer {

    public final static String STREAMS_KEY = "weather_sensor:wind";

    public void produceMessage(){
        int nbOfMessageToSend = 1;

        RedisClient redisClient = RedisClient.create("redis://localhost:6379");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        for (int i = 0 ; i < nbOfMessageToSend ; i++) {

            Map<String, String> messageBody = new HashMap<>();
            messageBody.put("speed", "15");
            messageBody.put("direction", "270");
            messageBody.put("sensor_ts", String.valueOf(System.currentTimeMillis()));
            messageBody.put("loop_info", String.valueOf( i ));

            String messageId = syncCommands.xadd(
                    STREAMS_KEY,
                    messageBody);

            System.out.printf("\tMessage %s : %s posted%n", messageId, messageBody);
        }

        System.out.println("\n");

        connection.close();
        redisClient.shutdown();
    }

    @GetMapping("/publishEvent")
    public String publishEvent(){
        produceMessage();
        return "Event Published!";
    }
}

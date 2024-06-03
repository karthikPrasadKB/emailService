package com.emailService.emailService.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    //Listens to this particular topics. consumer group Id : To make sure no other consumer
    //group doesnt;
    @KafkaListener(topics = "sendEmail", id="emailConsumerGroup")
    public void sendMail(String message){
        System.out.println("Message received @ emailConsumer : " + message);
    }
}

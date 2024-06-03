package com.emailService.emailService.consumers;

import com.emailService.emailService.dtos.EmailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    //Listens to this particular topics. consumer group Id : To make sure no other consumer
    //group doesnt;
    @KafkaListener(topics = "sendEmail", id="emailConsumerGroup")
    public void sendMail(String message) throws JsonProcessingException {
        //The object is converted to string and sent. Its needs to be decoded back into EmailDTO to process
        EmailDTO emailDTO = null;
        try{
            emailDTO = objectMapper.readValue(message, EmailDTO.class);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Email could not be converted into emailDTO object");
        }
        System.out.println("To : " + emailDTO.getTo());
        System.out.println("From : " + emailDTO.getFrom());
        System.out.println("Message : " + emailDTO.getMessage());
        System.out.println("Subject : " + emailDTO.getSubject());
    }

}

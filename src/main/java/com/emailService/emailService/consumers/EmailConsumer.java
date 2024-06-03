package com.emailService.emailService.consumers;

import com.emailService.emailService.dtos.EmailDTO;
import com.emailService.emailService.utils.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

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
        this.smtpEmail(emailDTO);
    }

    private void smtpEmail(EmailDTO emailDTO) {
        final String fromEmail = "xxxxxxx"; //requires valid gmail id
        final String password = "xxxxxxx"; // correct password for gmail id
        final String toEmail = emailDTO.getTo(); // can be any email id

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toEmail,emailDTO.getSubject(), emailDTO.getMessage());
    }

}

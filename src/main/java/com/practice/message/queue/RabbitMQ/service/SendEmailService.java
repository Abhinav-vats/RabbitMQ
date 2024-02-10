package com.practice.message.queue.RabbitMQ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.message.queue.RabbitMQ.vo.requestVo.UserRegistrationRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendEmailService {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "send-email")
    public void sendEmail(String event) throws JsonProcessingException {


        UserRegistrationRequestVO userRegistrationRequestVO = objectMapper.readValue(event, UserRegistrationRequestVO.class);

        // TODO: Logic to send email

        log.info("\nSending Email at:  {} \n",userRegistrationRequestVO.getEmail());
    }
}

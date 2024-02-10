package com.practice.message.queue.RabbitMQ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.message.queue.RabbitMQ.vo.requestVo.UserRegistrationRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ListenerService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = {"user-registration"} , containerFactory= "registrationListenerContainerFactory")
    public void onUserRegistration(String event) throws JsonProcessingException {
        log.info("\n"+event+"\n");
        UserRegistrationRequestVO userRegistrationRequestVO = objectMapper.readValue(event, UserRegistrationRequestVO.class);
        rabbitTemplate.convertAndSend("post-registration","", event);
        log.info(userRegistrationRequestVO.getUserName());
    }
}

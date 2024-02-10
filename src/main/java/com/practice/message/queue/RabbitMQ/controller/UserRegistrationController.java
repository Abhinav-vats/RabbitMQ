package com.practice.message.queue.RabbitMQ.controller;


import com.practice.message.queue.RabbitMQ.vo.requestVo.UserRegistrationRequestVO;
import com.practice.message.queue.RabbitMQ.vo.responseVo.ApiResponseVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserRegistrationController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/user")
    public ApiResponseVo createUser(@RequestBody UserRegistrationRequestVO userRegistrationRequestVO){

        rabbitTemplate.convertAndSend("", "user-registration", userRegistrationRequestVO.toString());
        return new ApiResponseVo("success");
    }
}

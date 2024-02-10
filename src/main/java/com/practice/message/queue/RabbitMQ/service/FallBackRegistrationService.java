package com.practice.message.queue.RabbitMQ.service;

import com.practice.message.queue.RabbitMQ.vo.requestVo.UserRegistrationRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FallBackRegistrationService {

    @RabbitListener(queues = {"fall-back-registration"})
    public void onRegistrationFailure(String failedRegistration){
        log.info("Executing fallback for failed registration {}", failedRegistration);
    }

}

package com.practice.message.queue.RabbitMQ.vo.responseVo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ApiResponseVo {
    private String status;
    private Map<String, Object> result;
    private Map<String, Object> reason;

    public ApiResponseVo(String status) {
        this.status = status;
    }
}

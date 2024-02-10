package com.practice.message.queue.RabbitMQ.vo.requestVo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequestVO {
    private String userName;
    private String email;
    private String mobileNumber;

    @Override
    public String toString() {
        return "{\n" +
                "\t\"userName\":\"" + userName + "\",\n" +
                "\t\"email\":\"" + email + "\",\n" +
                "\t\"mobileNumber\":\"" + mobileNumber + "\"\n" +
                "}";
    }
}

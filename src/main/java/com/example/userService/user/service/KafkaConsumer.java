package com.example.userService.user.service;

import com.example.userService.config.SecurityService;
import com.example.userService.user.model.UserDto;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class KafkaConsumer {

    @Autowired
    SecurityService securityService;

    @Autowired
    UserServiceImpl userService;

    @KafkaListener(topics = "jungTopic", groupId = "jung")
    public ResponseEntity<?> consume(String message) throws IOException, ParseException {
        System.out.println("jung's receive message : " + message);
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = new UserDto();

        UserDto jsonRes = objectMapper.readValue( message , UserDto.class);

        System.out.println("final....."+jsonRes.getUserId());
        String token = securityService.createToken(jsonRes.getId().toString());
        System.out.println("Token Next" + token);

        HttpStatus httpStatus =  userService.createUser((UserDto)jsonRes) ==1
                ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
        //System.out.println("after jung's receive message Good: " + jsonRes);

//        const obj = JSON.parse(json);
        //System.out.println("jung's userDto : " + message.getUserId());
    }
}

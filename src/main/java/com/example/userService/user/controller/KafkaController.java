package com.example.userService.user.controller;

import com.example.userService.user.service.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@Slf4j
@RequestMapping("kafka")
public class KafkaController {
    private final KafkaProducer producer;

    @Autowired
    KafkaController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping( "/login")
    public String sendMessage(@RequestBody HashMap<String, String> map) throws JsonProcessingException {
        System.out.println("login...."+map);

        //this.producer.sendMessage(userDto.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        this.producer.sendMessage(map.toString());
        String tojson = objectMapper.writeValueAsString(map);
        this.producer.sendMessage(tojson);

        return map.toString();
    }

}

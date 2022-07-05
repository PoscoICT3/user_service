package com.example.userService.user.controller;

import com.example.userService.config.SecurityService;
import com.example.userService.user.model.UserDto;
import com.example.userService.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")

public class UserController {
    // swagger
    // http://localhost:8000/swagger-ui/index.html#/
    @Autowired
    UserServiceImpl userService;

    @Autowired
    SecurityService securityService;

    @GetMapping("/")
    public List<UserDto> getUser(){

        return userService.findUserList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable String id){
        UserDto userDto = new UserDto();
        userDto.setId(Integer.valueOf(id));
        return userService.findUserById(userDto);
    }

    @PostMapping("/login")
    public Map login(@RequestBody UserDto userDto){
        //받아온걸 통해서 진행
        //Bearer +token값
        System.out.println("HereLoginDto" + userDto);
        UserDto loginUser = userService.serviceLogin(userDto);
        String token = securityService.createToken(loginUser.getId().toString());
        System.out.println("Next" + token);
        Map<String, Object> map = new HashMap<>();
        System.out.println("token,user,userId,phoneNum" + loginUser);
        map.put("token",token);
        map.put("user", loginUser);
        map.put("userId", loginUser.getUserId());
        map.put("phoneNum", loginUser.getPhoneNum());
        return map;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        HttpStatus httpStatus = userService.createUser(userDto) ==1
                ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteUserById(){

        UserDto userDto = new UserDto();
        userDto.setId(securityService.getIdAtToken());
        HttpStatus httpStatus = userService.deleteUserById(userDto) == 1
                ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateUserById(@RequestBody UserDto userDto){
        //test
        userDto.setId(securityService.getIdAtToken());
        userDto.setPhoneNum(userDto.getPhoneNum());
        userDto.setName(userDto.getName());
        HttpStatus httpStatus = userService.updateUserById(userDto) == 1
                ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }

    @GetMapping("/check")
    public Boolean check(){
        //test
        return true;
        //another test
    }

    @GetMapping("/me")
    public UserDto getUserByMe(){
//        log.info("cont");
        UserDto userDto = new UserDto();
        userDto.setId(securityService.getIdAtToken());
        return userService.findUserById(userDto);
    }
}
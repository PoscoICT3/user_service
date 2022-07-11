package com.example.userService.user.controller;

import com.example.userService.aspect.TokenRequired;
import com.example.userService.config.SecurityService;
import com.example.userService.user.model.UserDto;

import com.example.userService.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    SecurityService securityService;

    @GetMapping("/")
//    @TokenRequired
    public List<UserDto> getUser() {
        return userService.findUser();
    }

    @GetMapping("/{id}")
//    @TokenRequired
    public UserDto getUserById(@PathVariable String id) {
        UserDto userDto = new UserDto();
        userDto.setId(Integer.valueOf(id));
        return userService.findUserById(userDto);
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        HttpStatus httpStatus = userService.createUser(userDto) == 1
                ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }

    @DeleteMapping("/")
    public int deleteUserById(){
        UserDto userDto = new UserDto();
        userDto.setId(securityService.getIdAtToken());
        return userService.deleteUser(userDto);
    }

    @PutMapping("/")
    @TokenRequired
    @Operation(description = "정보 업데이트")
    public Integer updateUserById(@RequestBody UserDto userDto){
        userDto.setId(securityService.getIdAtToken());
        return userService.updateUserById(userDto);
    }

    @PostMapping("/login")
    @Operation(description = "로그인")
    public Map loginUser(@RequestBody UserDto userDto){
        UserDto loginUser = userService.serviceLogin(userDto);
        // 밀리초
        String token = securityService.createToken(loginUser.getId().toString());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("name", loginUser.getName());
        map.put("img", loginUser.getImg());

        return map;
    }

    //token 인증방식
    //header에도 값을 넣어주고 token get방식 url에도 받아오기
    //이건 보여주기식 실제로는 로직에 넣어서 사용
    @GetMapping("/token")
    @TokenRequired
    public String getToken(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String tokenBearer = request.getHeader("Authorization");
        String subject = securityService.getSubject(tokenBearer);
        return subject;
    }
    @GetMapping("/me")
    @TokenRequired
    public UserDto getUserByMe(){
//        log.info("cont");
        UserDto userDto = new UserDto();
        userDto.setId(securityService.getIdAtToken());
        return userService.findUserById(userDto);
    }

    @TokenRequired
    @GetMapping("/check")
    public Boolean check(){
        return true;
    }

}

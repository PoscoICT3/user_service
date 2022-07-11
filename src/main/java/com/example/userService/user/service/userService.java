package com.example.userService.user.service;

import com.example.userService.user.model.UserDto;

import java.util.List;


public interface userService {
    List<UserDto> findUser();
    UserDto findUserById(UserDto userDto);

    int createUser(UserDto userDto);

    int deleteUser(UserDto userDto);

    Integer updateUserById(UserDto userDot);

    UserDto serviceLogin(UserDto userDto);
}

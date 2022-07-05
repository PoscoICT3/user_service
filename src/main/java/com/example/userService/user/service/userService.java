package com.example.userService.user.service;

import com.example.userService.user.model.UserDto;

import java.util.List;


public interface userService {
    List<UserDto> findUserList();

    UserDto findUserById(UserDto userDto);

    UserDto serviceLogin(UserDto userDto);

    Integer createUser(UserDto userDto);

    Integer deleteUserById(UserDto userDto);

    Integer updateUserById(UserDto userDto);
}
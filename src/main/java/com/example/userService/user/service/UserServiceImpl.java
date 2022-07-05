package com.example.userService.user.service;


import com.example.userService.user.model.UserDto;

import com.example.userService.user.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements userService{

    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserDto> findUserList() {
        return userMapper.getUserList();
    }

    @Override
    public UserDto findUserById(UserDto userDto) {
        return userMapper.getUserById(userDto);
    }

    @Override
    public UserDto serviceLogin(UserDto userDto) {
        return userMapper.getLoginByUserIdAndPassword(userDto);
    }

    @Override
    public Integer createUser(UserDto userDto) {
        return userMapper.createUser(userDto);
    }

    @Override
    public Integer deleteUserById(UserDto userDto) {
        return userMapper.deleteUserById(userDto);
    }

    @Override
    public Integer updateUserById(UserDto userDto) {
        return userMapper.updateUserById(userDto);
    }
}
package com.example.userService.user.repository;

import com.example.userService.user.model.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserDto> getUserList();

    UserDto getUserById(UserDto userDto);

    UserDto getLoginByUserIdAndPassword(UserDto userDto);

    Integer createUser(UserDto userDto);

    Integer deleteUserById(UserDto userDto);

    Integer updateUserById(UserDto userDto);
}
package com.example.userService.user.model;


import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String password;
    private String userId;
    private String img;
    private String name;
}

package com.example.MSSQLConnection.service;

import com.example.MSSQLConnection.dto.UserDto;
import com.example.MSSQLConnection.persistence.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}

package org.sber.sberhomework20.service;

import org.sber.sberhomework20.dto.UserCreateDto;
import org.sber.sberhomework20.dto.UserDto;
import org.sber.sberhomework20.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(long id);

    void deleteUserById(long id);

    UserDto createUser(UserCreateDto userDto);

    List<UserDto> getUsers();

    UserDto updateUser(long id, UserUpdateDto userDto);
}

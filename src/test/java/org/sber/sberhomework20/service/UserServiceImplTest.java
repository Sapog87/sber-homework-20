package org.sber.sberhomework20.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sber.sberhomework20.data.User;
import org.sber.sberhomework20.dto.UserCreateDto;
import org.sber.sberhomework20.dto.UserDto;
import org.sber.sberhomework20.dto.UserUpdateDto;
import org.sber.sberhomework20.exception.UserAlreadyExistsException;
import org.sber.sberhomework20.exception.UserNotFoundException;
import org.sber.sberhomework20.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Получение пользователя по ID - успех")
    void testGetUserByIdSuccess() {
        User user = new User();
        user.setId(0L);
        user.setName("name");
        user.setLogin("login");

        doReturn(Optional.of(user)).when(userRepository).findById(anyLong());

        UserDto userDto = userService.getUserById(0);

        verify(userRepository).findById(0L);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getLogin(), userDto.getLogin());
    }

    @Test
    @DisplayName("Получение пользователя по ID - пользователь не найден")
    void testGetUserByIdNotFound() {
        doReturn(Optional.empty()).when(userRepository).findById(anyLong());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(0));

        verify(userRepository).findById(0L);
    }


    @Test
    @DisplayName("Удаление пользователя по ID - успех")
    void testDeleteUserByIdSuccess() {
        doReturn(true).when(userRepository).existsById(anyLong());
        doNothing().when(userRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> userService.deleteUserById(0));

        verify(userRepository).existsById(0L);
        verify(userRepository).deleteById(0L);
    }

    @Test
    @DisplayName("Удаление пользователя по ID - пользователь не найден")
    void testDeleteUserByIdNotFound() {
        doReturn(false).when(userRepository).existsById(anyLong());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(0));

        verify(userRepository).existsById(anyLong());
    }

    @Test
    @DisplayName("Создание пользователя - успех")
    void testCreateUserSuccess() {
        User user = new User();
        user.setId(0L);
        user.setName("name");
        user.setLogin("login");

        doReturn(false).when(userRepository).existsByLogin(anyString());
        doReturn(user).when(userRepository).save(any(User.class));

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("name");
        userCreateDto.setLogin("login");
        UserDto userDto = userService.createUser(userCreateDto);

        verify(userRepository).save(any(User.class));
        verify(userRepository).existsByLogin("login");

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getLogin(), userDto.getLogin());
    }

    @Test
    @DisplayName("Создание пользователя - логин уже занят")
    void testCreateUserAlreadyExists() {
        doReturn(true).when(userRepository).existsByLogin(anyString());

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("name");
        userCreateDto.setLogin("login");

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userCreateDto));

        verify(userRepository).existsByLogin("login");
    }

    @Test
    @DisplayName("Получение списка всех пользователей")
    void testGetAllUsers() {
        doReturn(List.of()).when(userRepository).findAll();

        List<UserDto> userDtos = userService.getUsers();

        verify(userRepository).findAll();

        assertEquals(0, userDtos.size());
    }

    @Test
    @DisplayName("Обновление пользователя - успех")
    void testUpdateUserSuccess() {
        User user = new User();
        user.setId(0L);
        user.setName("name");
        user.setLogin("login");
        doReturn(user).when(userRepository).save(any(User.class));
        doReturn(Optional.of(user)).when(userRepository).findById(anyLong());

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setName("name");
        userUpdateDto.setLogin("login");
        UserDto userDto = userService.updateUser(0, userUpdateDto);

        verify(userRepository).save(any(User.class));
        verify(userRepository).findById(0L);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getLogin(), userDto.getLogin());
    }

    @Test
    @DisplayName("Обновление пользователя - пользователь не найден")
    void testUpdateUserNotFound() {
        doReturn(Optional.empty()).when(userRepository).findById(anyLong());

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setName("name");
        userUpdateDto.setLogin("login");

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(0L, userUpdateDto));

        verify(userRepository).findById(0L);
    }
}
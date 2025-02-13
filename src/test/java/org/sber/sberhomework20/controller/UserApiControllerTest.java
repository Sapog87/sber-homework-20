package org.sber.sberhomework20.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sber.sberhomework20.dto.UserCreateDto;
import org.sber.sberhomework20.dto.UserDto;
import org.sber.sberhomework20.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserApiController.class)
class UserApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService service;

    @Test
    @DisplayName("Получение списка пользователей: пустой список")
    void testGetAllUsersEmptyList() throws Exception {
        doReturn(List.of()).when(service).getUsers();

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Получение списка пользователей: список с данными")
    void testGetAllUsersWithData() throws Exception {
        doReturn(List.of(new UserDto(), new UserDto())).when(service).getUsers();

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Получение пользователя по ID")
    void testGetUserById() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(0);
        userDto.setName("name");
        userDto.setLogin("login");

        doReturn(userDto).when(service).getUserById(0);

        mockMvc.perform(get("/api/users/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.login").value("login"));
    }

    @Test
    @DisplayName("Создание нового пользователя")
    void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(0);
        userDto.setName("name");
        userDto.setLogin("login");

        doReturn(userDto).when(service).createUser(any(UserCreateDto.class));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"name\":\"name\",\"login\":\"login\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.login").value("login"));
    }

    @Test
    @DisplayName("Обновление пользователя")
    void testUpdateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(0);
        userDto.setName("name");
        userDto.setLogin("login");

        doReturn(userDto).when(service).updateUser(eq(0L), any());

        mockMvc.perform(put("/api/users/0")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"name\":\"name\",\"login\":\"login\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.login").value("login"));
    }

    @Test
    @DisplayName("Удаление пользователя")
    void testDeleteUser() throws Exception {
        doNothing().when(service).deleteUserById(0);

        mockMvc.perform(delete("/api/users/0"))
                .andExpect(status().isOk());
    }
}
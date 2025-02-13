package org.sber.sberhomework20.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sber.sberhomework20.dto.UserCreateDto;
import org.sber.sberhomework20.dto.UserDto;
import org.sber.sberhomework20.dto.UserUpdateDto;
import org.sber.sberhomework20.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> index() {
        List<UserDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserCreateDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        URI location = URI.create("/api/users/" + createdUser.getId());
        return ResponseEntity.created(location).body(createdUser);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@PathVariable long id, @RequestBody @Valid UserUpdateDto user) {
        UserDto updateUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
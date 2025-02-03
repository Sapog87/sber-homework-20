package org.sber.sberhomework20.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.sber.sberhomework20.data.User;
import org.sber.sberhomework20.dto.UserCreateDto;
import org.sber.sberhomework20.dto.UserDto;
import org.sber.sberhomework20.dto.UserUpdateDto;
import org.sber.sberhomework20.exception.UserAlreadyExistsException;
import org.sber.sberhomework20.exception.UserNotFoundException;
import org.sber.sberhomework20.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.sber.sberhomework20.exception.ErrorMessages.USER_WITH_ID_NOT_FOUND;
import static org.sber.sberhomework20.exception.ErrorMessages.USER_WITH_LOGIN_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto getUserById(long id) {
        User user = findUserById(id);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        checkIfUserExistsById(id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDto createUser(@NonNull UserCreateDto userCreateDto) {
        String login = Objects.requireNonNull(userCreateDto.getLogin(), "login is null");
        checkIfUserNotExistsByLogin(login);

        User user = modelMapper.map(userCreateDto, User.class);

        User createdUser = userRepository.save(user);
        return modelMapper.map(createdUser, UserDto.class);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();

        return modelMapper.map(users,
                new TypeToken<List<UserDto>>() {
                }.getType());
    }

    @Override
    @Transactional
    public UserDto updateUser(long id, @NonNull UserUpdateDto userDto) {
        String login = Objects.requireNonNull(userDto.getLogin(), "login is null");
        String name = Objects.requireNonNull(userDto.getName(), "name is null");

        User user = findUserById(id);

        if (!Objects.equals(user.getLogin(), login)) {
            checkIfUserNotExistsByLogin(login);
            user.setLogin(login);
        }

        user.setName(name);

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    private User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(
                        USER_WITH_ID_NOT_FOUND.formatted(id))
        );
    }

    private void checkIfUserNotExistsByLogin(String login) {
        if (userRepository.existsByLogin(login)) {
            throw new UserAlreadyExistsException(
                    USER_WITH_LOGIN_ALREADY_EXISTS.formatted(login));
        }
    }

    private void checkIfUserExistsById(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(
                    USER_WITH_ID_NOT_FOUND.formatted(id));
        }
    }
}
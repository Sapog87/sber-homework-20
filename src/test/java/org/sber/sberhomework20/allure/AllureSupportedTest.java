package org.sber.sberhomework20.allure;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.sber.sberhomework20.data.User;
import org.sber.sberhomework20.dto.UserDto;
import org.sber.sberhomework20.repository.UserRepository;
import org.sber.sberhomework20.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@Epic("User management")
@Feature("User service")
@SpringBootTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class AllureSupportedTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @Story("Получение пользователя по ID")
    @Description("Тест для проверки поиска пользователя по id")
    void testGetUserByIdSuccess() {
        User user = new User();
        user.setId(0L);
        user.setName("name");
        user.setLogin("login");

        doReturn(Optional.of(user)).when(userRepository).findById(anyLong());

        UserDto userDto = userService.getUserById(0);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getLogin(), userDto.getLogin());
    }
}

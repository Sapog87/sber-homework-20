package org.sber.sberhomework20.restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerIT {
    @BeforeAll
    static void setUpBeforeClass() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    @DisplayName("Получение списка пользователей: пустой список")
    @Order(1)
    void testGetAllUsersEmptyList() {
        given()
                .when()
                .get("/api/users")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(0));
    }

    @Test
    @DisplayName("Создание нового пользователя")
    @Order(2)
    void testCreateUser() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"name\",\"login\":\"login\"}")
                .when().post("/api/users")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("name"))
                .body("login", equalTo("login"));
    }


    @Test
    @DisplayName("Обновление пользователя")
    @Order(3)
    void testUpdateUser() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"updatedName\",\"login\":\"updatedLogin\"}")
                .when().put("/api/users/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("updatedName"))
                .body("login", equalTo("updatedLogin"));
    }

    @Test
    @DisplayName("Получение пользователя по ID")
    @Order(4)
    void testGetUserById() {
        given()
                .when()
                .get("/api/users/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("updatedName"))
                .body("login", equalTo("updatedLogin"));
    }


    @Test
    @DisplayName("Удаление пользователя")
    @Order(5)
    void testDeleteUser() {
        given()
                .when()
                .delete("/api/users/1")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}

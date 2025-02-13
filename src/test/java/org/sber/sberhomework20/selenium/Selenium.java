package org.sber.sberhomework20.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class Selenium {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/users");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testFormSubmissionWithEmptyFields() {
        assertFalse(driver.findElement(By.id("name")).getAttribute("required").isEmpty());
        assertFalse(driver.findElement(By.id("login")).getAttribute("required").isEmpty());
    }

    @Test
    void testFormSubmissionWithInvalidData() {
        assertEquals("Создание пользователя", driver.getTitle());
        assertEquals("Создание нового пользователя", driver.findElement(By.tagName("h2")).getText());
    }
}

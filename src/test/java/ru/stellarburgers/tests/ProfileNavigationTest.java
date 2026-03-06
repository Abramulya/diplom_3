package ru.stellarburgers.tests;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.stellarburgers.BaseTest;
import ru.stellarburgers.pages.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
public class ProfileNavigationTest extends BaseTest {

    private String email;
    private String password;

    @BeforeEach
    public void loginBeforeTest() {
        email = generateUniqueEmail();
        password = "123456";
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();
        RegisterPage registerPage = loginPage.clickRegisterLink();
        loginPage = registerPage.register("Тестовый", email, password); //используется при регистрации
        mainPage = loginPage.login(email, password); //используется при авторизации
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    public void testGoToProfile() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();
        assertTrue(driver.getPageSource().contains("Выход"), "Переход в личный кабинет не выполнен");
    }
}
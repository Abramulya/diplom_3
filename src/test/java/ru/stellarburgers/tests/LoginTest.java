package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.stellarburgers.BaseTest;
import ru.stellarburgers.pages.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
public class LoginTest extends BaseTest {

    private String email;
    private String password;

    @BeforeEach
    public void setUpTestData() {
        email = generateUniqueEmail();
        password = "123456";
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();
        RegisterPage registerPage = loginPage.clickRegisterLink();
        registerPage.register("Тестовый", email, password); //используется при регистрации
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    @Description("Проверка входа через главную кнопку на главной странице")
    public void testLoginViaMainPageButton() {
        driver.get(BASE_URL);
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButtonMain()
                .login(email, password);

        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Проверка входа через кнопку Личный кабинет в шапке")
    public void testLoginViaPersonalAccountButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount()
                .login(email, password);

        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    @Description("Проверка входа через ссылку Войти в форме регистрации")
    public void testLoginViaRegisterForm() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount()
                .clickRegisterLink()
                .clickLoginLink()
                .login(email, password);

        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
    }

    @Test
    @DisplayName("Вход через форму восстановления пароля")
    @Description("Проверка входа через ссылку Войти в форме восстановления пароля")
    public void testLoginViaForgotPasswordForm() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount()
                .clickForgotPasswordLink()
                .clickLoginLink()
                .login(email, password);

        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
    }
}

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

        System.out.println("Регистрируем пользователя: " + email);
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();
        RegisterPage registerPage = loginPage.clickRegisterLink();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        loginPage = registerPage.register("Тестовый", email, password);

        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        System.out.println("Регистрация завершена");
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    @Description("Проверка входа через главную кнопку на главной странице")
    public void testLoginViaMainPageButton() {
        System.out.println("Тест: вход через главную кнопку");

        driver.get(BASE_URL);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButtonMain()
                .login(email, password);

        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
        System.out.println("Тест пройден");
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Проверка входа через кнопку Личный кабинет в шапке")
    public void testLoginViaPersonalAccountButton() {
        System.out.println("Тест: вход через Личный кабинет");

        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount()
                .login(email, password);

        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
        System.out.println("Тест пройден");
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    @Description("Проверка входа через ссылку Войти в форме регистрации")
    public void testLoginViaRegisterForm() {
        System.out.println("Тест: вход через форму регистрации");

        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount()
                .clickRegisterLink()
                .clickLoginLink()
                .login(email, password);

        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
        System.out.println("Тест пройден");
    }

    @Test
    @DisplayName("Вход через форму восстановления пароля")
    @Description("Проверка входа через ссылку Войти в форме восстановления пароля")
    public void testLoginViaForgotPasswordForm() {
        System.out.println("Тест: вход через форму восстановления пароля");

        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount()
                .clickForgotPasswordLink()
                .clickLoginLink()
                .login(email, password);

        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
        System.out.println("Тест пройден");
    }
}

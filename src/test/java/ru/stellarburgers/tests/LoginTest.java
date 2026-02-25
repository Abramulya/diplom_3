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
public class LoginTest extends BaseTest {

    private String email;
    private String password;

    @BeforeEach
    public void setUpTestData() {
        // Создаем тестовые данные для входа
        email = "testuser" + System.currentTimeMillis() + "@yandex.ru";
        password = "123456";

        // Предварительно регистрируем пользователя через UI, чтобы был с кем тестировать вход
        new MainPage(driver)
                .clickPersonalAccount()
                .clickRegisterLink()
                .register("Тестовый", email, password);
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    public void testLoginViaMainPageButton() {
        MainPage mainPage = new MainPage(driver);

        // Возвращаемся на главную (если ушли)
        driver.get(BASE_URL);

        mainPage.clickLoginButtonMain()
                .login(email, password);

        // Проверяем, что вход выполнен (кнопка "Оформить заказ" доступна)
        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    public void testLoginViaPersonalAccountButton() {
        new MainPage(driver)
                .clickPersonalAccount()
                .login(email, password);

        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void testLoginViaRegisterForm() {
        new MainPage(driver)
                .clickPersonalAccount()
                .clickRegisterLink()
                .clickLoginLink()
                .login(email, password);

        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void testLoginViaForgotPasswordForm() {
        new MainPage(driver)
                .clickPersonalAccount()
                .clickForgotPasswordLink()
                .clickLoginLink()
                .login(email, password);

        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Вход не выполнен");
    }
}

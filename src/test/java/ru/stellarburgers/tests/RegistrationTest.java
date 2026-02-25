package ru.stellarburgers.tests;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.stellarburgers.BaseTest;
import ru.stellarburgers.pages.MainPage;
import ru.stellarburgers.pages.RegisterPage;
import ru.stellarburgers.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(AllureJunit5.class)
public class RegistrationTest extends BaseTest {

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    public void testSuccessfulRegistration() {
        String name = "Тестовый";
        String email = generateUniqueEmail();
        String password = "123456";

        LoginPage loginPage = new MainPage(driver)
                .clickPersonalAccount()  // Клик на Личный кабинет
                .clickRegisterLink()      // Клик на ссылку регистрации
                .register(name, email, password); // Регистрация

        // Проверяем, что после успешной регистрации открылась страница входа
        assertTrue(loginPage.isDisplayed(), "Страница входа не открылась после регистрации");

        // Дополнительно проверим, что можно залогиниться созданным пользователем
        MainPage mainPage = loginPage.login(email, password);
        // Проверяем, что после логина видна главная страница (например, кнопка оформления заказа)
        assertTrue(driver.getPageSource().contains("Оформить заказ"), "Не удалось залогиниться созданным пользователем");
    }

    @Test
    @DisplayName("Ошибка при регистрации с паролем меньше 6 символов")
    public void testRegistrationWithShortPassword() {
        String name = "Тестовый";
        String email = generateUniqueEmail();
        String shortPassword = "12345"; // 5 символов

        RegisterPage registerPage = new MainPage(driver)
                .clickPersonalAccount()
                .clickRegisterLink()
                .registerWithInvalidPassword(name, email, shortPassword);

        // Проверяем, что появилась ошибка о некорректном пароле
        assertTrue(registerPage.isPasswordErrorDisplayed(), "Ошибка о некорректном пароле не отображается");

        // Дополнительно проверим, что остались на странице регистрации (кнопка регистрации видна)
        assertTrue(driver.getPageSource().contains("Зарегистрироваться"), "Произошел переход на другую страницу");
    }
}

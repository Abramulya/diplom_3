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
public class LogoutTest extends BaseTest {

    private String email;
    private String password;

    @BeforeEach
    public void loginBeforeTest() {
        email = generateUniqueEmail();
        password = "123456";
        System.out.println("Регистрируем пользователя: " + email);
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();
        RegisterPage registerPage = loginPage.clickRegisterLink();
        waitForSeconds(1);
        loginPage = registerPage.register("Тестовый", email, password);
        waitForSeconds(2);
        mainPage = loginPage.login(email, password);
        waitForSeconds(2);
        System.out.println("Пользователь залогинен");
    }

    @Test
    @DisplayName("Выход из аккаунта по кнопке 'Выйти' в личном кабинете")
    @Description("Проверка выхода из аккаунта через кнопку Выйти в ЛК")
    public void testLogout() {
        System.out.println("Тест: выход из аккаунта");
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();
        waitForSeconds(2);
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogoutButton();
        waitForSeconds(2);
        boolean isLoginButtonVisible = driver.getPageSource().contains("Войти");
        assertTrue(isLoginButtonVisible, "Выход не выполнен - кнопка 'Войти' не найдена");

        System.out.println("Тест пройден");
    }

    private void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
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
public class ProfileNavigationTest extends BaseTest {

    private String email;
    private String password;

    @BeforeEach
    public void loginBeforeTest() {
        email = generateUniqueEmail();
        password = "123456";

        // Регистрируемся
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();
        RegisterPage registerPage = loginPage.clickRegisterLink();

        // Ждем загрузки страницы регистрации
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Регистрируем и сразу логинимся
        loginPage = registerPage.register("Тестовый", email, password);

        // Ждем загрузки страницы логина
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Логинимся
        mainPage = loginPage.login(email, password);

        // Ждем загрузки главной
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    public void testGoToProfile() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();

        // Ждем загрузки профиля
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue(driver.getPageSource().contains("Выход"), "Переход в личный кабинет не выполнен");
    }
}
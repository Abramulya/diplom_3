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
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();
        RegisterPage registerPage = loginPage.clickRegisterLink();
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        loginPage = registerPage.register("Тестовый", email, password);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        mainPage = loginPage.login(email, password);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    public void testGoToProfile() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccount();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue(driver.getPageSource().contains("Выход"), "Переход в личный кабинет не выполнен");
    }
}
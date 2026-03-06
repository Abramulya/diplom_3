package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.stellarburgers.BaseTest;
import ru.stellarburgers.pages.MainPage;
import ru.stellarburgers.pages.LoginPage;
import ru.stellarburgers.pages.RegisterPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
public class RegistrationTest extends BaseTest {

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка успешной регистрации нового пользователя")
    public void testSuccessfulRegistration() {
        String name = "Тестовый";
        String email = generateUniqueEmail();
        String password = "123456";

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();

        RegisterPage registerPage = loginPage.clickRegisterLink();
        loginPage = registerPage.register(name, email, password);
        boolean isLoginPageDisplayed = loginPage.isDisplayed();
        assertTrue(isLoginPageDisplayed, "Страница входа не открылась после регистрации");

        mainPage = loginPage.login(email, password);
        boolean isOrderButtonVisible = driver.getPageSource().contains("Оформить заказ");
        assertTrue(isOrderButtonVisible, "Не удалось залогиниться созданным пользователем");
    }

    @Test
    @DisplayName("Ошибка при регистрации с паролем менее 6 символов")
    @Description("Проверка появления ошибки при регистрации с коротким паролем")
    public void testRegistrationWithShortPassword() {
        String name = "Тестовый";
        String email = generateUniqueEmail();
        String shortPassword = "12345";

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();
        RegisterPage registerPage = loginPage.clickRegisterLink();
        registerPage.registerWithInvalidPassword(name, email, shortPassword);

        boolean isErrorDisplayed = registerPage.isPasswordErrorDisplayed();
        assertTrue(isErrorDisplayed, "Ошибка о некорректном пароле не появилась");
    }
}
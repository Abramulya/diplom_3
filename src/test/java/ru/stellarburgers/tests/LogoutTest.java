package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.stellarburgers.BaseTest;
import ru.stellarburgers.pages.MainPage;
import ru.stellarburgers.pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
public class LogoutTest extends BaseTest {

    private String email;
    private String password;

    @BeforeEach
    public void loginBeforeTest() {
        email = generateUniqueEmail();
        password = "123456";

        new MainPage(driver)
                .clickPersonalAccount()
                .clickRegisterLink()
                .register("Тестовый", email, password)
                .login(email, password);
    }

    @Test
    @DisplayName("Выход из аккаунта по кнопке 'Выйти' в личном кабинете")
    @Description("Проверка выхода из аккаунта через кнопку Выйти в ЛК")
    public void testLogout() {
        // Заходим в личный кабинет
        new MainPage(driver).clickPersonalAccount();

        // Выходим из аккаунта
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogoutButton();

        // Проверяем, что вышли (появилась кнопка "Войти")
        assertTrue(driver.getPageSource().contains("Войти"), "Выход не выполнен");
    }
}
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

        System.out.println("Тест: успешная регистрация");
        System.out.println("Email: " + email);

        // Шаг 1: Переход на страницу регистрации
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();
        waitForSeconds(1);

        RegisterPage registerPage = loginPage.clickRegisterLink();
        waitForSeconds(1);

        // Шаг 2: Регистрация
        System.out.println("Регистрируем пользователя...");
        loginPage = registerPage.register(name, email, password);
        waitForSeconds(2); // Ждем загрузки страницы логина

        // Шаг 3: Проверяем, что страница логина отображается
        boolean isLoginPageDisplayed = loginPage.isDisplayed();
        System.out.println("Страница логина отображается: " + isLoginPageDisplayed);
        assertTrue(isLoginPageDisplayed, "Страница входа не открылась после регистрации");

        // Шаг 4: Логинимся созданным пользователем
        System.out.println("Пробуем залогиниться...");
        mainPage = loginPage.login(email, password);
        waitForSeconds(2); // Ждем загрузки главной страницы

        // Шаг 5: Проверяем, что вход выполнен
        boolean isOrderButtonVisible = driver.getPageSource().contains("Оформить заказ");
        System.out.println("Кнопка 'Оформить заказ' видна: " + isOrderButtonVisible);
        assertTrue(isOrderButtonVisible, "Не удалось залогиниться созданным пользователем");

        System.out.println("Тест пройден успешно!");
    }

    @Test
    @DisplayName("Ошибка при регистрации с паролем менее 6 символов")
    @Description("Проверка появления ошибки при регистрации с коротким паролем")
    public void testRegistrationWithShortPassword() {
        String name = "Тестовый";
        String email = generateUniqueEmail();
        String shortPassword = "12345"; // 5 символов

        System.out.println("Тест: регистрация с коротким паролем");
        System.out.println("Email: " + email);

        // Шаг 1: Переход на страницу регистрации
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();
        waitForSeconds(1);

        RegisterPage registerPage = loginPage.clickRegisterLink();
        waitForSeconds(1);

        // Шаг 2: Пытаемся зарегистрироваться с коротким паролем
        System.out.println("Пробуем зарегистрироваться с паролем: " + shortPassword);
        registerPage.registerWithInvalidPassword(name, email, shortPassword);
        waitForSeconds(1); // Ждем появления ошибки

        // Шаг 3: Проверяем, что ошибка отображается
        boolean isErrorDisplayed = registerPage.isPasswordErrorDisplayed();
        System.out.println("Ошибка отображается: " + isErrorDisplayed);
        assertTrue(isErrorDisplayed, "Ошибка о некорректном пароле не появилась");

        System.out.println("Тест пройден успешно!");
    }

    // Метод ожидания
    private void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
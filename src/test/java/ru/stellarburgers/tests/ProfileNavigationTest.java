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
public class ProfileNavigationTest extends BaseTest {

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
    @DisplayName("Переход в личный кабинет по клику на 'Личный кабинет'")
    @Description("Проверка перехода в личный кабинет авторизованного пользователя")
    public void testGoToProfile() {
        new MainPage(driver).clickPersonalAccount();

        // Проверяем, что мы в профиле (кнопка "Выход" видна)
        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue(driver.getPageSource().contains("Выход"), "Переход в личный кабинет не выполнен");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на 'Конструктор'")
    @Description("Проверка перехода из ЛК в конструктор через кнопку Конструктор")
    public void testGoToConstructorFromProfile() {
        // Заходим в личный кабинет
        new MainPage(driver).clickPersonalAccount();

        // Кликаем на Конструктор
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickConstructor();

        // Проверяем, что мы в конструкторе
        assertTrue(driver.getPageSource().contains("Соберите бургер"), "Переход в конструктор не выполнен");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотип")
    @Description("Проверка перехода из ЛК в конструктор через логотип Stellar Burgers")
    public void testGoToConstructorFromProfileViaLogo() {
        // Заходим в личный кабинет
        new MainPage(driver).clickPersonalAccount();

        // Кликаем на логотип
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogo();

        // Проверяем, что мы в конструкторе
        assertTrue(driver.getPageSource().contains("Соберите бургер"), "Переход в конструктор по логотипу не выполнен");
    }
}
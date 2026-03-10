package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.stellarburgers.BaseTest;
import ru.stellarburgers.pages.MainPage;
import ru.stellarburgers.pages.LoginPage;
import ru.stellarburgers.pages.RegisterPage;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
public class RegistrationTest extends BaseTest {

    // Поля для хранения данных созданного пользователя
    private String createdUserEmail;
    private String createdUserPassword;
    private String createdUserName;

    // Базовый URL API (можно вынести в константы)
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";
    private static final String API_LOGIN = "/api/auth/login";
    private static final String API_USER = "/api/auth/user";

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка успешной регистрации нового пользователя")
    public void testSuccessfulRegistration() {
        createdUserName = "Тестовый";
        createdUserEmail = generateUniqueEmail();
        createdUserPassword = "123456";

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();

        RegisterPage registerPage = loginPage.clickRegisterLink();
        registerPage.register(createdUserName, createdUserEmail, createdUserPassword);
        boolean isLoginPageDisplayed = loginPage.isDisplayed();
        assertTrue(isLoginPageDisplayed, "Страница входа не открылась после регистрации");

        loginPage.login(createdUserEmail, createdUserPassword);
        boolean isOrderButtonVisible = driver.getPageSource().contains("Оформить заказ");
        assertTrue(isOrderButtonVisible, "Не удалось залогиниться созданным пользователем");
    }

    @Test
    @DisplayName("Ошибка при регистрации с паролем менее 6 символов")
    @Description("Проверка появления ошибки при регистрации с коротким паролем")
    public void testRegistrationWithShortPassword() {
        createdUserName = "Тестовый";
        createdUserEmail = generateUniqueEmail();
        createdUserPassword = "12345";

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = mainPage.clickPersonalAccount();
        RegisterPage registerPage = loginPage.clickRegisterLink();
        registerPage.registerWithInvalidPassword(createdUserName, createdUserEmail, createdUserPassword);

        boolean isErrorDisplayed = registerPage.isPasswordErrorDisplayed();
        assertTrue(isErrorDisplayed, "Ошибка о некорректном пароле не появилась");

        createdUserEmail = null;
        createdUserPassword = null;
        createdUserName = null;
    }

    @AfterEach
    @Step("Удаление пользователя через API")
    public void deleteUser() {
        // Если пользователь не был создан (например, в тесте с коротким паролем) - выходим
        if (createdUserEmail == null || createdUserPassword == null) {
            return;
        }

        try {
            String accessToken = loginAndGetToken(createdUserEmail, createdUserPassword);

            if (accessToken != null) {
                deleteUserByToken(accessToken);
            } else {
                System.out.println("Не удалось получить токен для пользователя: " + createdUserEmail);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    private String loginAndGetToken(String email, String password) {
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(BASE_URL + API_LOGIN);

        if (response.statusCode() == 200) {
            String token = response.jsonPath().getString("accessToken");
            return token;
        } else {
            return null;
        }
    }

    private void deleteUserByToken(String token) {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .when()
                .delete(BASE_URL + API_USER);
    }
}
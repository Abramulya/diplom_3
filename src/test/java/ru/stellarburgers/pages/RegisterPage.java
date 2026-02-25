package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    private final WebDriver driver;

    private final By nameField = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By emailField = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath(".//input[@type='password']");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath(".//a[text()='Войти']");
    private final By errorMessage = By.xpath(".//p[text()='Некорректный пароль']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Заполнить имя: {name}")
    public RegisterPage setName(String name) {
        driver.findElement(nameField).sendKeys(name);
        return this;
    }

    @Step("Заполнить email: {email}")
    public RegisterPage setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    @Step("Заполнить пароль: {password}")
    public RegisterPage setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    @Step("Клик на кнопку 'Зарегистрироваться'")
    public LoginPage clickRegisterButton() {
        driver.findElement(registerButton).click();
        return new LoginPage(driver);
    }

    @Step("Регистрация пользователя")
    public LoginPage register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        return clickRegisterButton();
    }

    @Step("Клик на ссылку 'Войти'")
    public LoginPage clickLoginLink() {
        driver.findElement(loginLink).click();
        return new LoginPage(driver);
    }

    @Step("Проверить, что отображается ошибка пароля")
    public boolean isPasswordErrorDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    // Добавим метод для регистрации, который не возвращает страницу логина при ошибке
    @Step("Попытка регистрации с некорректным паролем")
    public RegisterPage registerWithInvalidPassword(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        driver.findElement(registerButton).click();
        return this;
    }
}
package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;

    private final By emailField = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath(".//input[@type='password']");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By registerLink = By.xpath(".//a[text()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath(".//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Заполнить email: {email}")
    public LoginPage setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    @Step("Заполнить пароль: {password}")
    public LoginPage setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    @Step("Клик на кнопку 'Войти'")
    public MainPage clickLoginButton() {
        driver.findElement(loginButton).click();
        return new MainPage(driver);
    }

    @Step("Логин пользователя")
    public MainPage login(String email, String password) {
        setEmail(email);
        setPassword(password);
        return clickLoginButton();
    }

    @Step("Клик на ссылку 'Зарегистрироваться'")
    public RegisterPage clickRegisterLink() {
        driver.findElement(registerLink).click();
        return new RegisterPage(driver);
    }

    @Step("Клик на ссылку 'Восстановить пароль'")
    public ForgotPasswordPage clickForgotPasswordLink() {
        driver.findElement(forgotPasswordLink).click();
        return new ForgotPasswordPage(driver);
    }

    // Добавим метод проверки отображения страницы логина
    @Step("Проверить отображение страницы логина")
    public boolean isDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }
}

package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath("//input[@type='password']");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By registerLink = By.xpath(".//a[text()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath(".//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Заполнить email: {email}")
    public LoginPage setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        return this;
    }

    @Step("Заполнить пароль")
    public LoginPage setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    @Step("Клик на кнопку 'Войти'")
    public MainPage clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(".//button[text()='Оформить заказ']")));

        return new MainPage(driver);
    }

    @Step("Полный логин")
    public MainPage login(String email, String password) {
        setEmail(email);
        setPassword(password);
        return clickLoginButton();
    }

    @Step("Клик на ссылку регистрации")
    public RegisterPage clickRegisterLink() {
        driver.findElement(registerLink).click();
        return new RegisterPage(driver);
    }

    @Step("Клик на ссылку восстановления пароля")
    public ForgotPasswordPage clickForgotPasswordLink() {
        driver.findElement(forgotPasswordLink).click();
        return new ForgotPasswordPage(driver);
    }

    @Step("Проверить отображение страницы логина")
    public boolean isDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
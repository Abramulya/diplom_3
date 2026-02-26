package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegisterPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath("//input[@type='password']");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath(".//a[text()='Войти']");
    private final By errorMessage = By.xpath(".//p[text()='Некорректный пароль']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Заполнить имя: {name}")
    public RegisterPage setName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        return this;
    }

    @Step("Заполнить email: {email}")
    public RegisterPage setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    @Step("Заполнить пароль")
    public RegisterPage setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    @Step("Клик на кнопку 'Зарегистрироваться'")
    public LoginPage clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();

        // Ждем загрузки страницы логина (появление кнопки "Войти")
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(".//button[text()='Войти']")));

        return new LoginPage(driver);
    }

    @Step("Полная регистрация")
    public LoginPage register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        return clickRegisterButton();
    }

    @Step("Регистрация с невалидным паролем")
    public RegisterPage registerWithInvalidPassword(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
        return this;
    }

    @Step("Клик на ссылку входа")
    public LoginPage clickLoginLink() {
        driver.findElement(loginLink).click();
        return new LoginPage(driver);
    }

    @Step("Проверить, что ошибка пароля отображается")
    public boolean isPasswordErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
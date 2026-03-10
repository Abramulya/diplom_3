package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By logoutButton = By.xpath(".//button[text()='Выход']");
    private final By constructorLink = By.xpath(".//p[text()='Конструктор']");
    private final By stellarLogo = By.className("AppHeader_header__logo__2D0X2");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Клик на кнопку 'Выход' с ожиданием")
    public LoginPage clickLogoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(".//button[text()='Войти']")));

        return new LoginPage(driver);
    }

    @Step("Клик на 'Конструктор'")
    public MainPage clickConstructor() {
        wait.until(ExpectedConditions.elementToBeClickable(constructorLink)).click();
        return new MainPage(driver);
    }

    @Step("Клик на логотип")
    public MainPage clickLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(stellarLogo)).click();
        return new MainPage(driver);
    }
}
package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage {
    private final WebDriver driver;

    private final By logoutButton = By.xpath(".//button[text()='Выход']");
    private final By constructorLink = By.xpath(".//p[text()='Конструктор']");
    private final By stellarLogo = By.className("AppHeader_header__logo__2D0X2");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик на кнопку 'Выход'")
    public LoginPage clickLogoutButton() {
        driver.findElement(logoutButton).click();
        return new LoginPage(driver);
    }

    @Step("Клик на 'Конструктор' из профиля")
    public MainPage clickConstructor() {
        driver.findElement(constructorLink).click();
        return new MainPage(driver);
    }

    @Step("Клик на логотип из профиля")
    public MainPage clickLogo() {
        driver.findElement(stellarLogo).click();
        return new MainPage(driver);
    }
}
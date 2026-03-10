package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginButtonMain = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath(".//p[text()='Личный Кабинет']");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private final By stellarLogo = By.className("AppHeader_header__logo__2D0X2");
    private final By bunsTab = By.xpath(".//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath(".//span[text()='Начинки']/parent::div");
    private final By currentTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG tab_tab_type_current__2BEPc')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Клик на кнопку 'Войти в аккаунт' на главной")
    public LoginPage clickLoginButtonMain() {
        driver.findElement(loginButtonMain).click();
        return new LoginPage(driver);
    }

    @Step("Клик на 'Личный кабинет'")
    public LoginPage clickPersonalAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(".//button[text()='Сохранить' or text()='Войти']")));
        return new LoginPage(driver);
    }

    @Step("Клик на 'Конструктор'")
    public MainPage clickConstructor() {
        driver.findElement(constructorButton).click();
        return this;
    }

    @Step("Клик на логотип Stellar Burgers")
    public MainPage clickLogo() {
        driver.findElement(stellarLogo).click();
        return this;
    }

    @Step("Клик на раздел 'Булки'")
    public boolean clickBunsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
        try {
            wait.until(driver -> {
                String classAttr = driver.findElement(bunsTab).getAttribute("class");
                return classAttr != null && classAttr.contains("tab_tab_type_current");
            });
            return true; // таб активировался успешно
        } catch (Exception e) {
            return false; // таб не активировался за отведенное время
        }
    }

    @Step("Клик на раздел 'Соусы'")
    public boolean clickSaucesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
        try {
            wait.until(driver -> {
                String classAttr = driver.findElement(saucesTab).getAttribute("class");
                return classAttr != null && classAttr.contains("tab_tab_type_current");
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Клик на раздел 'Начинки'")
    public boolean clickFillingsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
        try {
            wait.until(driver -> {
                String classAttr = driver.findElement(fillingsTab).getAttribute("class");
                return classAttr != null && classAttr.contains("tab_tab_type_current");
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
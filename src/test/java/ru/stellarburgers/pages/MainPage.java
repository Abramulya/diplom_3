package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;

    // Локаторы
    private final By loginButtonMain = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath(".//p[text()='Личный Кабинет']");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private final By stellarLogo = By.className("AppHeader_header__logo__2D0X2");
    private final By bunsTab = By.xpath(".//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath(".//span[text()='Начинки']/parent::div");
    private final By fillingsTabHeader = By.xpath(".//h2[text()='Начинки']");
    private final By currentTab = By.xpath("//div[contains(@class, 'tab_tab__1SPyG tab_tab_type_current__2BEPc')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик на кнопку 'Войти в аккаунт' на главной")
    public LoginPage clickLoginButtonMain() {
        driver.findElement(loginButtonMain).click();
        return new LoginPage(driver);
    }

    @Step("Клик на 'Личный кабинет'")
    public LoginPage clickPersonalAccount() {
        driver.findElement(personalAccountButton).click();
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
    public MainPage clickBunsTab() {
        driver.findElement(bunsTab).click();
        waitForTabToBeSelected();
        return this;
    }

    @Step("Клик на раздел 'Соусы'")
    public MainPage clickSaucesTab() {
        driver.findElement(saucesTab).click();
        waitForTabToBeSelected();
        return this;
    }

    @Step("Клик на раздел 'Начинки'")
    public MainPage clickFillingsTab() {
        driver.findElement(fillingsTab).click();
        waitForTabToBeSelected();
        return this;
    }

    @Step("Проверить, что выбранный таб - текущий")
    public boolean isTabSelected(By tabLocator) {
        String tabClass = driver.findElement(tabLocator).getAttribute("class");
        return tabClass.contains("tab_tab_type_current");
    }

    // Добавим методы для проверки выбранного раздела
    @Step("Проверить, что выбран раздел 'Булки'")
    public boolean isBunsSectionSelected() {
        String bunsClass = driver.findElement(bunsTab).getAttribute("class");
        return bunsClass.contains("tab_tab_type_current");
    }

    @Step("Проверить, что выбран раздел 'Соусы'")
    public boolean isSaucesSectionSelected() {
        String saucesClass = driver.findElement(saucesTab).getAttribute("class");
        return saucesClass.contains("tab_tab_type_current");
    }

    @Step("Проверить, что выбран раздел 'Начинки'")
    public boolean isFillingsSectionSelected() {
        String fillingsClass = driver.findElement(fillingsTab).getAttribute("class");
        return fillingsClass.contains("tab_tab_type_current");
    }

    private void waitForTabToBeSelected() {
        System.out.println("Start to wait 3 sec...");
        try {
            Thread.sleep(3000);
            System.out.println("Wait end");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
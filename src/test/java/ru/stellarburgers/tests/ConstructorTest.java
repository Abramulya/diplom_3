package ru.stellarburgers.tests;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.stellarburgers.BaseTest;
import ru.stellarburgers.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
public class ConstructorTest extends BaseTest {

    @Test
    @DisplayName("Переход к разделу 'Булки' в конструкторе")
    public void testBunsSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesTab();
        boolean isSelected = mainPage.clickBunsTab();
        assertTrue(isSelected, "The 'Buns' section is not selected");
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы' в конструкторе")
    public void testSaucesSection() {
        MainPage mainPage = new MainPage(driver);
        boolean isSelected = mainPage.clickSaucesTab();
        assertTrue(isSelected, "The 'Sauces' section is not selected");
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки' в конструкторе")
    public void testFillingsSection() {
        MainPage mainPage = new MainPage(driver);
        boolean isSelected = mainPage.clickFillingsTab();
        assertTrue(isSelected, "The 'Fillings' section is not selected");
    }
}
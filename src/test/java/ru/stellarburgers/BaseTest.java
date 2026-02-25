package ru.stellarburgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected static final String BASE_URL = "https://stellarburgers.education-services.ru";

    @BeforeEach
    @Step("Настройка браузера и запуск драйвера")
    public void setUp() {
        // Получаем браузер из системной переменной, по умолчанию - chrome
        String browser = System.getProperty("browser", "chrome");

        if ("yandex".equals(browser)) {
            // Для Яндекс.Браузера используем ChromeOptions, но указываем путь к Yandex
            WebDriverManager.chromedriver().setup(); // Яндекс использует тот же драйвер, что и Chrome
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:/Users/UserName/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
            driver = new ChromeDriver(options);
        } else {
            // Для Google Chrome
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    @AfterEach
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("Генерация уникального email")
    public String generateUniqueEmail() {
        return "testuser" + System.currentTimeMillis() + "@yandex.ru";
    }
}

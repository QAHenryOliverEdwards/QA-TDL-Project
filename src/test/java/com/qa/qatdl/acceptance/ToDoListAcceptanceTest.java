package com.qa.qatdl.acceptance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ToDoListAcceptanceTest {

    @TestConfiguration
    static class AppConfig {
        @Bean
        public WebDriver webDriver() {
            return new FirefoxDriver();
        }
    }

    @Autowired
    private static WebDriver driver;

    private static WebElement element;

    @LocalServerPort
    private int port;

    @Autowired


    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/driver/chrome/windows/chromedriver.exe");

        driver = new ChromeDriver();
    }

    @AfterAll
    public static void cleanup() {
        driver.quit();
    }

    @Test
    public void createToDoListTest() throws InterruptedException {
        driver.navigate().to("http://localhost:" + port + "/index.html");

        element = driver.findElement(By.cssSelector("#to-do-list-name"));
        element.sendKeys("Shopping List");

        element = driver.findElement(By.cssSelector("#to-do-list-create > form:nth-child(2) > button:nth-child(3)"));
        element.click();

        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > th:nth-child(1)"));
        assertEquals(element.getText(), "1");

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > td:nth-child(2)"));
        assertEquals(element.getText(), "1");

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > td:nth-child(3)"));
        assertEquals(element.getText(), "Shopping List");

    }
}

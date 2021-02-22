package com.qa.qatdl.acceptance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "classpath:test-drop-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:test-schema.sql", "classpath:test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("dev")
public class TaskAcceptanceTest {

    @Autowired
    private static WebDriver driver;

    private static WebElement element;

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
    public void createTaskTest() throws InterruptedException {
        Thread.sleep(500);
        driver.get("http://localhost:8393");

        element = driver.findElement(By.cssSelector("body > main > div > div > div.col-3 > div > div:nth-child(2) > a"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#task-menu > a:nth-child(1)"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#task-name"));
        element.sendKeys("Turkey");

        element = driver.findElement(By.cssSelector("#task-description"));
        element.sendKeys("14.99");

        element = driver.findElement(By.cssSelector("#task-to-do-list"));
        element.sendKeys("1");

        element = driver.findElement(By.cssSelector("#task-create > form > div:nth-child(3) > button"));
        element.click();

        Thread.sleep(5000);
    }
}

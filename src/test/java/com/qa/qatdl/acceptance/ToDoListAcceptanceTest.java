package com.qa.qatdl.acceptance;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "classpath:test-drop-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:test-schema.sql", "classpath:test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("dev")
public class ToDoListAcceptanceTest {

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
    public void createToDoListTest() throws InterruptedException {
        Thread.sleep(500);
        driver.get("http://localhost:8393");

        element = driver.findElement(By.cssSelector("body > main > div > div > div.col-3 > div > div:nth-child(1) > a"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#to-do-list-menu > a:nth-child(1)"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#to-do-list-name"));
        element.sendKeys("Shopping List");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#to-do-list-create > form:nth-child(2) > button:nth-child(3)"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        Thread.sleep(1000);

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > th:nth-child(1)"));
        assertEquals(element.getText(), "1");

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > td:nth-child(2)"));
        assertEquals(element.getText(), "3");

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > td:nth-child(3)"));
        assertEquals(element.getText(), "Shopping List");

    }

    @Test
    public void readToDoListTest() throws InterruptedException {
        Thread.sleep(500);
        driver.get("http://localhost:8393");

        element = driver.findElement(By.cssSelector("body > main > div > div > div.col-3 > div > div:nth-child(1) > a"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#to-do-list-menu > a:nth-child(2)"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#to-do-list-read-all"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        Thread.sleep(1000);

        String elementText = driver.findElements(By.cssSelector("#table-body")).stream().map(WebElement::getText).collect(Collectors.toList()).toString().replace("\n", " ");
        System.out.println(elementText);
        String expected = "[1 1 General Chicken, Ham 2 2 Music To Buy]";
        assertEquals(expected, elementText);

        element = driver.findElement(By.cssSelector("#to-do-list-read-id"));
        element.sendKeys("1");

        element = driver.findElement(By.cssSelector("#to-do-list-read > form > div > div:nth-child(3) > button"));
        element.click();
        Thread.sleep(1000);

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > th:nth-child(1)"));
        assertEquals(element.getText(), "1");

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > td:nth-child(2)"));
        assertEquals(element.getText(), "1");

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > td:nth-child(3)"));
        assertEquals(element.getText(), "General");
    }

    @Test
    public void updateToDoListTest() throws InterruptedException {
        Thread.sleep(500);
        driver.get("http://localhost:8393");

        element = driver.findElement(By.cssSelector("body > main > div > div > div.col-3 > div > div:nth-child(1) > a"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#to-do-list-menu > a:nth-child(3)"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#to-do-list-update-id"));
        element.sendKeys("1");

        element = driver.findElement(By.cssSelector("#to-do-list-new-name"));
        element.sendKeys("Games To Get");

        element = driver.findElement(By.cssSelector("#to-do-list-update > form > div:nth-child(2) > button"));
        element.click();

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > th:nth-child(1)"));
        assertEquals(element.getText(), "1");

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > td:nth-child(2)"));
        assertEquals(element.getText(), "1");

        element = driver.findElement(By.cssSelector("#table-body > tr:nth-child(1) > td:nth-child(3)"));
        assertEquals(element.getText(), "Games To Get");
    }

    @Test
    public void deleteToDoListTest() throws InterruptedException {
        Thread.sleep(500);
        driver.get("http://localhost:8393");

        element = driver.findElement(By.cssSelector("body > main > div > div > div.col-3 > div > div:nth-child(1) > a"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#to-do-list-menu > a:nth-child(4)"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#to-do-list-delete-id"));
        element.sendKeys("1");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        element = driver.findElement(By.cssSelector("#to-do-list-delete > form > button"));
        element.click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        Thread.sleep(1000);

        element = driver.findElement(By.cssSelector("#table-body"));
        assertEquals("", element.getText());
    }
}

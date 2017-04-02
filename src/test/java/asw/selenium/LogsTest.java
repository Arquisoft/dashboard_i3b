package asw.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import asw.Application;
import asw.kafka.mockProducer.MessageProducer;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest(classes = Application.class)
public class LogsTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;

    @Autowired
    private MessageProducer mp;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8090";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testLogs() throws Exception {
        driver.get(baseUrl + "/");
        Thread.sleep(1000);
        mp.send("councilStaff");
        Thread.sleep(1000);
        mp.send("councilmen");
        Thread.sleep(1000);
        mp.send("otherAuthorities");
        Thread.sleep(1000);
        mp.send("councilStaff");
        Thread.sleep(1000);
        mp.send("councilmen");
        Thread.sleep(1000);
        mp.send("otherAuthorities");
        Thread.sleep(1000);
        assertTrue(driver.findElement(By.id("logscouncilstaff")).getText().contains("CouncilStaff log: MESSAGE TEST LOG councilStaff"));
        assertTrue(driver.findElement(By.id("logscouncilmen")).getText().contains("Councilmen log: MESSAGE TEST LOG councilmen"));
        assertTrue(driver.findElement(By.id("logsOtherAuth")).getText().contains("Other Authorities log: MESSAGE TEST LOG otherAuthorities"));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}

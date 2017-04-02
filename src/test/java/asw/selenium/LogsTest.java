package asw.selenium;

import asw.Application;
import asw.kafka.mockProducer.MessageProducer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:8070")
public class LogsTest {
    private WebDriver driver;
    private URI baseUrl;
    private boolean acceptNextAlert = true;

    @Autowired
    private MessageProducer mp;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = new URI("http://localhost:8070/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testLogs() throws Exception {
        driver.get(baseUrl.toString());
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
        assertTrue(driver.getPageSource().contains("Dashboard"));
        //assertTrue(driver.findElement(By.id("logscouncilstaff")).getText().contains("CouncilStaff log: MESSAGE TEST LOG councilStaff"));
        //assertTrue(driver.findElement(By.id("logscouncilmen")).getText().contains("Councilmen log: MESSAGE TEST LOG councilmen"));
        //assertTrue(driver.findElement(By.id("logsOtherAuth")).getText().contains("Other Authorities log: MESSAGE TEST LOG otherAuthorities"));
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

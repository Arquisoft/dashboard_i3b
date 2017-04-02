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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class LogsTest {
    private WebDriver driver;
    private URI baseUrl;
    private boolean acceptNextAlert = true;

    @Autowired
    private MessageProducer mp;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = new URI("http://localhost:8090/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testLogs() throws Exception {
        driver.get(baseUrl.toString());
//        Thread.sleep(1000);
//        mp.send("councilStaff");
//        Thread.sleep(5000);
//        mp.send("councilmen");
//        Thread.sleep(5000);
//        mp.send("otherAuthorities");
//        Thread.sleep(5000);
//        mp.send("councilStaff");
//        Thread.sleep(5000);
//        mp.send("councilmen");
//        Thread.sleep(5000);
//        mp.send("otherAuthorities");
//        Thread.sleep(20000);
        assertTrue(driver.getPageSource().contains("Dashboard"));
//        assertTrue(driver.getPageSource().contains("CouncilStaff log: MESSAGE TEST LOG councilStaff"));
//        assertTrue(driver.getPageSource().contains("Councilmen log: MESSAGE TEST LOG councilmen"));
//        assertTrue(driver.getPageSource().contains("Other Authorities log: MESSAGE TEST LOG otherAuthorities"));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}

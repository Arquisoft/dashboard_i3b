package steps;

import asw.Application;
import asw.kafka.mockProducer.MessageProducer;
import cucumber.api.java.en.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by juanf on 01/04/2017.
 */
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class LogsSteps {

    private static FirefoxDriver driver;

    private static MessageProducer mp;

    public static void setUp() {
        driver = new FirefoxDriver();
        mp = new MessageProducer();
    }


    @Given("^the user navigates to \"([^\"]*)\"$")
    public void theUserNavigatesTo(String url) {
        driver.get(url);
    }


    @And("^clicks \"([^\"]*)\" link")
    public void clicksXXXLink(String urlText) {
        WebElement element = driver.findElementByLinkText(urlText);
        element.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @And("a message is produced with topic \"([^\"]*)\"$")
    public void aMessageIsProducedWithTopic(String topic) {
        mp.send(topic);
    }

    @When("the user waits (\\d+) seconds")
    public void theUserWaitsXSeconds(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("there is a log of \"([^\"]*)\" on the webpage")
    public void thereIsALogOfTypeOnTheWebpage(String logType) throws Throwable {
        String log = "MESSAGE TEST LOG " + logType;
        assertTrue(driver.getPageSource().contains(log));

    }

    @But("there is not a log of \"([^\"]*)\" on the webpage")
    public void thereIsNotALogOfTypeOnTheWebpage(String logType) throws Throwable {
        String log = "MESSAGE TEST LOG " + logType;
        assertFalse(driver.getPageSource().contains(log));
    }


    public static void tearDown() {
        driver.quit();
    }

}

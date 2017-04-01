package asw.hello;

import asw.Application;
import asw.kafka.mockProducer.MessageProducer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by juanf on 01/04/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LogsSteps {

    private static FirefoxDriver driver;

    private static MessageProducer mp;

    public static void setUp() {
        driver = new FirefoxDriver();
        mp = new MessageProducer();
    }

    @Given("^the user navigates to \"([^\"]*)\"$")
    public void the_user_navigates_to(String url) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.get(url);
    }

    @Given("^clicks \"([^\"]*)\" link$")
    public void clicks_link(String urlText) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        WebElement element = driver.findElementByLinkText(urlText);
        element.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("^a message is produced with topic \"([^\"]*)\"$")
    public void a_message_is_produced_with_topic(String topic) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        mp.send(topic);
    }

    @When("^the user waits (\\d+) seconds$")
    public void the_user_waits_seconds(int seconds) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^there is a log of \"([^\"]*)\" on the webpage$")
    public void there_is_a_log_of_on_the_webpage(String logType) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String log = "MESSAGE TEST LOG " + logType;
        assertTrue(driver.getPageSource().contains(log));
    }

    @Then("^there is not a log of \"([^\"]*)\" on the webpage$")
    public void there_is_not_a_log_of_on_the_webpage(String logType) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String log = "MESSAGE TEST LOG " + logType;
        assertFalse(driver.getPageSource().contains(log));
    }



    public static void tearDown() {
        driver.quit();
    }

}

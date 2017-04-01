package asw.steps;

import asw.Application;
import asw.kafka.mockProducer.MessageProducer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ContextConfiguration
@SpringBootTest(classes = Application.class)
public class LogsSteps {

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private MessageProducer mp;

    protected MockMvc mvc;
    protected MvcResult result;

    @Given("^the user navigates to /$")
    public void the_user_navigates_to() throws Throwable {
        Assert.notNull(context);
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
        result = mvc.perform(get("/")).andReturn();
    }

    @Given("^clicks \"([^\"]*)\" link$")
    public void clicks_link(String str) throws Throwable {
        Assert.notNull(context);
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
        result = mvc.perform(get("/" + str)).andReturn();
    }

    @Given("^a message is produced with topic \"([^\"]*)\"$")
    public void a_message_is_produced_with_topic(String topic) throws Throwable {
        mp.send(topic);
    }

    @When("^the user waits (\\d+) seconds$")
    public void the_user_waits_seconds(int seconds) throws Throwable {
        Thread.sleep(seconds * 1000);
    }

    @Then("^there is a log of \"([^\"]*)\" on the webpage$")
    public void there_is_a_log_of_on_the_webpage(String str) throws Throwable {
        assertThat(result.getResponse().getContentAsString(), containsString(str));
    }

    @Then("^there is not a log of \"([^\"]*)\" on the webpage$")
    public void there_is_not_a_log_of_on_the_webpage(String str) throws Throwable {
        assertThat(result.getResponse().getContentAsString(), not(containsString(str)));
    }
}
package hello;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.*;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
public class CucumberTest{
}
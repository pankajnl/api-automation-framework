package cucumber.options;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions"},
        monochrome = true
)

//AbstractTestNGCucumberTests – Runs each cucumber scenario found in the features as a separate test.
public class TestRunner extends AbstractTestNGCucumberTests {

}

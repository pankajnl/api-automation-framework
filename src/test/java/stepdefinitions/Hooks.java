package stepdefinitions;

import context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;

/* This class contains hooks that will run before and after each scenario.
The @Before annotation indicates that the method should be executed before the scenario, while the @After annotation indicates that the method should be executed after the scenario.
In this example, we are simply clearing the TestContext before each scenario to ensure that we start with a clean state, but in a real-world application, you might use these hooks to set up test data, initialize resources, or perform cleanup tasks after the tests have run*/
public class Hooks {

    private final TestContext context;
    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before()
    public void beforeScenario() {
        context.clear();
    }

    @After("@Clanup")
    public void afterScenario() {
        // Perform cleanup tasks after the scenario
    }

}

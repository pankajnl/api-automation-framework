package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;

/*This class contains hooks that will run before and after each scenario.
The @Before annotation indicates that the method should be executed before the scenario, while the @After annotation indicates that the method should be executed after the scenario.
In this example, we are simply printing messages to the console, but in a real-world application, you might use these hooks to set up test data, initialize resources, or perform cleanup tasks after the tests have run.*/
public class Hooks {
    @Before("@DeletePost")
    public void beforeScenario() {
        //System.out.println("This will run before the scenario");
    }

    @After("@Clanup")
    public void afterScenario() {
        //System.out.println("This will run after the scenario");
    }

}

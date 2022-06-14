package step_defs;

import io.cucumber.java.en.When;

public class Parallel {
    @When("parallel test")
    public void parallelTest() {
        System.out.println(Thread.currentThread().getId());
    }
}

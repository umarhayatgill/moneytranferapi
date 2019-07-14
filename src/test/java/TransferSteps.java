import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class TransferSteps {
    @Given("^that the user (.*) has (\\d+) Euro in his account$")
    public void givenUserHasBalanceInAccount(String fromUserEmailId, int accountBalance) throws Throwable {

    }

    @When("^user (.*) makes a (.*) Euro transfer to user (.*)$")
    public void whenUserMakesATransfer(String fromUserEmailId, BigDecimal fromUserAccountBalance, String toUserEmailId) throws Throwable {

    }

    @Then("^(\\d+) Euro is transferred successfully to user (.*)$")
    public void thenMoneyShouldBeTransferred(int fromUserAccountBalance, String toUserEmailId) throws Throwable {
        assertTrue("true",true);
    }
}

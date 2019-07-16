import static java.lang.Thread.sleep;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Map;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import spark.Spark;

public class TransferSteps {

    private TestResponse res;

    private String fromUserAccountId;
    private BigDecimal balanceToTransfer;
    private String toUserAccountId;

    @Before
    public void init() throws InterruptedException {
        MoneyTransferAPI.main(null);
        sleep(3000);
    }

    @After
    public static void afterClass() {
        Spark.stop();
    }

    @Given("^that the (.*) has to transfer (.*) Euro to (.*)")
    public void givenUserHasBalanceInAccount(String fromUserAccountId, BigDecimal balanceToTransfer, String toUserAccountId) throws Throwable {
        this.fromUserAccountId = fromUserAccountId;
        this.balanceToTransfer = balanceToTransfer;
        this.toUserAccountId = toUserAccountId;
    }

    @When("^the transfer is requested$")
    public void whenUserMakesATransfer() throws Throwable {
        res = Helper.request("POST", "/moneytransfer?fromAccountId="+this.fromUserAccountId+
                "&toAccountId="+this.toUserAccountId+"&amountToTransfer="+this.balanceToTransfer);
    }

    @Then("^amount is deducted and from sender's account$")
    public void andAmountShouldBeDeductedFromSender() throws Throwable {
        res = Helper.request("GET", "/accounts/"+this.fromUserAccountId);
        Map<String, String> json = res.json();
        assertEquals(200, res.status);
        assertEquals(0.0,json.get("balance"));
    }

    @And("^receiver receives the amount$")
    public void thenMoneyShouldBeTransferredToReceiver() throws Throwable {
        res = Helper.request("GET", "/accounts/"+this.toUserAccountId);
        Map<String, String> json = res.json();
        assertEquals(220.0, json.get("balance"));
        assertEquals(200, res.status);
    }



}

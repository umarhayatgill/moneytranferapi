import controller.AccountController;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Account;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.AccountService;
import spark.Spark;

import java.math.BigDecimal;
import java.util.Currency;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class TransferSteps {

    private TestResponse res;

    private String fromUserAccountId;
    private BigDecimal balanceToTransfer;
    private String toUserAccountId;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Before
    public void init() throws InterruptedException {
        MockitoAnnotations.initMocks(this);
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



        Account accountStub = new Account();
        accountStub.setAccountID(fromUserAccountId);
        accountStub.setBalance(BigDecimal.valueOf(12));
        accountStub.setCurrency(Currency.getInstance("EUR"));
        when(accountService.getAccount(fromUserAccountId)).thenReturn(accountStub);
        accountStub = new Account();
        accountStub.setAccountID(toUserAccountId);
        accountStub.setBalance(BigDecimal.valueOf(10));
        accountStub.setCurrency(Currency.getInstance("EUR"));
        when(accountService.getAccount(toUserAccountId)).thenReturn(accountStub);

    }

    @When("^the transfer is requested$")
    public void whenUserMakesATransfer() throws Throwable {
        res = Helper.request("POST", "/moneytransfer?fromAccountId="+this.fromUserAccountId+
                "&toAccountId="+this.toUserAccountId+"&amountToTransfer="+this.balanceToTransfer);
    }

    @Then("^(.*) receives (.*) Euro in his account$")
    public void thenMoneyShouldBeTransferred(String toUserAccountId, BigDecimal balanceToTransfer) throws Throwable {
        assertEquals(200, res.status);
    }
}

import com.google.gson.JsonElement;
import helper.Util;
import helper.TestResponse;
import org.junit.*;
import spark.Spark;

import java.math.BigDecimal;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;



public class AccountControllerIntegrationTest {

    @Before
    public void beforeClass() throws InterruptedException {
        MoneyTransferAPI.main(null);
        sleep(3000);
    }


    @Test
    public void getListOfAllAccounts() {
        TestResponse res = Util.request("GET", "/accounts");
        JsonElement json = res.jsonElement();
        Assert.assertEquals(200, res.status);
        assertEquals(2, json.getAsJsonObject().size());
    }

    @Test
    public void aNewAccountShouldBeCreated() {
        TestResponse res = Util.request("PUT", "/account/3?userId=1&balance=200&currencyCode=EUR");
        Assert.assertEquals(200, res.status);
    }

    @Test
    public void amountCanBeDepositedInAnAccount() {
        TestResponse res = Util.request("PUT", "/account/2/deposit/20");
        Assert.assertEquals(200, res.status);
    }

    @Test
    public void amountCanBeWithdrawnFromAnAccount() {
        TestResponse res = Util.request("PUT", "/account/2/withdraw/20");
        Assert.assertEquals(200, res.status);
    }

    @Test
    public void balanceForAnAccountShouldBeCorrect() {
        TestResponse res = Util.request("GET", "/account/3/balance");
        JsonElement json = res.jsonElement();
        Assert.assertEquals(200, res.status);
        assertEquals(BigDecimal.valueOf(200), json.getAsJsonObject().get("data").getAsBigDecimal());
    }


    @Test
    public void anExistingAccountShouldBeDeleted() {
        TestResponse res = Util.request("DELETE", "/account/3");
        JsonElement json = res.jsonElement();
        Assert.assertEquals(200, res.status);
    }
}

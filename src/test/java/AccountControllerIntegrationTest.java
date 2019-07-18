import static java.lang.Thread.sleep;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonElement;

import spark.Spark;

public class AccountControllerIntegrationTest {
    @BeforeClass
    public static void beforeClass() throws InterruptedException {
        MoneyTransferAPI.main(null);
        sleep(3000);
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void getListOfAllAccounts() {
        TestResponse res = Helper.request("GET", "/accounts");
        JsonElement json = res.jsonElement();
        assertEquals(200, res.status);
        assertEquals(2, json.getAsJsonObject().size());
    }

    @Test
    public void aNewAccountShouldBeCreated() {
        TestResponse res = Helper.request("PUT", "/account/3?userId=1&balance=200&currencyCode=EUR");
        assertEquals(200, res.status);
    }

    @Test
    public void amountCanBeDepositedInAnAccount() {
        TestResponse res = Helper.request("PUT", "/account/2/deposit/20");
        assertEquals(200, res.status);
    }

    @Test
    public void amountCanBeWithdrawnFromAnAccount() {
        TestResponse res = Helper.request("PUT", "/account/2/withdraw/20");
        assertEquals(200, res.status);
    }

    @Test
    public void balanceForAnAccountShouldBeCorrect() {
        TestResponse res = Helper.request("GET", "/account/3/balance");
        JsonElement json = res.jsonElement();
        assertEquals(200, res.status);
        assertEquals(BigDecimal.valueOf(200), json.getAsJsonObject().get("data").getAsBigDecimal());
    }


    @Test
    public void anExistingAccountShouldBeDeleted() {
        TestResponse res = Helper.request("DELETE", "/account/3");
        JsonElement json = res.jsonElement();
        assertEquals(200, res.status);
    }
}

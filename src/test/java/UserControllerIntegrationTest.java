import static java.lang.Thread.sleep;

import com.google.gson.JsonElement;
import helper.TestResponse;
import helper.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserControllerIntegrationTest {
    @Before
    public  void beforeClass() throws InterruptedException {
        MoneyTransferAPI.main(null);
        sleep(3000);
    }
    @Test
    public void getListOfAllUsers() {
        TestResponse res = Util.request("GET", "/users");
        JsonElement json = res.jsonElement();
        Assert.assertEquals(200, res.status);
        assertEquals(2, json.getAsJsonObject().size());
    }

    @Test
    public void aNewUserShouldBeCreated() {
        TestResponse res = Util.request("POST", "/user/3?firstName=umar&lastName=hayat&email=umarhayat@foobar.com");
        JsonElement json = res.jsonElement();
        Assert.assertEquals(200, res.status);
    }

    @Test
    public void anExistingUserShouldBeUpdated() {
        TestResponse res = Util.request("PUT", "/user/3?firstName=umar&lastName=hayatgill&email=umarhayatgill@foobar.com");
        JsonElement json = res.jsonElement();
        Assert.assertEquals(200, res.status);
        assertEquals("hayatgill", json.getAsJsonObject().get("data").getAsJsonObject().get("lastName").getAsString());
        assertEquals("umarhayatgill@foobar.com", json.getAsJsonObject().get("data").getAsJsonObject().get("email").getAsString());
    }

    @Test
    public void anExistingUserShouldBeDeleted() {
        TestResponse res = Util.request("DELETE", "/user/3");
        JsonElement json = res.jsonElement();
        Assert.assertEquals(200, res.status);
    }
}

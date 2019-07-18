import static java.lang.Thread.sleep;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonElement;

import spark.Spark;

public class UserControllerIntegrationTest {
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
    public void getListOfAllUsers() {
        TestResponse res = Helper.request("GET", "/users");
        JsonElement json = res.jsonElement();
        assertEquals(200, res.status);
        assertEquals(2, json.getAsJsonObject().size());
    }

    @Test
    public void aNewUserShouldBeCreated() {
        TestResponse res = Helper.request("POST", "/user/3?firstName=umar&lastName=hayat&email=umarhayat@foobar.com");
        JsonElement json = res.jsonElement();
        assertEquals(200, res.status);
    }

    @Test
    public void anExistingUserShouldBeUpdated() {
        TestResponse res = Helper.request("PUT", "/user/3?firstName=umar&lastName=hayatgill&email=umarhayatgill@foobar.com");
        JsonElement json = res.jsonElement();
        assertEquals(200, res.status);
        assertEquals("hayatgill", json.getAsJsonObject().get("data").getAsJsonObject().get("lastName").getAsString());
        assertEquals("umarhayatgill@foobar.com", json.getAsJsonObject().get("data").getAsJsonObject().get("email").getAsString());
    }

    @Test
    public void anExistingUserShouldBeDeleted() {
        TestResponse res = Helper.request("DELETE", "/user/3");
        JsonElement json = res.jsonElement();
        assertEquals(200, res.status);
    }
}

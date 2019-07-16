import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import java.util.Map;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

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
    public void aNewUserShouldBeCreated() {
        TestResponse res = Helper.request("POST", "/users?name=umar&email=umarhayat@foobar.com");
        Map<String, String> json = res.json();
        assertEquals(200, res.status);
        assertEquals("umar", json.get("name"));
        assertEquals("umarhayat@foobar.com", json.get("email"));
    }

    @Test
    public void anExistingUserShouldBeUpdated() {
        TestResponse res = Helper.request("PUT", "/users?name=john&email=umarhayat@foobar.com");
        Map<String, String> json = res.json();
        assertEquals(200, res.status);
        assertEquals("john", json.get("name"));
        assertEquals("umarhayat@foobar.com", json.get("email"));
    }


}

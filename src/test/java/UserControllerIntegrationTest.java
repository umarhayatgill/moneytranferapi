import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

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
        TestResponse res = request("POST", "/users?name=umar&email=umarhayat@foobar.com");
        Map<String, String> json = res.json();
        assertEquals(200, res.status);
        assertEquals("umar", json.get("name"));
        assertEquals("umarhayat@foobar.com", json.get("email"));
    }

    @Test
    public void anExistingUserShouldBeUpdated() {
        TestResponse res = request("PUT", "/users?name=john&email=umarhayat@foobar.com");
        Map<String, String> json = res.json();
        assertEquals(200, res.status);
        assertEquals("john", json.get("name"));
        assertEquals("umarhayat@foobar.com", json.get("email"));
    }

    private TestResponse request(String method, String path) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:4567" + path);
            connection  = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        } finally {
            connection.disconnect();
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }
}

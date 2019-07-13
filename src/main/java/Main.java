import controller.UserController;
import service.UserServiceImpl;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        after((req, res) -> {
            res.type("application/json");
        });
        new UserController(new UserServiceImpl());
    }
}

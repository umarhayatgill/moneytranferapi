package controller;

import helper.ResponseError;
import model.User;
import service.UserService;

import static spark.Spark.*;
import static helper.JsonUtil.json;

public class UserController {

    public UserController(final UserService userService) {
        get("/users", (req, res) -> userService.getAllUsers(), json());

        get("/users/:id", (req, res) -> {
            String id = req.params(":id");
            try {
                User user = userService.getUser(id);
                return user;
            }catch (Exception ex) {
                res.status(400);
                return new ResponseError("No user with id %s found", id);
            }
        }, json());

    }
}

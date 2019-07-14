package controller;

import exception.AlreadyExistException;
import exception.NotFoundException;
import helper.ResponseError;
import model.User;
import service.UserService;

import static spark.Spark.*;
import static helper.JsonUtil.json;

public class UserController {

    public UserController(final UserService userService) {
        get("/users", (req, res) -> userService.getAllUsers(), json());

        post("/users", (req, res) -> {
                    try {
                        User user = userService.createUser(req.queryParams("name"), req.queryParams("email"));
                        return user;
                    }catch (AlreadyExistException ex) {
                        res.status(400);
                        return new ResponseError("User with this email already exists");
                    }
                } , json());


        put("/users", (req, res) -> {
            try {
                User user = userService.updateUser(req.queryParams("name"), req.queryParams("email"));
                return user;
            }catch (NotFoundException ex) {
                res.status(400);
                return new ResponseError("User with this email already exists");
            }
        } , json());

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

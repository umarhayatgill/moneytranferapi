package controller;

import exception.AlreadyExistException;
import exception.NotFoundException;
import helper.ResponseError;
import helper.ResponseSuccess;
import model.User;
import service.UserService;

import static spark.Spark.*;
import static helper.JsonUtil.json;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class UserController {
   public UserController(final UserService userService) {
        //get list of all users
        get("/users", (req, res) -> userService.getAllUsers(), json()); //get list of all users

       //get user with given id
       get("/users/:id", (req, res) -> {
           String id = req.params(":id");
           try {
               User user = userService.getUser(id);
               return user;
           }catch (NotFoundException ex) {
               res.status(400);
               return new ResponseError("No user with id %s found", id);
           }
       }, json());

        //add a user
        post("/users/:id", (req, res) -> {
            String userId = req.params(":id");
            try {
                User user = new User.Builder().withId(userId).withFirstName(req.queryParams("firstName"))
                        .withLastName(req.queryParams("lastName")).withEmail(req.queryParams("email")).build();
                userService.createUser(user);
                res.status(200);
                return new ResponseError("User with %s id has been created", userId);
            }catch (AlreadyExistException ex) {
                res.status(400);
                return new ResponseError("User with id %s already exists", userId);
            }
        } , json());

        //edit a particular user
        put("/users/:id", (req, res) -> {
            String userId = req.params(":id");
            try {
                User user = new User.Builder().withId(userId).withFirstName(req.queryParams("firstName"))
                        .withLastName(req.queryParams("lastName")).withEmail(req.queryParams("email")).build();
                return userService.updateUser(user);
            }catch (NotFoundException ex) {
                res.status(400);
                return new ResponseError("User with %s id does not exist", userId);
            }
        } , json());

        //check whether a user exists with given id
       options("/users/:id", (req, res) -> {
           String userId = req.params(":id");
           boolean userExist = userService.userExist(userId);
           res.status(200);
           return userExist;
       } , json());

       //delete a particular user
       delete("/users/:id", (req, res) -> {
           String userId = req.params(":id");
           try {
               userService.deleteUser(userId);
               res.status(200);
               return new ResponseSuccess("User with %s id has been deleted", userId);
           }catch (Exception ex) {
               return new ResponseError("User with %s id does not exist", userId);
           }
       } , json());

    }
}

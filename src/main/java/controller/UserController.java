package controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;

import model.User;
import response.StandardResponse;
import response.StatusResponse;
import service.UserService;

public class UserController {
   public UserController(final UserService userService) {
        //get list of all users
       get("/users", (req, res) -> {
           String id = req.params(":id");
           return new Gson().toJson(
                   new StandardResponse(StatusResponse.SUCCESS,new Gson()
                           .toJsonTree(userService.getAllUsers())));
       });

       //get user with given id
       get("/user/:id", (req, res) -> {
           String id = req.params(":id");
           return new Gson().toJson(
                   new StandardResponse(StatusResponse.SUCCESS,new Gson()
                           .toJsonTree(userService.getUser(id))));
       });

        //create a new user
        post("/user/:id", (req, res) -> {
            String userId = req.params(":id");
            User user = new User.Builder().withId(userId).withFirstName(req.queryParams("firstName"))
                    .withLastName(req.queryParams("lastName")).withEmail(req.queryParams("email")).build();
            userService.createUser(user);
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,"New user has been created"));
        });

        //edit a particular user
        put("/user/:id", (req, res) -> {
            String userId = req.params(":id");
            User user = new User.Builder().withId(userId).withFirstName(req.queryParams("firstName"))
                    .withLastName(req.queryParams("lastName")).withEmail(req.queryParams("email")).build();
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,new Gson()
                            .toJsonTree(userService.updateUser(user))));
        });

        //check whether a user exists with given id
       options("/user/:id", (req, res) -> {
           String userId = req.params(":id");
           return new Gson().toJson(
                   new StandardResponse(StatusResponse.SUCCESS,new Gson()
                           .toJsonTree(userService.userExist(userId))));
       });

       //delete a particular user
       delete("/user/:id", (req, res) -> {
           String userId = req.params(":id");
           userService.deleteUser(userId);
           return new Gson().toJson(
                   new StandardResponse(StatusResponse.SUCCESS,"User with %s id has been deleted", userId));
       });

    }
}

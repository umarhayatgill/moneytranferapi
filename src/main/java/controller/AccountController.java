package controller;

import helper.ResponseError;
import model.Account;
import model.User;
import service.AccountService;
import service.UserService;

import static helper.JsonUtil.json;
import static spark.Spark.get;
import static spark.Spark.post;

public class AccountController {
    public AccountController(final AccountService accountService) {
        get("/accounts", (req, res) -> accountService.getAllAccounts(), json());

        get("/accounts/:id", (req, res) -> {
            String id = req.params(":id");
            try {
                Account account = accountService.getAccount(id);
                return account;
            }catch (Exception ex) {
                res.status(400);
                return new ResponseError("No account with id %s found", id);
            }
        }, json());

        post("/moneytransfer", (req, res) -> {
            String fromAccountId = req.params("fromAccountId");
            String toAccountId = req.params("toAccountId");
            try {
                Account fromAccount = accountService.getAccount(fromAccountId);
                Account toAccount = accountService.getAccount(toAccountId);
                accountService
            }catch (Exception ex) {
                res.status(400);
                return new ResponseError("No account with id %s found", id);
            }
        }, json());

    }
}

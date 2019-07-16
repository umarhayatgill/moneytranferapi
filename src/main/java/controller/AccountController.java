package controller;

import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import helper.ResponseError;
import helper.ResponseSuccess;
import model.Account;
import service.AccountService;

import static helper.JsonUtil.json;
import static spark.Spark.*;

public class AccountController {
    public AccountController(final AccountService accountService) {
        get("/accounts", (req, res) -> accountService.getAllAccounts(), json());

        get("/account/:id", (req, res) -> {
            String id = req.params(":id");
            try {
                Account account = accountService.getAccount(id);
                return account;
            }catch (Exception ex) {
                res.status(400);
                return new ResponseError("No account with id %s found", id);
            }
        }, json());

        get("/account/:id/balance", (req, res) -> {
            String id = req.params(":id");
            try {
                return accountService.getAccountBalance(id);
            }catch (Exception ex) {
                res.status(400);
                return new ResponseError("No account with id %s found", id);
            }
        }, json());

        //delete a particular user
        delete("/user/:id", (req, res) -> {
            String userId = req.params(":id");
            try {
                accountService.deleteAccount(userId);
                res.status(200);
                return new ResponseSuccess("Account with %s id has been deleted", userId);
            }catch (Exception ex) {
                res.status(400);
                return new ResponseError("Account with %s id does not exist", userId);
            }
        } , json());

        post("/moneytransfer", (req, res) ->
        {
            try {
                String fromAccountId = req.queryParams("fromAccountId");
                String toAccountId = req.queryParams("toAccountId");
                String amountToTransfer = req.queryParams("amountToTransfer");
                accountService.makePayment(fromAccountId, toAccountId, amountToTransfer);
                res.status(200);
                return new ResponseSuccess("Money has been transferred successfully");
            }catch (NotSufficientBalanceException exception){
                res.status(400);
                return new ResponseError("Your account does not have sufficient balance");
            }catch (NotFoundException exception){
                res.status(400);
                return new ResponseError("Account details are not valid");
            }
        }, json());



    }
}

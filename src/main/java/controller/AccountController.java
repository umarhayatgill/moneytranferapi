package controller;

import exception.NotSufficientBalanceException;
import helper.ResponseError;
import helper.ResponseSuccess;
import model.Account;
import service.AccountService;

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
            }
        }, json());



    }
}

package controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.math.BigDecimal;
import java.util.Currency;

import com.google.gson.Gson;

import dependencyinjection.daggercomponents.AccountServiceComponent;
import dependencyinjection.daggercomponents.DaggerAccountServiceComponent;
import dependencyinjection.daggercomponents.UserServiceComponent;
import model.Account;
import response.StandardResponse;
import response.StatusResponse;
import service.AccountService;
import service.UserService;

public class AccountController {
    AccountServiceComponent accountServiceComponent = DaggerAccountServiceComponent.create();
    AccountService accountService = accountServiceComponent.buildUserService();
    public void registerAccountApiRoutes() {

        //get all accounts
        get("/account/all", (req, res) -> {
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,new Gson()
                            .toJsonTree(accountService.getAllAccounts())));
        });

        //get account details by account id
        get("/account/:id", (req, res) -> {
            String id = req.params(":id");
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,new Gson()
                            .toJsonTree(accountService.getAccount(id))));
        });

        //get balance of account
        get("/account/:id/balance", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,new Gson()
                            .toJsonTree(accountService.getAccountBalance(id))));
        });

        //create a new account
        put("/account/:id", (req, res) -> {
            String accountId = req.params(":id");
            Account account = Account.builder().withAccountID(accountId).withUserId(req.queryParams("userId"))
                    .withBalance(BigDecimal.valueOf(Long.valueOf(req.queryParams("balance")))).
                            withCurrency(Currency.getInstance(req.queryParams("currencyCode"))).build();
            accountService.createAccount(account);
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,"New account has been created"));
        });

        //delete a particular account
        delete("/account/:id", (req, res) -> {
            String accountId = req.params(":id");
            accountService.deleteAccount(accountId);
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,
                            "Account with %s id has been deleted", accountId));
        });

        //withdraw money from account
        put("/account/:id/withdraw/:amount", (req, res) -> {
            String accountId = req.params(":id");
            BigDecimal amountToWithdraw = BigDecimal.valueOf(Long.valueOf(req.params(":amount")));
            accountService.withdrawMoney(accountId, amountToWithdraw);
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,
                            "Amount has been withdrawn from account with id %s", accountId));
        });

        //deposit money to account
        put("/account/:id/deposit/:amount", (req, res) -> {
            String accountId = req.params(":id");
            BigDecimal amountToDeposit = BigDecimal.valueOf(Long.valueOf(req.params(":amount")));
            accountService.depositMoney(accountId, amountToDeposit);
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,
                            "Amount has been deposited to account with id %s", accountId));
        });


        post("/moneytransfer", (req, res) ->
        {
            String fromAccountId = req.queryParams("fromAccountId");
            String toAccountId = req.queryParams("toAccountId");
            String amountToTransfer = req.queryParams("amountToTransfer");
            accountService.makePayment(fromAccountId, toAccountId, amountToTransfer);
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,
                            "Money has been transferred successfully"));
        });



    }
}

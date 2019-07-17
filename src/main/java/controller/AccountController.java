package controller;

import exception.AlreadyExistException;
import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import helper.ResponseError;
import helper.ResponseSuccess;
import model.Account;
import response.StandardResponse;
import response.StatusResponse;
import service.AccountService;

import static helper.JsonUtil.json;
import static spark.Spark.*;

import java.math.BigDecimal;
import java.util.Currency;

import com.google.gson.Gson;

public class AccountController {
    public AccountController(final AccountService accountService) {
        get("/accounts", (req, res) -> accountService.getAllAccounts(), json());

        get("/account/:id", (req, res) -> {
            String id = req.params(":id");
            try {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,new Gson()
                                .toJsonTree(accountService.getAccount(id))));
            }catch (NotFoundException ex) {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,"No account with id %s found", id));
            }
        });

        get("/account/:id/balance", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");
            try {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,new Gson()
                                .toJsonTree(accountService.getAccountBalance(id))));
            }catch (NotFoundException ex) {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,"No account with id %s found", id));
            }
        });

        //add a new account
        put("/account/:id", (req, res) -> {
            String accountId = req.params(":id");
            try {
                Account account = Account.builder().withAccountID(accountId).withUserId(req.queryParams("userId"))
                        .withBalance(BigDecimal.valueOf(Long.valueOf(req.queryParams("balance")))).
                                withCurrency(Currency.getInstance(req.queryParams("currencyCode"))).build();
                accountService.createAccount(account);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,new Gson()
                                .toJsonTree(accountService.updateAccount(account))));
            }catch (NotFoundException ex) {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,"Account with %s id does not exist", accountId));
            }
        });

        //update an existing account
        put("/account/:id", (req, res) -> {
            String accountId = req.params(":id");
            try {
                Account account = Account.builder().withAccountID(accountId).withUserId(req.queryParams("userId"))
                        .withBalance(BigDecimal.valueOf(Long.valueOf(req.queryParams("balance")))).
                                withCurrency(Currency.getInstance(req.queryParams("currencyCode"))).build();
                accountService.updateAccount(account);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,"Account with %s id has been created", accountId));
            }catch (NotFoundException ex) {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,"Account with %s id already exist", accountId));
            }
        });

        //delete a particular account
        delete("/account/:id", (req, res) -> {
            String accountId = req.params(":id");
            try {
                accountService.deleteAccount(accountId);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,
                                "Account with %s id has been deleted", accountId));
            }catch (NotFoundException ex) {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,
                                "Account with %s id does not exist", accountId));
            }
        });

        post("/moneytransfer", (req, res) ->
        {
            try {
                String fromAccountId = req.queryParams("fromAccountId");
                String toAccountId = req.queryParams("toAccountId");
                String amountToTransfer = req.queryParams("amountToTransfer");
                accountService.makePayment(fromAccountId, toAccountId, amountToTransfer);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,
                                "Money has been transferred successfully"));
            }catch (NotSufficientBalanceException exception){
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,
                                "Your account does not have sufficient balance"));
            }catch (NotFoundException exception){
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,
                                "Account ids are not valid"));
            }
        });



    }
}

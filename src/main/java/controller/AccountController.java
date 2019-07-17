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

        //create a new account
        put("/account/:id", (req, res) -> {
            String accountId = req.params(":id");
            try {
                Account account = Account.builder().withAccountID(accountId).withUserId(req.queryParams("userId"))
                        .withBalance(BigDecimal.valueOf(Long.valueOf(req.queryParams("balance")))).
                                withCurrency(Currency.getInstance(req.queryParams("currencyCode"))).build();
                accountService.createAccount(account);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,"New account has been created"));
            }catch (AlreadyExistException ex) {
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

        //withdraw money from account
        put("/account/:id/withdraw/:amount", (req, res) -> {
            String accountId = req.params(":id");
            BigDecimal amountToWithdraw = BigDecimal.valueOf(Long.valueOf(req.params(":amount")));
            try {
                accountService.withdrawMoney(accountId, amountToWithdraw);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,
                                "Amount has been withdrawn from account with id %s", accountId));
            }catch (NotFoundException ex) {
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR,
                                "Account with %s id does not exist", accountId));
            }
        });

        //deposit money to account
        put("/account/:id/deposit/:amount", (req, res) -> {
            String accountId = req.params(":id");
            BigDecimal amountToDeposit = BigDecimal.valueOf(Long.valueOf(req.params(":amount")));
            try {
                accountService.depositMoney(accountId, amountToDeposit);
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS,
                                "Amount has been deposited to account with id %s", accountId));
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

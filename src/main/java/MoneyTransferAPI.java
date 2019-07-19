import static spark.Spark.before;
import static spark.Spark.exception;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import controller.AccountController;
import controller.UserController;
import exception.AlreadyExistException;
import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import response.StandardResponse;
import response.StatusResponse;
import service.AccountServiceImpl;

public class MoneyTransferAPI {
    public static void main(String[] args) {
        before((req, res) -> {
            res.type("application/json");
        });
        exception(NotFoundException.class, (e, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, e.getMessage())));
        });
        exception(AlreadyExistException.class, (e, req, res) -> {
            res.status(HttpStatus.CONFLICT_409);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, e.getMessage())));
        });
        exception(NotSufficientBalanceException.class, (e, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, e.getMessage())));
        });
        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, e.getMessage())));
        });
        exception(Exception.class, (e, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, e.getMessage())));
        });
        new UserController();
        new AccountController(new AccountServiceImpl());
    }
}

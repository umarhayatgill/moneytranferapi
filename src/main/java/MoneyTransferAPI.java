import com.google.gson.Gson;
import controller.AccountController;
import controller.UserController;
import exception.AlreadyExistException;
import exception.NotFoundException;
import exception.NotSufficientBalanceException;
import exception.SameAccountException;
import org.eclipse.jetty.http.HttpStatus;
import response.StandardResponse;
import response.StatusResponse;

import static spark.Spark.before;
import static spark.Spark.exception;

public class MoneyTransferAPI {
    public static void main(String[] args) {
        startSparkApplication();
    }

    private static void startSparkApplication() {
        exceptionHandling();
        setResponseType();
        UserController userController = new UserController();
        userController.registerUserApiRoutes();
        AccountController accountController = new AccountController();
        accountController.registerAccountApiRoutes();
    }

    private static void setResponseType() {
        before((req, res) -> {
            res.type("application/json");
        });
    }

    private static void exceptionHandling() {
        exception(NotFoundException.class, (e, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, "Not found")));
        });
        exception(AlreadyExistException.class, (e, req, res) -> {
            res.status(HttpStatus.CONFLICT_409);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, "Already exists")));
        });
        exception(NotSufficientBalanceException.class, (e, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, "You do not have sufficient balance to transfer")));
        });
        exception(SameAccountException.class, (e, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, "Ammount can not be transferred between same accounts")));
        });
        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, "Illegal arguments provided in request")));
        });
        exception(NullPointerException.class, (e, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, "Insufficient arguments provided")));
        });
        exception(Exception.class, (e, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(new Gson().toJson(
                    new StandardResponse(StatusResponse.ERROR, "The system did not respond properly")));
        });
    }
}

#### Money transfer Rest API
Java RESTful API for money transfer between user accounts

#### How to Run

mvn exec:java

#### Technologies

* Spark Java (Light weight framewrok for microservices and comes embedded with Jetty container)
* Cucumber (for acceptance testing of features)
* Mockito (for mocking database calls in unit testing)
* Dagger 2 for dependency injection (It is very light dependency 
injection framework from google like Guice)

Application starts a jetty server on localhost port 
4567.In memory hashmap based data structure initialized
with some sample user and account data to play around with.

Few example calls are given below.
* localhost:4567/user/1
* localhost:4567/user/2
* localhost:4567/account/1
* localhost:4567/account/2

####Testing
TDD and BDD practice has been followed in developing the API in following
areas
* Unit Testing
* Integration Testing
* Functional Testing 
* Acceptance Testing

Although, acceptance testing overlaps with the functional testing
in a sense that in acceptance tests , features are tested as in functional
testing but in acceptance testing, the point is to write tests in such a way which can be even
read by product as they are defined in English and are converted 
into domain language. For example, it can be defined like following

###### Feature: Money Transfer
    Scenario: Successful money transfer
        Given that the 1 has to transfer 200 Euro to 2
        When the transfer is requested
        Then amount is deducted and from sender's account
        And receiver receives the amount


HTTP METHOD | PATH | USAGE
--- | --- | ---
GET| /user/all | Get all users
GET| /user/:id | Get user by its id
PUT| /user/:id | Create a new user
POST| /user/:id | Update an existing user
DELETE| /user/:id | Delete an exisiting user
GET| /account/all | Get all accounts
GET| /account/:id | Get account details by id
GET| /account/:id/balance | Get account balance by accountId
PUT| /account/:id | Create a new account
DELETE| /account/:id | Remove account by accountId
PUT| /account/:id/withdraw/:amount | Withdraw money from account
PUT| /account/:id/deposit/:amount | Deposit money from account
POST| /moneytransfer | Perform transaction between 2 user accounts

#### Https Status
* 200 OK: The request has succeeded
* 400 Bad Request: The request could not be understood by the server
* 404 Not Found: The requested resource cannot be found
* 500 Internal Server Error: The server encountered an unexpected condition 


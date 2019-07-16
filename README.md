#### Money transfer Rest API
Java RESTful API for money transfer between user accounts

#### How to Run

mvn exec:java

#### Technologies

* Spark Java (Jetty container embedded)
* Cucumber (for functional/acceptance testing)
* Mockito (for mocking database calls in unit testing)

Application starts a jetty server on localhost port 
4567.In memory hashmap based data structure initialized
with some sample user and account data to play around with.

Few example calls are given below.
* localhost:4567/users/1
* localhost:4567/users/2
* localhost:4567/accounts/1
* localhost:4567/accounts/2

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


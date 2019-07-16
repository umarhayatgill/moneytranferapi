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
GET| /users | Get all users
GET| /users/:id | Get user by its id
PUT| /users/:id | Create a new user
POST| /users/:id | Update an existing user
DELETE| /users/:id | Delete an exisiting user
GET| /accounts | Get all accounts
GET| /account/{accountId}/balance | Get account balance by accountId
PUT| /account/create | Create a new account
DELETE| /account/{accountId} | Remove account by accountId
PUT| /account/{accountId}/withdraw/{amount} | Withdraw money from account
PUT| /account/{accountId}/deposit/{amount} | Deposit money from account
POST| /transaction | Perform transaction between 2 user accounts

#### Https Status
* 200 OK: The request has succeeded
* 400 Bad Request: The request could not be understood by the server
* 404 Not Found: The requested resource cannot be found
* 500 Internal Server Error: The server encountered an unexpected condition 


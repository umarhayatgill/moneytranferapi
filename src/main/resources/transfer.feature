Feature: Money Transfer
    Scenario: Successful money transfer
        Given that the user Vinod has 150 Euro in his account
        When user "Vinod" makes a 100 Euro transfer to user "Shinod"
        Then 100 Euro is transferred successfully to user Shinod
Feature: Money Transfer
    Scenario: Successful money transfer
        Given that the 123 has to transfer 200 Euro to 234
        When the transfer is requested
        Then amount is deducted and from sender's account
        And receiver receives the amount
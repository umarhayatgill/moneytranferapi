Feature: Money Transfer
    Scenario: Successful money transfer
        Given that the Aqib has to transfer 150 Euro to Umar
        When the transfer is requested
        Then Umar receives 150 Euro in his account
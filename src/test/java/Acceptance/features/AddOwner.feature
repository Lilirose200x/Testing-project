Feature: Add Owner
As a user of the PetClinic Application
I would like to add a new Owner so that the Owner can book appointments for their Pets. 

  @ignore
  Scenario: Add a new Owner
    Given the following Owner information
      | firstName | lastName | id | telephone | address             | city     |
      | Saagar    | Arya     | 69 | 123456789 | heehee hoo hoo blvd | Montreal |
    When the User asks the System to add the Owner
    Then the System adds the Owner to the database

  Scenario: Add a new Owner with no first name
    Given the following Owner information
      | firstName | lastName | id | telephone | address             | city     |
      |           | Arya     | 69 | 123456789 | heehee hoo hoo blvd | Montreal |
    When the User asks the System to add the Owner
    Then the new Owner is not added
    And a First Name Missing error message is displayed to the User

  Scenario: Add a new owner with no last name
    Given the following Owner information
      | firstName | lastName | id | telephone | address             | city     |
      | Saagar    |          | 69 | 123456789 | heehee hoo hoo blvd | Montreal |
    When the User asks the System to add the Owner
    Then the new Owner is not added
    And a Last Name Missing error message is displayed to the User

  Scenario: Add a new owner with no telephone
    Given the following Owner information
      | firstName | lastName | id | telephone | address             | city     |
      | Saagar    | Arya     | 69 |           | heehee hoo hoo blvd | Montreal |
    When the User asks the System to add the Owner
    Then the new Owner is not added
    And a Telephone Missing error message is displayed to the User

  Scenario: Add a new owner with no address
    Given the following Owner information
      | firstName | lastName | id | telephone | address | city     |
      | Saagar    | Arya     | 69 | 123456789 |         | Montreal |
    When the User asks the System to add the Owner
    Then the new Owner is not added
    And an Address Missing error message is displayed to the User

  Scenario: Add a new owner with no city
    Given the following Owner information
      | firstName | lastName | id | telephone | address             | city |
      | Saagar    | Arya     | 69 | 123456789 | heehee hoo hoo blvd |      |
    When the User asks the System to add the Owner
    Then the new Owner is not added
    And a City Missing error message is displayed to the User

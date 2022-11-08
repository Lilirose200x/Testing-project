Feature: Create Visit
As a user of the PetClinic System, I would like to create an appointment for a Pet, so that this Pet will be treated by a Vet.

  Scenario Outline: Create a new Visit
    Given the following Visit information
      | description | petId |
      | checkup     |     1 |
    And the Visit has date
      | 2022-01-19 |
    When the user submits the new Visit
    Then the Visit is added to the system

  Scenario Outline: Attempt to create a Visit with bad Date format
    Given the following Visit information
      | description | petId |
      | checkup     |     6 |
    And the Visit has date
      | 20220119 |
    When the user submits the new Visit
    Then the visit should not be added to the database
    And an error message is displayed to the User.

  Scenario Outline: Attempt to create Visit with no Date
    Given the following Visit information
      | description | petId |
      |             |     6 |
    And the Visit has date
      | - |
    When the user submits the new Visit
    Then the Visit is added to the system

  Scenario Outline: Attempt to create a Visit with no Description
    Given the following Visit information
      | description | petId |
      |             |     6 |
    And the Visit has date
      | 2021-12-21 |
    When the user submits the new Visit
    Then the visit should not be added to the database
    And an error message is displayed to the User.

  Scenario Outline: Attempt to create a Visit with no Description and no Date
    Given the following Visit information
      | description | petId |
      |             |     6 |
    And the Visit has date
      | - |
    When the user submits the new Visit
    Then the visit should not be added to the database
    And an error message is displayed to the User.

  Scenario Outline: Attempt to create a Visit with no Description and bad Date format
    Given the following Visit information
      | description | petId |
      |             |     9 |
    And the Visit has date
      | January 5 2022 |
    When the user submits the new Visit
    Then the visit should not be added to the database
    And an error message is displayed to the User.

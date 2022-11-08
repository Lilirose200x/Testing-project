Feature: Search Owners
As a user of the PetClinic Application,
I wish to find particular owners by their last name
So that I can view or edit their information.

  Background: 
    Given the following Owners are in the system:
      | firstName | lastName | address                  | city     | telephone |
      | Saagar    | Arya     | 1234 none of ur business | montreal | 123456789 |
      | Kevin     | Arya     |          123 ur business | Toronto  | 123456789 |
      | Jack      | Black    |         123 my business. | Alaska   | 123400000 |

  Scenario: Find an existing owner with last name Black
    When the User searches for Owners with last name "<lastName>"
    Then these Owners are displayed:
      | firstName | lastName | address          | city   | telephone |
      | Jack      | Black    | 123 my business. | Alaska | 123400000 |

    Examples: 
      | lastName |
      | Black    |

  Scenario: Find existing owners with last name Arya
    When the User searches for Owners with last name "<lastName>"
    Then these Owners are displayed:
      | firstName | lastName | address                  | city     | telephone |
      | Saagar    | Arya     | 1234 none of ur business | montreal | 123456789 |
      | Kevin     | Arya     |          123 ur business | Toronto  | 123456789 |

    Examples: 
      | lastName |
      | Arya     |

  Scenario: Find existing owners without inputting a last name
    When the User searches for Owners with last name " "
    Then these Owners are displayed:
      | firstName | lastName  | address                  | city      | telephone  |
      | George    | Franklin  |       110 W. Liberty St. | Madison   | 6085551023 |
      | Saagar    | Arya      | 1234 none of ur business | montreal  |  123456789 |
      | Eduardo   | Rodriquez |        2693 Commerce St. | McFarland | 6085558763 |
      | Kevin     | Arya      |          123 ur business | Toronto   |  123456789 |
      | Selena    | McTavish  |         2387 S. Fair Way | Madison   | 6085552765 |
      | Jean      | Coleman   |          105 N. Lake St. | Monona    | 6085552654 |
      | Jack      | Black     |         123 my business. | Alaska    |  123400000 |
      | Maria     | Escobito  |            345 Maple St. | Madison   | 6085557683 |
      | David     | Schroeder |     2749 Blackhawk Trail | Madison   | 6085559435 |
      | Carlos    | Estaban   |    2335 Independence La. | Waunakee  | 6085555487 |

  Scenario: Search for Owners that do not exist
    When the User searches for Owners with last name "<lastName>"
    Then the system shall raise the error "has not been found"

    Examples: 
      | lastName |
      | Bruh     |

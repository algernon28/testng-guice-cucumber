@cart @checkout
Feature: Checkout

  Background: 
    Given I am logged as user
      | username      | password     |
      | standard_user | secret_sauce |

  Scenario Outline: remove products from cart
    And I add '<products>' as products
    And I enter the cart page
    And the cart badge shows <num>
    Then the items in the cart are <num>
    But I remove '<remove>'
    Then the items in the cart are <items left>

    Examples: 
      | products                                  | num | remove                | items left |
      | Sauce Labs Backpack,Sauce Labs Bike Light |   2 | Sauce Labs Bike Light |          1 |

  Scenario Outline: checkout products
    And I add '<products>' as products
    And I enter the cart page
    And the cart badge shows <num>

    Examples: 
      | products                                  | num | remove                | items left |
      | Sauce Labs Backpack,Sauce Labs Bike Light |   2 | Sauce Labs Bike Light |          1 |

Feature: Products

  Background: 
    Given I am logged as user
      | username                | password     |
      | performance_glitch_user | secret_sauce |
    And I land on the Products page

  @products
  Scenario: Count the products
    Then the number of products presented is 6

  @products
  Scenario: Sort the items
    And I sort the items
      | sort value | sort text           |
      | az         | Name (A to Z)       |
      | za         | Name (Z to A)       |
      | lohi       | Price (low to high) |
      | hilo       | Price (high to low) |
    Then the items are correctly sorted
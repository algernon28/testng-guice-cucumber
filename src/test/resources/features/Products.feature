Feature: Products

  Background: 
    Given I am logged as user
      | username      | password     |
      | standard_user | secret_sauce |
    And I land on the Products page

  @products
  Scenario Outline: Sort the items
    Then the number of products presented is 6
    And I sort the items for '<sort value>'
    Then the items are correctly sorted for '<sort text>'

    Examples: 
      | sort value | sort text           |
      | az         | Name (A to Z)       |
      | za         | Name (Z to A)       |
      | lohi       | Price (low to high) |
      | hilo       | Price (high to low) |

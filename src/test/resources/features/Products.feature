Feature: Products

  Background: 
    Given I am logged as user
      | username                | password     |
      | performance_glitch_user | secret_sauce |
    And I land on the Products page
     
	@products
	Scenario: Count the products
		Then the number of products presented is 6
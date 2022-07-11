Feature: Login Negative
  Test the login with bad credentials

  Background: 
    Given I am on the login page

  @login @negative
  Scenario Outline: Login with correct credentials
    When I enter username as '<username>' and password as '<password>'
    Then I land on the Products page

    Examples: 
      | username                | password     |
      | standard_user           | secret_sauce |
      | performance_glitch_user | secret_sauce |

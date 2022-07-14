Feature: Login Negative
  Test the login with bad credentials

  Background: 
    Given I am on the login page

  @login @negative
  Scenario Outline: Login with wrong credentials
    When I enter username as '<username>' and password as '<password>'
    Then I expect validation message as '<message>' is displayed

    Examples: 
      | username                | password      | message         |
      | locked_out_user         | secret_sauce  | login.lockedout |
      | locked_out_user         | wrongpassword | login.wrong     |
      | standard_user           | wrongpassword | login.wrong     |
      | performance_glitch_user | wrongpassword | login.wrong     |
      | problem_user            | wrongpassword | login.wrong     |

Feature: DFS Q1


  Scenario: a google map request
    Given I have a property file and excel payload
    When I hit run
    Then call the API test and assert
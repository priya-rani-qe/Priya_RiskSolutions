Feature: To check the links in home page

  @linkCheck
  Scenario Outline: To Verify different links are available and clickable in home page

   Given I am on the LexisNexis website 'https://risk.lexisnexis.co.uk/'
    Then I checked if the "<link>" is clickable
    Examples:
      |       link                   |
      | Financial Services           |
      | Insurance                    |
      | Life and Pensions            |
      | Corporations and Non-Profits |


  @subIndustryCheck
  Scenario Outline: To Verify different sub-Industries are available and clickable under Financial Services

    Given I am on the LexisNexis website 'https://risk.lexisnexis.co.uk/'
    When I clicked on Choose Your Industry
    Then I clicked on "<Industry>"
    And Clickable checked for "<subIndustry>"
    Examples:
    |Industry                  |subIndustry|
    |Financial Services |Financial Crime Compliance       |
    |Financial Services |Fraud and Identity Management    |
    |Financial Services |Customer Data Management         |
    |Financial Services |Credit Risk Assessment           |
    |Financial Services |Collections and Recovery         |
    |Financial Services |Investigations and Due Diligence |
    |Financial Services |Risk Orchestration               |
Feature: The user should be able to see the logs related to its role in the application, but not other one's logs

  Scenario: CouncilStaff sees its logs but not other's logs
    Given the user navigates to /
    And clicks "councilstaff_logs" link
    And a message is produced with topic "councilStaff"
    When the user waits 5 seconds
    Then there is a log of "councilStaff" on the webpage
      But there is not a log of "councilmen" on the webpage

  Scenario: Councilmen doesn't see other's logs
    Given the user navigates to /
    And clicks "councilmen_logs" link
    And a message is produced with topic "councilmen"
    When the user waits 5 seconds
    Then there is a log of "councilmen" on the webpage
      But there is not a log of "councilStaff" on the webpage

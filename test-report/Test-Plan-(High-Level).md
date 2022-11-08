> Note: We had a failed acceptance test that prevented the Jacoco report to be generated so we forced the automatic build to ignore the failed acceptance test for auto-building.

# High-level Plan
We divide our team into 3 subgroups that will each test 2 or 3 different test levels.
Our test strategy is to test the non-trivial and core functionalities of the Pet Clinic application with our limited time and resources.
Our high-level coverage goal is to have at least 90% branch coverage for all the different test levels.

## Roles and Responsibilities
Please refer to [Roles and Responsibilities](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/wiki/Roles-and-Responsibilities)

## Unit Testing
### Testing Strategy
We used white-box testing to test the functionality of the source code in the `model` and `service` directories.
Tests were selected in order to cover every branch. This means that we made at least one test case per branch. We decided to write tests only for non-trivial methods. Before writing tests for each class, we looked at the overall dependencies between the methods and decided to write tests for the methods with the least dependencies first and the method depending on trivial methods that do not need to be tested. This allowed us to use those tested methods for other methods that depended on them. 
### Coverage Goal
Our coverage goal for the model and service classes of the PetClinic application is 75% line coverage and 100% branch coverage on JaCoCo.  
[See explanations for Coverage Goals in Unit Testing](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/wiki/Unit-Testing#coverage-goal)

## Integration Testing
### Testing Strategy
Our integration strategy is the bottom-up strategy. We chose this strategy because we wanted to test the reusable components thoroughly and it allows us to do testing in different orders.   
[See more details in Integration Testing](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/wiki/Integration-Testing#provide-a-description-of-your-integration-strategy-incl-justifications)
### Coverage Goal
- Our coverage goal for the `model` classes of the PetClinic application is 75% line coverage and 95% branch coverage on JaCoCo. 
- Our coverage goal for the queries in the `persistence` classes is to test all the queries to make sure they work properly. 

[See explanations for Coverage Goals in Integration Testing](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/wiki/Integration-Testing#coverage-goal)

## API Testing
### Testing strategy
Our strategy is to create API tests that reach our goal of 90% code coverage across all api endpoints. We selected this goal because it seems both attainable and effective. 100% test coverage can be difficult to achieve, but 90% code coverage leads to a similar outcome of most code covered, but is less complex than trying to get to 100%. Further, 90% is an A at McGill so we believe that it should be an A in our tests too.
### Coverage Goal
API testing aims to investigate API endpoints defined in the controller classes of the PetClinic application. Our goal is to achieve 90% code coverage with our API testing.

## Acceptance Testing
### Testing strategy
For our acceptance testing, we used black box testing to test the functionality of our application. We made this decision because at the system level of testing, black box testing is more appropriate than white box. This is due to the fact that black box testing helps catagorize all inputs and helps derive the expected outputs of the system. We used a scenario graph in order to derive the test cases for the features that we selected. We started by creating a scenario graph. This scenario graph held our main scenario - then for each node of the graph, we developed an alternate path (if needed). These alternate paths allow us to generate all the test cases that we require. 
### Coverage Goal
Our goal for this section is to implement three of these generated test cases. Instead of doing multiple asynchronous sessions, we decided to work on all of our tasks together using VS Code's live share feature. This enabled us to share terminals and code without the use of git. This allowed us to work on the same code base and test cases at the same time.

## Non-Functional Test
No extra test plan was generated for non-functional testing as mentioned in the specification of this project. For more information, please refer to [Non-functional testing](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/wiki/Non-Functional-(Performance)-Testing).


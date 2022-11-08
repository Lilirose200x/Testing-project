## Automation
We have automated the execution of our tests on the build for Unit, Integration, API, and Acceptance test  with the maven build tool. The modification of the [pom.xml](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/9caf390249c7ac2c9327702771ba340236966542/pom.xml#L258-L276) file is as below: 
![pom](https://user-images.githubusercontent.com/48535851/199908445-330104bf-7221-43be-9af8-16f4ac675902.png)

## Code Coverage
We also integrated the code coverage tool JaCoCo in our build, generating the coverage reports for our tests.
Steps: 
1. Navigate to the root directory
2. Run `./mvnw clean test`
3. Open the `index.html` file, located under `./target/site/jacoco` folder, by browser tools.

## Continuous integration
CI with GitHub Actions is implemented:
![CI](https://user-images.githubusercontent.com/48535851/199906911-6ad4e661-5600-48b2-9f10-b167f57c8ac3.png)
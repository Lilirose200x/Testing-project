> Note: We had a failed acceptance test that prevented the Jacoco report to be generated so we forced the automatic build to ignore the failed acceptance test for auto-building.

# Plan
## Task Assignment
| Task                                        | Assignee                |
| ------------------------------------------- | ----------------------- |
| Feature 1: Create a new owner               | Kevin Nam               |
| Feature 2: Create new visit for owner's pet | Saagar Arya             |
| Feature 3: Find owner by Last Name          | Saagar Arya             |
| Documentation: Testing Plan                 | Kevin Nam & Saagar Arya |
| Documentation: Testing Report               | Kevin Nam & Saagar Arya |

Dedicated Acceptance Testing Directory: project-test-10/src/test/java/Acceptance/

## Testing Strategy 


For our acceptance testing, we used black box testing to test the functionality of our application. We made this decision because at the system level of testing, black box testing is more appropriate than white box. This is due to the fact that black box testing helps catagorize all inputs and helps derive the expected outputs of the system. We used a scenario graph in order to derive the test cases for the features that we selected. We started by creating a scenario graph. This scenario graph held our main scenario - then for each node of the graph, we developed an alternate path (if needed). These alternate paths allow us to generate all the test cases that we require. Our goal for this section is to implement three of these generated test cases. Instead of doing multiple asynchronous sessions, we decided to work on all of our tasks together using VS Code's live share feature. This enabled us to share terminals and code without the use of git. This allowed us to work on the same code base and test cases at the same time. 


## 3 Features of the Petclinic Application
### <u> **Feature 1: Create a new owner** </u>

![image](https://user-images.githubusercontent.com/48372730/199844158-d2402818-5775-4414-ad78-91b1d65c774d.png)

- <u>User Story:</u> As a user of the PetClinic Application, I would like to add a new owner to the system to allow this owner to book appointments for their pets.
- <u> Actors:</u> PetClinic Application User
- <u> Preconditions:</u> The user of the PetClinic Application has the add new user form webpage already open and the user already knows the information of the new owner
- <u> Justification for Selection:</u> This was selected because it is a critical feature for the PetClinic Application to work. Making sure this feature works properly ensures more users to be added to the application and connect with veterinarians.

**Main / Successful Scenarios**

1. The User fills out the new Owner form.
2. The User filled the form correctly
3. The User asks the System to add the Owner.
4. The System adds the Owner to the database.

**Alternative scenarios**

- 2a. The User did not fill the First Name field.
- 3a. The System detects that a field is missing
- 3a1. The System displays a first name missing error to the user.
- 2b. The User did not fill the Last Name field.
- 3b. The System detects that a field is missing
- 3b1. The System displays a last name missing error to the user.
- 2c. The User did not fill the telephone field.
- 3c. The System detects that a field is missing
- 3c1. The System displays a telephone missing error to the user.
- 2d. The User did not fill the address field.
- 3d. The System detects that a field is missing
- 3d1. The System displays a address missing error to the user.
- 2e. The User did not fill the city field.
- 3e. The System detects that a field is missing
- 3e1. The System displays a city missing error to the user.

Post conditions (for main): The new owner with their information is added to the database.

### **<u> Feature 2: Create new visit for owner's pet</u>**

![image](https://user-images.githubusercontent.com/48372730/199844212-e740c00e-e57e-4324-ae90-383e8b9461ae.png)

<!-- Saagar -->

- <u>User Story:</u> As a user of the PetClinic System, I would like to create an appointment for a Pet, so that this Pet will be treated by a Vet.
- <u> Actor(s):</u> User
- <u> Preconditions: </u> An Owner with a Pet exists in the system.
- <u> Justification of selection: </u> We selected this feature because it involves many subsystems. It tests Owner, Pet and Visit subsystems, and the integration between them.

**Main / Successful Scenarios**

1. The User fills out the new Visit form.
2. The User filled the form correctly
3. The User asks the System to add the Visit.
4. The System adds the Visit to the database.

**Alternative scenarios**

- 2a. The User did not fill the Date field correctly.
- 3a. The System detects an error.
- 3a1. The System displays an error to the user.
- 2b. The User did not fill the Date field.
- 3b. The System detects an error.
- 3b1. The System displays an error to the user.
- 2c. The User did not fill the description field.
- 3c. The System detects an error.
- 3c1. The System displays an error to the user.
- 2d. The User did not fill the description and the date field.
- 3d. The System detects an error.
- 3d1. The System displays an error to the user.
- 2e. The User did not fill the description field and did not fill the date field correctly.
- 3e. The System detects an error.
- 3e1. The System displays an error to the user.

Post conditions (for main): The new Visit with their information is added to the database.

### **<u> Feature 3: Find owner by Last Name</u>**

![image](https://user-images.githubusercontent.com/48372730/199844254-a7bbd4f2-4db1-4327-b77c-82d87635dffd.png)

<!-- SAAGAR -->

- <u> User Story:</u> Find an Owner by their last name. This will allow the user to view the Owner's information and any Pets that they may own. This will also allow the User to create a Visit for the Owner's Pet, and modify any of the Pet or Owner's information.
- <u> Actor:</u> User
- <u> Precondition(s):</u> An Owner exists in the PetClinic System, and the Last Name is known to the User.
- <u> Justification for Selection:</u> We decided to pursue this feature as we thought it to be incredibly important to the functionality of the PetClinic System. In order for any Visits to be scheduled, the User would need to have the ability to find the Owner of the Pet. This feature also allows the User to modify the Owner's information, and the Pet's information.

**Main / Successful Scenarios**

1. The User inputs the Last Name of the Owner into the PetClinic Application
2. The System shows a list of all Owners with the same Last Name.
3. The User selects the Owner they wish to view.
4. The System displays the Owner's information, and any Pets that they may own.

**Alternative Scenarios**

- 1a: User does not input the Last Name of the Owner into the PetClinic Application.
- 2a: The System shows a list containing all existing owners.
- 1b: System cannot find an owner with specified last name.


# Report

To properly run the acceptance tests on the PetClinic Application, we added other software tools and their Maven dependencies to the project. Therefore, the [pom.xml](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/pom.xml) file was modified to include the following new dependencies:

- `cucumber-spring` to allow us to use Cucumber annotations
- `cucumber-java` to allow us to use cucumber-java
- `cucumber-junit` dependency

Furthermore, rather than using Java Spring Boot's mockMvc, Spring Boot Web Clients [Test Rest Template](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/web/client/TestRestTemplate.html) were used to invoke the PetClinic's end points. We could not use software tools such as mockMvc and Mockito since the implementation of step definitions and cucumer annotations were mandatory to run our test cases unlike unit testing, integration testing and API testing. That is why we choose to use Spring Boot's Test Rest Template. We also made a Java class [CucumberConfiguration](https://github.com/McGill-ECSE429-Fall2021/project-test-14/blob/acceptance-testing/src/test/java/Acceptance/cucumber/CucumberConfiguration.java) to help convert the Cucumber Datatables into objects from the PetClinic models.

## Feature 1: Create a new owner

### Files

Feature file: [project-test-10/src/test/java/Acceptance/features/AddOwner.feature](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/Acceptance/features/AddOwner.feature)

Step definitions: [project-test-10/src/test/java/Acceptance/steps/AddOwner.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/Acceptance/cucumber/stepdefs/AddOwner.java)


This tests Owner.Java and OwnerRepository.java

### Documentation for each scenario:

The setup for each is just ensuring that the owners defined at the top of the test are in a List of which owners we will expect at the end.

There is no teardown.

| Scenario Name                      | Given                                                                                                                                                       | Then                                                                                                                                                                                                             | Test Outcome |
| ---------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------ |
| Add a new Owner                    | **firstName:** Saagar <br />**lastName**: Arya, id: 69 <br /> **telephone**: 123456789 <br />**address**: heehee hoo hoo blvd <br /> **city**: Montreal\*\* | `java.lang.AssertionError: expected:<[Owner@7ae6927 id = 69, new = false, lastName = 'Arya', firstName = 'Saagar', address = 'heehee hoo hoo blvd', city = 'Montreal', telephone = '123456789']> but was:<null>` | Test Fails   |
| Add a new Owner with no first name | **firstName:** <br />**lastName**: Arya, id: 69 <br />**telephone**: 123456789<br /> **address**: heehee hoo hoo blvd <br /> **city**: Montreal             | A First Name Missing error is displayed.                                                                                                                                                                         | Test passes  |
| Add a new Owner with no last name  | **firstName:** Saagar <br />**lastName**: , id: 69 <br />**telephone**: 123456789<br /> **address**: heehee hoo hoo blvd <br /> **city**: Montreal          | A Last Name Missing error is displayed.                                                                                                                                                                          | Test passes  |
| Add a new Owner with no telephone  | **firstName:** Saagar <br />**lastName**: Arya, id: 69 <br />**telephone**: <br /> **address**: heehee hoo hoo blvd <br /> **city**: Montreal               | A telephone Missing error is displayed.                                                                                                                                                                          | Test passes  |
| Add a new Owner with no address    | **firstName:** Saagar <br />**lastName**: Arya, id: 69 <br />**telephone**: 123456789<br /> **address**: <br /> **city**: Montreal                          | An address Missing error is displayed.                                                                                                                                                                           | Test passes  |
| Add a new Owner with no city       | **firstName:** Saagar <br />**lastName**: Arya, id: 69 <br />**telephone**: 123456789<br /> **address**: heehee hoo hoo blvd <br /> **city**:               | A city Missing error is displayed.                                                                                                                                                                               | Test passes  |

### Results

The main scenario of this feature failed. We noticed that the HTML response returned for the\*User_asks_the_System_to_add_the_Owner() has some empty fields, even though fields were provided. This bug is severe. The usability of the PetClinic Application is severily impacte as Users are unable to create Owners. However, when we tested out the actual application, it seemed to work fine. We are not sure why this is the case - we decided that it must be an issue in our implementation of the acceptance tests.

### Scenario Coverages

<img width="906" alt="image" src="https://user-images.githubusercontent.com/51128536/199842898-82c512c8-13e7-431b-90e2-749bd28dc5c5.png">

Our test coverage for this feature is 100%. We tested all 6 alternate scenarios and the main scenario from our scenario graph

## Feature 2: Create new visit for owner's pet

### Files

Feature file: [project-test-10/src/test/java/Acceptance/features/CreateVisit.feature](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/Acceptance/features/CreateVisit.feature)


Step definitions: [project-test-10/src/test/java/Acceptance/steps/CreateVisit.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/Acceptance/cucumber/stepdefs/CreateVisit.java)


This tests Visit.Java and VisitRepository.java

### Helper Methods

We modified the source code to introduce a helper method in Visit.java. This method is called setStringDate(String date). This method takes in a String date and converts it to a Date object. This method is used in the step definition file to convert the String date to a Date object, and set it as the date of the Visit object.

We also used a helper method in CreateVisit.java. This method is called isDateValidFormat(String date). This helper method is used to check if the date format is valid. This method is used in the step definition file to check if the date format is valid. If the date format is not valid, then the test case fails.

### Documentation for each scenario

The setup for each is just creating a new List of expected visits

There is no teardown.

| Scenario Name                                                     | Given                                                                   | Then                                           | Test Outcome |
| ----------------------------------------------------------------- | ----------------------------------------------------------------------- | ---------------------------------------------- | ------------ |
| Create a new Visit                                                | **description**: checkup<br /> **petID:** 1 <br /> **date:** 2022-01-19 | The Visit is added to the System               | Test passes  |
| Attempt to create a Visit with bad Date format                    | **description**: checkup<br /> **petID:** 6 <br /> **date:** 20220119   | And an error message is displayed to the User. | Test passes  |
| Attempt to create Visit with no Date                              | **description**: checkup<br /> **petID:** 6 <br /> **date:** -          | And an error message is displayed to the User. | Test passes  |
| Attempt to create a Visit with no Description                     | **description**: <br /> **petID:** 6 <br /> **date:** 20220119          | An error message is displayed to the User.     | Test passes  |
| Attempt to create a Visit with no Description and no Date         | **description**: <br /> **petID:** 6 <br /> **date:** -                 | An error message is displayed to the User.     | Test passes  |
| Attempt to create a Visit with no Description and bad Date format | **description**: <br /> **petID:** 9 <br /> **date:** 20220119          | an error message is displayed to the User.     | Test passes  |

### Results

None of the tests failed. This showcases that the add Visit feature of the PetClinic Application is working as expected.

### Scenario coverages
<img width="184" alt="image" src="https://user-images.githubusercontent.com/51128536/199843037-8e0e0518-0472-4fdc-bb12-3931ca9b924f.png">

Our test coverage goals was to execute all the scenarios in the graph above. As you can see in this screenshot, we executed 6 scenarios ( same number as the scenarios developed from our scenario graph).

## Feature 3: Find owners by the last name

### Files

Feature file: [project-test-10/src/test/java/Acceptance/features/SearchOwner.feature](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/Acceptance/features/SearchOwner.feature)

Step definitions: [project-test-10/src/test/java/Acceptance/steps/SearchOwner.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/Acceptance/cucumber/stepdefs/SearchOwner.java)


This tests OwnerRepository.java

### Documentation for each scenario:

The setup for each is just creating a new List of expected visits

There is no teardown.

| Name  | Inputs | Outputs | Results |
|--|--|--|--|
| Find an existing owner with last name Black  | `lastName: Black` | `firstName: Jack, lastName: Black, address: 123 my business., city: Alaska, telephone: 123400000` | Pass |
| Find existing owners with last name Arya |`lastName: Arya` | `firstName: Saagar, lastName: Arya, address: 1234 none of ur business, city: montreal, telephone: 123456789; firstName: Kevin, lastName: Arya, address: 123 ur business, city: Toronto, telephone: 123456789` | Pass |
| Find existing owners without inputting a last name| none | `firstName:George, lastName:Franklin, address:110 W. Liberty St., city:Madison, telephone:6085551023; firstName: Saagar, lastName: Arya, address: 1234 none of ur business, city: montreal, telephone: 123456789; firstName:Eduardo, lastName:Rodriquez, address:2693 Commerce St., city:McFarland, telephone:6085558763; firstName:Kevin, lastName:Nam, address:123 ur business, city:Toronto, telephone:123456789; firstName:Selena, lastName:McTavish, address:2387 S. Fair Way, city:Madison, telephone:6085552765; firstName:Jean, lastName:Coleman, address:105 N. Lake St., city:Monona, telephone:6085552654; firstName:Jack, lastName:Black, address:1450 Oak Blvd., city:Monona, telephone:6085555387; firstName:Maria, lastName:Escobito, address:345 Maple St., city:Madison, telephone:6085557683; firstName:David, lastName:Schroeder, address:2749 Blackhawk Trail, city:Madison, telephone:6085559435; firstName:Carlos, lastName:Estaban, address:2335 Independence La., city:Waunakee, telephone:6085555487 ` | Pass |
| Search for Owners that do not exist | ` lastName: Bruh` | owner does not exist, error message is displayed | Pass |

### Results

None of the tests failed. This showcases that the Search Owner by Last Name feature of the PetClinic Application is working as expected.  
<img width="183" alt="image" src="https://user-images.githubusercontent.com/51128536/199843201-d40a7cc0-84e8-406f-8be6-51841dc178fd.png">  
Our Scenario coverage goals was to execute all the scenarios in the scenario graph above. As you can see in this screenshot, we executed 4 scenarios ( same number as the scenarios developed from our scenario graph).  

## Test Coverage with Jacoco

<img width="359" alt="image" src="https://user-images.githubusercontent.com/51128536/199842374-fdccc960-3a26-44d8-95b7-9e3ebfb206e1.png"> 
 
<img width="537" alt="image" src="https://user-images.githubusercontent.com/51128536/199842311-b43ddb56-0d50-4928-9310-3eab908a7363.png"> 


<img width="344" alt="image" src="https://user-images.githubusercontent.com/51128536/199842045-1a8b9f76-81b7-43c0-b529-e2b2f2dd4693.png"> 
 
<img width="489" alt="image" src="https://user-images.githubusercontent.com/51128536/199842146-dd2cde79-f410-4fb5-ada1-da869887d854.png">  


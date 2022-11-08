# Plan

## Coverage Goal
We hope to create API tests that reach our coverage goal of 90% code coverage across all API endpoint in the controller classes of the PetClinic
application. We selected this goal because it seems both attainable and effective. 100% test coverage can be difficult to achieve, but 90% code coverage leads to a similar outcome of most code covered but is less complex than trying to get to 100%. Further, 90% is an A at McGill so we believe that it should be an A in our tests too.

## Potential Risk
The final 10% of the code coverage that we may not achieve could have severe problems. This would have severe impacts on the user experience. Hopefully, this does not happen. 

## Design
We created a JUnit test suite, located in the following folder: `project-proj-10/src/main/java/org/springframework/samples/petclinic/controller`

# Report
## Test Input Data
Our test suite does not require any input test data.

## Integration of Mocking Technologies
We used MockMvc and Mockito to integrate the mocking technologies. We make a call to an endpoint and we then assert that the output is exactly what we had expected.
In order to get the code coverage, we used jacoco. Simply run the following commands in order to get the code coverage to display:
`mvn spring-javaformat:apply`
`mvn clean jacoco:prepare-agent install jacoco:report`
Then open the following file: `project-proj-10/target/site/jacoco/index.html`

## Achieved Coverage
We were aiming for an average of 90% code coverage, which we achieved. 

<img width="327" alt="image" src="https://user-images.githubusercontent.com/51128536/199858487-6451ffee-ab7a-4c43-9079-ce9bda024609.png">

Individual test coverages for each endpoint are shown below under their respective test cases.

## Test Failures
None of our tests fail! This shows that at least 90% of the Controller method code works as intended.

## List of Test Cases and Coverage Report for Each Controller-Under-Test
### OwnerController
[OwnerControllerTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/controller/OwnerControllerTests.java)  
**Setup**: Create two Owners and set Mockito to return these Owners whenever requests are asked from the repository

#### Tests
|Test Name|Test URL|Inputs|Test Description|Test Results|
|--|--|--|--|--|
|testInitCreationForm|`/owners/new`|None|This test confirms that calling the create Owner endpoint shows the createOrUpdateOwnerForm|Passes|
|testProcessCreationFormSuccess|`/owners/new`|firstName: Kevin, lastName: Nam, address: 1234 get on the dance floor bld, city: montreal, telephone: 6085551023|This test confirms that calling the create Owner endpoint redirects the user to the correct page.|Passes|
|testProcessCreationFormHasErrors|`/owners/new`|firstName: Camiller, lastName: Cabeller, city: Montreal|The goal of this test is to see if the controller notices the absence of the address and telephone field, and returns the correct error. | passes|
|testInitFindForm|`/owners/find`|none|The goal of this test is to see if the endpoint shows the correct page | passes|
|testProcessFindFormNoOwnersFound|`/owners`|lastName: LOL|The goal of this test is to see if the endpoint redirects the user to the correct page | passes|
|testProcessFindFormOneOwnerFound|`/owners`|lastName: Arya|The goal of this test is to see if the endpoint redirects the user to the correct page of that owner | passes|
|testProcessFindFormMultipleOwners|`/owners`|lastName: Nam|The goal of this test is to see if the endpoint redirects the user to the correct page that shows the list of found owners | passes|
|testInitUpdateOwnerForm|`/owners/1/edit`|None|The goal of this test is to see if the endpoint redirects the user to the correct page where they can edit the owner with id 1 | passes|
|testProcessUpdateOwnerFormSuccess|`/owners/1/edit`|firstName: Saagar, lastName: Arya, address: 1234 get on the dance floor bld, city: Montreal, telephone:6085551023 |The goal of this test is to see if the endpoint redirects the User when they try to update an Owner correctly | passes|
|testProcessUpdateOwnerFormHasErrors|`/owners/1/edit`|firstName: Joe, lastName: Bloggs, address:, telephone: |The goal of this test is to see if the endpoint redirects the User back to the page with an error after they missed a few fields | passes|
|testShowOwner|`/owners/1`|firstName: Joe, lastName: Bloggs, address:, telephone: |The goal of this test is to see if the endpoint shows the User an Owner object when they try to see the owner. | passes|

#### Coverage
<img width="538" alt="image" src="https://user-images.githubusercontent.com/51128536/199858980-7f318e66-ef16-4ffd-bbc3-d21346b74394.png">

The test coverage for the OwnerController is 98%. This is good.


### PetController
[PetControllerTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/controller/PetControllerTests.java)  

#### Tests
|Test Name|Test URL|Inputs|Test Description|Test Results|
|--|--|--|--|--|
|testInitCreationForm|`/owners/1/pets/new`|None|This test is to check if requesting to create a Pet for an Owner opens the correct form|passes|
|testProcessCreationFormHasErrors|`/owners/1/pets/new`|name: bob, birthDate: 2016/12/12|This test is to check if requesting to create a Pet for an Owner with an error keeps the same page open|passes|
|testProcessUpdateFormSuccess|`/owners/1/pets/1/edit`|name: Betty, birthDate: 2016/12/12, type: hamster|This test is to check if requesting to edit a pet of an Owner without any errors works|passes|
|testProcessUpdateFormHasErrors|`/owners/1/pets/1/edit`|name: Betty, birthDate: 2016/12/12|This test is to check if creating a pet of an Owner with any errors keeps the same page open|passes|

#### Coverage
<img width="586" alt="image" src="https://user-images.githubusercontent.com/51128536/199859034-49193b97-2d6e-4f3c-86c6-5ad42600aadc.png">

As shown in the image above, the test coverage for the PetController is 72%. This is below the coverage that we had wanted in average across all endpoints. We still succeeded in our goal, as the average coverage was 90% as seen in the image at the start of this section. 


### VisitController
[VisitControllerTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/controller/VisitControllerTests.java)  
**Setup**: Tell Mockito to return a new Pet when a request is made to the repository

#### Tests
|Test Name|Test URL|Inputs|Test Description|Test Results|
|--|--|--|--|--|
|testInitNewVisitForm|`/owners/1/pets/1/visits/new`|None|This test is to check if requesting to create a visit for a pet opens the correct form|passes|
|testProcessNewVisitFormHasErrors|`/owners/1/pets/1/visits/new`|name: Saagar|This test is to check if creating a visit for a pet with any errors keeps the same page open|passes|
|testProcessNewVisitFormSuccess|`/owners/1/pets/1/visits/new`|name: Saagar, description: Visit Description|This test is to check if requesting to create a visit for a pet without any errors redirects the user to the correct page|passes|

#### Coverage
<img width="494" alt="image" src="https://user-images.githubusercontent.com/51128536/199859059-992c702a-a397-4b4c-9164-c24bd2772ac0.png">

The test coverage for the VisitController was 100%. This is excellent.


### CrashController
[CrashControllerTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/controller/CrashControllerTests.java)  

#### Tests
|Test Name|Test URL|Inputs|Test Description|Test Results|
|--|--|--|--|--|
|testCrash|`/oups`|None|This test is to check if requesting to the crash page shows the correct page|passes|

#### Coverage
<img width="329" alt="image" src="https://user-images.githubusercontent.com/51128536/199859100-3e686fb9-6512-4894-a13a-88142542eb68.png">

The test coverage for the provided CrashController is 100%. This is excellent.

### WelcomeController
[WelcomeControllerTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/controller/WelcomeControllerTests.java)
#### Coverage
<img width="343" alt="image" src="https://user-images.githubusercontent.com/51128536/199859149-ccd5b7b5-e4ba-4953-b402-736e812cc6fe.png"> 

The test coverage for the provided WelcomeController is 100%. This is excellent.  

### VetController
[VetController.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/controller/VetControllerTests.java)  
#### Coverage
<img width="392" alt="image" src="https://user-images.githubusercontent.com/51128536/199859081-df945249-0594-4f1f-9f3a-2b15374c353c.png">

The test coverage for the VetController is 100%. This is excellent.
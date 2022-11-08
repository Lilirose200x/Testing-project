# Plan
## Coverage Goal
Our coverage goal for the model classes of the PetClinic application is 75% line coverage and 95% branch coverage on JaCoCo. This is because we had 76% line coverage and 95% branch coverage in unit testing and since we only added more integration tests for the model classes that call methods that have been covered in unit testing. 

Our coverage goal for the queries in the `persistence` classes is to test all queries in the OwnerRepository and PetRepository because we would like to make sure that all queries work properly.

# Report
## Test Input Data
We did not put input data in an external resources file.

## High-Level Block Diagram With Dependencies
![image](https://user-images.githubusercontent.com/48372730/199868186-85b48cf6-e0b1-4626-874b-18a5f5e49b08.png)

As seen in the diagram above, there are dependencies between Pet and PetType, Pet and Visit, Owner and Pet, Vets and Vet, and Vet and Specialty

## Integration Strategy
Our integration strategy is the bottom-up strategy. We chose this strategy because we wanted to test the reusable components thoroughly and it allows us to do testing in different orders.

The integration tests between different classes in the `model` package can be found under `src/test/java/org/springframework/samples/petclinic/it`. Since we only need to write a single test case per dependency for the `model` classes, we first went through all the classes and took note of the methods that depend on other `model` classes. We noticed that the Pet model class interacts with the Owner model class, and as well that the Visit model class interacts with the Pet model class. Therefore, we wrote a test for each of these dependencies (see [OwnerItTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/it/OwnerItTests.java) and [PetItTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/it/PetItTests.java)).
At the higher level, the integration between the controllers and the model classes will be handled by the API tests.

Our test strategy for the queries in the `persistence` classes is to write a single test case per dependency to ensure that the queries work as intended. As there is no query in the VetRepository.java and VisitRepository.java, the 2 repositories are not tested.

## List of Test Cases

### Model Classes
#### Owner
[OwnerItTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/it/OwnerItTests.java)
|Test case method name|Goal|Input|Outputs|
| ------- | ------- | ------- | ------- | 
|testAddPet_Pet_setOwner()|Verify that the Pet model class interacts with the Owner model class correctly|- New pet|- Assert that the size of the list of pets has increased by 1<br> - Assert that the list of pets contains the added pet<br> - Assert that the added pet’s owner has been set to the correct owner|

#### Pet
[PetItTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/it/PetItTests.java)
|Test case method name|Goal|Input|Outputs|
| ------- | ------- | ------- | ------- | 
|testAddVisit_Visit_SetPetId()|Verify that the Visitmodel class interacts with the Pet model class correctly|- New visit|- Assert that the size of the list of visits has increased by 1 <br> - Assert that the list of visits contains the added visit <br> - Assert that the added visit’s pet id has been set to the correct pet id <br>|


### Persistence Classes 
#### OwnerRepository
[OwnerRepoTest.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/persistence/OwnerRepoTest.java)  
|Test case method name|Goal|Input|Outputs|
| ------- | ------- | ------- | ------- | 
|testFindByLastNameValid|Test if we can find the owner created by the last name|Input is LastName of a owner created before the test starts| Check if the owner is successfully created and the attributes of the owners.|
|testFindByLastNameInValid|Test if we can find the owner created by an Invalid last name|Input is a Invalid last name of a owner| Check that the owner found is null.|
|testFindByValidId|Test if we can find the owner by Id|Input is a valid id of an owner created before the test|Check if the owner is found and the attributes of the owner are the same with the created owenr|
|testFindByInvalidID|Test if we can find the owner by an invalid ID|Input is a random Invalid ID|Check if the method will return a null|
#### PetRepository
[PetRepoTest.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/persistence/PetRepoTest.java)
|Test case method name|Goal|Input|Outputs|
| ------- | ------- | ------- | ------- | 
|testFindPetTypes|Test if we can get all of the pet types in the System|There is no input|Check if the output is more than 0 and for each pet type in the list has the name same as what is stored in the System|
|testFindByIdValidId|Test if we can get the pet by its id.|The input id is 1|Check if the pet returned by the method is not null and the attributes of the pet are the same as the stored pet object. The last name of the owner is also checked.|
|testFindByIdInvalidId|Test if we can get a pet by an invalid id|Input is a random invalid id|Check if the pet returned by the method is null|

## Drivers, Mocks and Stubs
We did not use any mocking or stubs for integrating the `model` classes because of our integration strategy. Contrary to the unit tests, we wanted to test the integration between the model classes, which meant that we wanted to run the methods without mocking. For each of our integration tests for the models, we had to create drivers in order to call our methods under test.

We also did not use any mocking or stubs for integrating the `persistence` classes because we did not need to.


## Changes to the Source Code
We did not make any changes to the source code.


## Achieved Coverage

Our integration tests for the `model` classes do not really contribute to increasing our code coverage. This is because our unit tests are pretty thorough and these model integration tests are effectively combining some of the unit tests.

We verified that all the queries in the persistence classes are 100% covered.


### Jacoco Report for Model Classes
![image](https://user-images.githubusercontent.com/70775435/200136778-4de7b9aa-3156-4546-bfd7-718477ce3728.png)
We have 98% of line coverage and 95% of branch coverage for the model classes.

### Jacoco Report for Controller, Model, Application and Service 
![image](https://user-images.githubusercontent.com/70775435/200136834-b45bed11-e25f-4afb-a809-607f2d1fec16.png)
We have 94% of line coverage for the entire application and 89% of branch coverage for the entire application



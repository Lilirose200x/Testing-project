> We decide to demonstrate the **white-box testing techniques** used in unit test.

# Plan
We applied the white-box testing technique in unit testing. We selected this approach instead of black-box testing because we do not have sufficient documentation on the methods that we are testing. Viewing the system from a back-box perspective would require us to be more aware of the developers’ intentions when creating the methods. However, with white-box testing, we are able to test the code as we see it to guarantee that it is functional. The goal of unit testing is to make sure that each component of the system works, therefore we decided to use white-box testing and write tests to achieve full branch coverage. 
The benefits of this chosen technique are:
* Make sure each component works on its own (isolated from other parts of the system)
* Almost all possible branches for different methods are covered by the tests
* Builds confidence in each component’s quality
* Tests source code to make sure implementation is technically correct

Specifically, we decided to focus on branch/edge coverage to ensure that each edge of the control flow graphs are traversed at least once. This is also the IEEE unit test standard for minimum coverage, making it a good candidate for our testing. Alternatively, opting for another technique like full path coverage would be unreasonable and quite excessive from a test-creation perspective as this is not a safety-critical system.

# Report
## Intermediate Steps and Models
### `addPet(Pet pet)` method in the `Owner.java` class
For applying the white-box testing technique and having 100% branch coverage, we will use the `addPet(Pet pet)` method in the Owner class in order to illustrate the intermediate steps and models. We created the following control flow graph for the method.

#### Control Flow Graph of the `addPet(Pet pet)`
![image](https://user-images.githubusercontent.com/70775435/199297889-02d0ea36-a92a-4143-ad38-2b58509bbf6d.png)

The control flow graph allowed us to generate an appropriate number of test cases to ensure full branch coverage as seen in this table of paths. 

#### Paths of the `addPet(Pet pet)`
|Path|pet.isNew()|Condition|
| ---| --- | --- |
|1-2-3 | True | T→T |
|1-3| False | F→F|

This table provided us with the specific test cases that we needed. After implementing them and running code coverage, we were able to verify that the method was fully covered.


### `getVisitsInternal()` method in the `Pet.java` class
After writing our unit tests and viewing our coverage report, we noticed that the `getVisitsInternal()` method in the Pet class was not fully covered. To investigate, we created a control flow graph and a table of paths. This made us realize that we needed a second test for this method. However, given that we are not allowed to modify the source code for the purpose of the unit tests, we are forced to leave it as is, without complete branch coverage.

![image](https://user-images.githubusercontent.com/70775435/199298365-3c55e6aa-ab54-466d-9dd8-1fc168e0605e.png)

#### Control Flow Graph of the `getVisitsInternal()`
![image](https://user-images.githubusercontent.com/70775435/199294865-59d1379d-c977-400a-aab7-6941facc3e87.png)

#### Paths of the `getVisitsInternal()`
|Path|this.visits|Condition|
| ---| --- | --- |
|1-2-3 | null | T→T |
|1-3| !null | F→F|




## List of Test Cases

[OwnerTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/model/OwnerTests.java)


<table>
  <tr>
   <td>Method Under Test
   </td>
   <td>Test Case Method Name
   </td>
   <td>Input
   </td>
   <td>Outputs
   </td>
  </tr>
  <tr>
   <td>getPetsInternal(): Set&lt;Pet>
   </td>
   <td>testGetPetsInternal_Empty()
   </td>
   <td>Empty set of pets
   </td>
   <td>Asserts that empty set is returned by getPetsInternal()
   </td>
  </tr>
  <tr>
   <td>getPetsInternal(): Set&lt;Pet>
   </td>
   <td>testGetPetsInternal_Multiple()
   </td>
   <td>Set of pets containing pets
   </td>
   <td>Asserts set of pets returned is the same as the input
   </td>
  </tr>
  <tr>
   <td>getPets(): void
   </td>
   <td>testGetPets()
   </td>
   <td>Unsorted lists of pets
   </td>
   <td>Assert that Sorted list of pets is returned
   </td>
  </tr>
  <tr>
   <td>addPet(Pet pet): void
   </td>
   <td>testAddPet_NewPet()
   </td>
   <td>New pet
   </td>
   <td>Assert size of list of pets increased
   </td>
  </tr>
  <tr>
   <td>addPet(Pet pet): void
   </td>
   <td>testAddPet_existingPet()
   </td>
   <td>Existing pet
   </td>
   <td>- Assert size of initial pets and actual pets are equal \
- Assert that the list of pets of the owner does not contain the added existing pet
   </td>
  </tr>
  <tr>
   <td>getPet(String, boolean): Pet
   </td>
   <td>testGetPet_NonExistent_IgnoreNew()
   </td>
   <td>- Non-existent `name= "NonExistent"`
<p>
- `ignoreNew=true`
<p>
- Added 1 pet with `name=”aPet”` in pets with `id=1`
   </td>
   <td>Assert that when we call `.getPet()` on owner, it returns `null`
   </td>
  </tr>
  <tr>
   <td>getPet(String, boolean): Pet
   </td>
   <td>testGetPet_NonExistent_Not_IgnoreNew()
   </td>
   <td>- Non-existent `name= "NonExistent"`
<p>
- `ignoreNew=false`
<p>
- Existing pet has id
   </td>
   <td>Assert that when we call `.getPet()` on owner, it returns `null`
   </td>
  </tr>
  <tr>
   <td>getPet(String, boolean): Pet
   </td>
   <td>testGetPet_NonExistent_IgnoreNew_PetIsNew()
   </td>
   <td>- Non-existent `name= "NonExistent"`
<p>
- `ignoreNew=true`
<p>
- Added 1 pet with `name=”aPet”` in pets without pet id
   </td>
   <td>Assert that when we call `.getPet()` on owner, it returns `null`
   </td>
  </tr>
  <tr>
   <td>getPet(String, boolean): Pet
   </td>
   <td>testGetPet_DoesExist()
   </td>
   <td>- Existing `name = "aPet"`
<p>
- `ignoreNew=false`
<p>
- Existing pet does not have id
   </td>
   <td>Assert that when we call `.getPet()` on owner, it returns the pet (comparing the pet names)
   </td>
  </tr>
  <tr>
   <td>getPet(String, boolean): Pet
   </td>
   <td>testGetPet_DoesExist_IgnoreNew_NewPet()
   </td>
   <td>- Existing `name = "aPet"`
<p>
- `ignoreNew=true`
<p>
- Existing pet does not have id
   </td>
   <td>Assert that when we call `.getPet()` on owner, it returns `null`
   </td>
  </tr>
  <tr>
   <td>getPet(String, boolean): Pet
   </td>
   <td>testGetPet_DoesExist_IgnoreNew_NotNewPet()
   </td>
   <td>- Existing `name = "aPet"`
<p>
- `ignoreNew=true`
<p>
- Existing pet has id
   </td>
   <td>Assert that when we call `.getPet()` on owner, it returns the pet (comparing the pet names)
   </td>
  </tr>
  <tr>
   <td>getPet(String, boolean): Pet
   </td>
   <td>testGetPet_DoesExist_NotIgnoreNew_NewPet()
   </td>
   <td>- Existing `name = "aPet"`
<p>
- `ignoreNew=false`
<p>
- Existing pet does not have id
   </td>
   <td>Assert that when we call `.getPet()` on owner, it returns the pet (comparing the pet names)
   </td>
  </tr>
  <tr>
   <td>getPet(String, boolean): Pet
   </td>
   <td>testGetPet_DoesNotExist_NotIgnoreNew_NewPet()
   </td>
   <td>- Non-existent `name= "NonExistent"`
<p>
- `ignoreNew=false`
<p>
- Existing pet does not have id
   </td>
   <td>Assert that when we call `.getPet()` on owner, it returns `null`
   </td>
  </tr>
  <tr>
   <td>getPet(String): Pet
   </td>
   <td>testGetPet_NameDoesNotExist
   </td>
   <td>- Non-existent `name= "NonExistent"`
   </td>
   <td>Assert that when we call `.getPet()` on owner, it returns `null`
   </td>
  </tr>
</table>


[PersonTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/model/PersonTests.java)


<table>
  <tr>
   <td>Method Under Test
   </td>
   <td>Test Case Method Name
   </td>
   <td>Input
   </td>
   <td>Outputs
   </td>
  </tr>
  <tr>
   <td>setFirstName(String): void
   </td>
   <td>testGetSetFirstName()
   </td>
   <td>String for a name
   </td>
   <td>Assert that when we call `getFirstName()` on person, it returns the same string
   </td>
  </tr>
  <tr>
   <td>getFirstName(): String
   </td>
   <td>testGetSetFirstName()
   </td>
   <td>First name of an existing person
   </td>
   <td>Assert that when we call `getFirstName()` on person, it returns a string
   </td>
  </tr>
</table>


[PetTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/model/PetTests.java)


<table>
  <tr>
   <td>Method Under Test
   </td>
   <td>Test Case Method Name
   </td>
   <td>Input
   </td>
   <td>Outputs
   </td>
  </tr>
  <tr>
   <td>getVisitsInternal(): Set&lt;Visit> 
   </td>
   <td>testGetVisitsInternal_Empty()
   </td>
   <td>Empty `visits`
   </td>
   <td>Assert that when we call `getVisitsInternal()`, a new empty `Set&lt;>` is returned
   </td>
  </tr>
  <tr>
   <td>getVisitsInternal(): Set&lt;Visit> 
   </td>
   <td>testGetVisitsInternal()
   </td>
   <td>3 visits in the system
   </td>
   <td>Assert that when we call `getVisitsInternal()`, the `Set&lt;>` of the Pet’s Visits is returned
   </td>
  </tr>
  <tr>
   <td>getVisits(): List&lt;Visit>
   </td>
   <td>testGetVisits()
   </td>
   <td>Unsorted lists of visits
   </td>
   <td>Assert that list of visits sorted by date is returned
   </td>
  </tr>
  <tr>
   <td>addVisit(Visit): void
   </td>
   <td>testAddVisit()
   </td>
   <td>A visit
   </td>
   <td>Assert that the visit was added to the pet’s list of visits (by comparing the size of `visits` and verifying that `visits` contains the added visit)
   </td>
  </tr>
</table>


[VetsTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/model/VetsTests.java)


<table>
  <tr>
   <td>Method Under Test
   </td>
   <td>Test Case Method Name
   </td>
   <td>Input
   </td>
   <td>Outputs
   </td>
  </tr>
  <tr>
   <td>getVetList(): List&lt;Vet>
   </td>
   <td>testGetVisitsInternal_Empty()
   </td>
   <td>Empty list of Vets
   </td>
   <td>Asserts that when we call `getVetList()`, a new empty ArrayList is returned
   </td>
  </tr>
</table>


[VetTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/model/VetTests.java)


<table>
  <tr>
   <td>Method Under Test
   </td>
   <td>Test Case Method Name
   </td>
   <td>Input
   </td>
   <td>Outputs
   </td>
  </tr>
  <tr>
   <td>getSpecialtiesInternal(): Set&lt;Specialty>
   </td>
   <td>testGetSpecialtiesInternal()
   </td>
   <td>Empty `specialties`
   </td>
   <td>Assert that when we call `getSpecialtiesInternal()`, a new empty `Set&lt;>` is returned
   </td>
  </tr>
  <tr>
   <td>getSpecialtiesInternal(): Set&lt;Specialty>
   </td>
   <td>testGetSpecialtiesInternal_Multiple()
   </td>
   <td>3 specialties in the system
   </td>
   <td>Assert that when we call `getSpecialtiesInternal()`, the `Set&lt;>` of the Vet’s Specialties is returned
   </td>
  </tr>
  <tr>
   <td>getSpecialties(): List&lt;Specialty>
   </td>
   <td>testGetSpecialties()
   </td>
   <td>Unsorted lists of specialties
   </td>
   <td>Assert that list of specialties sorted by name is returned
   </td>
  </tr>
  <tr>
   <td>getNrOfSpecialties(): int
   </td>
   <td>testGetNrOfSpecialties()
   </td>
   <td>3 specialties added to system
   </td>
   <td>Assert that when we call `testGetNrOfSpecialties()`, integer `3` is returned
   </td>
  </tr>
  <tr>
   <td>addSpectialty(Specialty): void
   </td>
   <td>testAddSpecialty()
   </td>
   <td>No specialties in the system 
   </td>
   <td>Assert that the new specialties has been added to the system (assert that the size of `specialties` has been increased by 1 and that it contains the newly added specialties)
   </td>
  </tr>
</table>


[PetTypeFormatterTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/service/PetTypeFormatterTests.java)


<table>
  <tr>
   <td>Method Under Test
   </td>
   <td>Test Case Method Name
   </td>
   <td>Input
   </td>
   <td>Outputs
   </td>
  </tr>
  <tr>
   <td>parse(String text, Locale locale)
   </td>
   <td>shouldParse()
   </td>
   <td>2 pets in the system
   </td>
   <td>Assert that PetType was found (assert that name of type is equal to input)
   </td>
  </tr>
  <tr>
   <td>parse(String text, Locale locale)
   </td>
   <td>shouldThrowParseException()
   </td>
   <td>2 pets in the system
   </td>
   <td>Assert an exception from  ParseException class is thrown
   </td>
  </tr>
</table>


[PetValidatorTests.java](https://github.com/McGill-ECSE429-Fall2022/project-proj-10/blob/master/src/test/java/org/springframework/samples/petclinic/service/PetValidatorTests.java)


<table>
  <tr>
   <td>Method Under Test
   </td>
   <td>Test Case Method Name
   </td>
   <td>Input
   </td>
   <td>Outputs
   </td>
  </tr>
  <tr>
   <td>validate(Object obj, Errors errors): void
   </td>
   <td>testValidate()
   </td>
   <td>- name has a length
<p>
- pet is not new (has an id)
<p>
- pet type is not null
<p>
- pet has a birthdate
   </td>
   <td>Assert that there were no errors
   </td>
  </tr>
  <tr>
   <td>validate(Object obj, Errors errors): void
   </td>
   <td>testValidate_allWrong()
   </td>
   <td>- name does not have a length
<p>
- pet is new (no id) and pet type is not null
<p>
- pet does not have a birthdate
   </td>
   <td>Assert that there were errors for the name, type and birthdate
   </td>
  </tr>
  <tr>
   <td>validate(Object obj, Errors errors): void
   </td>
   <td>testValidate_MissingName()
   </td>
   <td>- name does not have a length
<p>
- pet is not new (has an id)
<p>
- pet type is not null
<p>
- pet has a birthdate
   </td>
   <td>Assert that there were errors for the name
   </td>
  </tr>
  <tr>
   <td>validate(Object obj, Errors errors): void
   </td>
   <td>testValidate_MissingBirthDate()
   </td>
   <td>- name has a length
<p>
- pet is not new (has an id)
<p>
- pet type is not null
<p>
- pet does not have a birthdate
   </td>
   <td>Assert that there were errors for the birth date
   </td>
  </tr>
  <tr>
   <td>validate(Object obj, Errors errors): void
   </td>
   <td>testValidate_MissingIdAndType()
   </td>
   <td>- name has a length
<p>
- pet is new (no id) and pet type is not null
<p>
- pet has a birthdate
   </td>
   <td>Assert that there were errors for the type
   </td>
  </tr>
  <tr>
   <td>validate(Object obj, Errors errors): void
   </td>
   <td>testValidate_MissingId()
   </td>
   <td>- name has a length
<p>
- pet type is not null
<p>
- pet has a birthdate
   </td>
   <td>Assert that there were no errors
   </td>
  </tr>
  <tr>
   <td>validate(Object obj, Errors errors): void
   </td>
   <td>testValidate_MissingType()
   </td>
   <td>- name has a length
<p>
- pet is not new (has an id)
<p>
- pet has a birthdate
   </td>
   <td>Assert that there were no errors
   </td>
  </tr>
  <tr>
   <td>supports(Class&lt;?>): boolean
   </td>
   <td>testSupportsEqual()
   </td>
   <td>The Pet class
   </td>
   <td>Assert that petValidator does support it
   </td>
  </tr>
  <tr>
   <td>supports(Class&lt;?>): boolean
   </td>
   <td>testSupportsSuper()
   </td>
   <td>The BaseEntity class
   </td>
   <td>Assert that petValidator does not support it
   </td>
  </tr>
</table>
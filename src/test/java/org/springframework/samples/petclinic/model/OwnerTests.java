package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerTests {

	private Owner owner;

	@Mock
	Pet petMock;

	@BeforeEach
	void setup() {
		this.owner = new Owner();
	}

	@AfterEach
	void teardown() {
		this.owner = null;
	}

	@Test // No pets, newHashSet<>) should be returned
	public void testGetPetsInternal_Empty() {
		Set<Pet> expected = new HashSet<>();
		Set<Pet> actual = owner.getPetsInternal();
		assertEquals(expected, actual);
	}

	@Test // There are pets, newHashSet<>) should be returned
	public void testGetPetsInternal_Multiple() {
		Set<Pet> pets = makePets(true);
		owner.setPetsInternal(pets); // setPetsInternal trivial working

		Set<Pet> actual = owner.getPetsInternal();
		assertEquals(pets, actual);
	}

	@Test // Initially unsorted
	public void testGetPets() {
		Set<Pet> pets = makePets(false);
		owner.setPetsInternal(pets);
		ArrayList<Pet> petsArrayList = new ArrayList<>(pets);
		PropertyComparator.sort(petsArrayList, new MutableSortDefinition("name", true, true));
		List<Pet> expected = Collections.unmodifiableList(petsArrayList);
		List<Pet> actual = owner.getPets();
		assertEquals(expected, actual);
	}

	@Test
	public void testAddPet_NewPet() {
		// Given
		List<Pet> initialPets = owner.getPets();
		when(petMock.isNew()).thenReturn(true);
		doNothing().when(petMock).setOwner(isA(Owner.class));

		// When
		owner.addPet(petMock);
		List<Pet> actualPets = owner.getPets();

		// Verify
		assertEquals(initialPets.size() + 1, actualPets.size());
		assertTrue(actualPets.contains(petMock));
	}

	@Test // getPetsInternal empty, Add EXISTING pet
	public void testAddPet_existingPet() {
		// Given
		List<Pet> initialPets = owner.getPets();
		when(petMock.isNew()).thenReturn(false);
		doNothing().when(petMock).setOwner(isA(Owner.class));

		// When
		owner.addPet(petMock);
		List<Pet> actualPets = owner.getPets();

		// Verify
		assertEquals(initialPets.size(), actualPets.size());
		assertFalse(actualPets.contains(petMock));
	}

	/*** getPet(String name, boolean ignoreNew) Tests ***/

	// Test 1: ignoreNew = true, pets size =1, pet name DNE
	@Test
	public void testGetPet_NonExistent_IgnoreNew() {
		// Given
		final String nonExistentName = "NonExistent";
		Pet pet1 = new Pet();
		pet1.setName("aPet");
		pet1.setId(1);
		owner.addPet(pet1);

		// when
		Pet actualPet = owner.getPet(nonExistentName, true);

		// Verify
		assertNull(actualPet);

	}

	// Test 2: ignoreNew = false --> test getPet(name)
	@Test
	public void testGetPet_NonExistent_Not_IgnoreNew() {
		// Given
		final String nonExistentName = "NonExistent";

		Set<Pet> pets = makePets(true);
		owner.setPetsInternal(pets);

		// when
		Pet actualPet = owner.getPet(nonExistentName, false);

		// Verify
		assertNull(actualPet);
	}

	// Test 3: ignoreNew = true; pet.isNew() = true (no pet id)
	@Test
	public void testGetPet_NonExistent_IgnoreNew_PetIsNew() {
		// Given
		final String nonExistentName = "NonExistent";
		Pet pet1 = new Pet();
		pet1.setName("aPet");
		owner.addPet(pet1);

		// when
		Pet actualPet = owner.getPet(nonExistentName, true);

		// Verify
		assertNull(actualPet);
	}

	// Test 4: PET EXISTS, ignoreNew = false; pet.isNew() = false (pet has id)
	// new = NO id = true
	@Test
	public void testGetPet_DoesExist() {
		// Given
		final String name = "aPet";

		Set<Pet> pets = makePets(false);
		owner.setPetsInternal(pets);

		// when
		Pet actualPet = owner.getPet(name, false);

		// Verify
		assertEquals(name, actualPet.getName());
	}

	// Another case: names can be the same, but they should not be checked if pet is new

	// Test 5: PET EXISTS, ignoreNew = true; pet.isNew() = true (NO pet id)
	@Test
	public void testGetPet_DoesExist_IgnoreNew_NewPet() { // same as test 4, BUT
															// ignoreNew=true and NO petId
		// Given
		final String name = "aPet";

		Set<Pet> pets = makePets(false);
		owner.setPetsInternal(pets);

		// when
		Pet actualPet = owner.getPet(name, true);

		// Verify
		assertNull(actualPet);
	}

	// Test 6: Ignore New = true; isNew = False; Name Exists = same
	@Test
	public void testGetPet_DoesExist_IgnoreNew_NotNewPet() {
		// Given
		final String name = "aPet";

		Set<Pet> pets = makePets(true);
		owner.setPetsInternal(pets);

		// when
		Pet actualPet = owner.getPet(name, true);

		// Verify
		assertEquals(name, actualPet.getName());
	}

	// Test 7: Ignore New = false; isNew = true; Name Exists = same
	@Test
	public void testGetPet_DoesExist_NotIgnoreNew_NewPet() {
		// Given
		final String name = "aPet";

		Set<Pet> pets = makePets(false);
		owner.setPetsInternal(pets);

		// when
		Pet actualPet = owner.getPet(name, false);

		// Verify
		assertEquals(name, actualPet.getName());
	}

	// Test 8: Ignore New = false; isNew = true; Name Exists = DIFFERENT
	@Test
	public void testGetPet_DoesNotExist_NotIgnoreNew_NewPet() {
		// Given
		final String nonExistent = "NonExistent";

		Set<Pet> pets = makePets(false);
		owner.setPetsInternal(pets);

		// when
		Pet actualPet = owner.getPet(nonExistent, false);

		// Verify
		assertNull(actualPet);
	}

	// TEST getPet (String name) with pet name that does not exist
	@Test
	public void testGetPet_NameDoesNotExist() {
		// Given
		final String nonExistent = "NonExistent";

		Set<Pet> pets = makePets(false);
		owner.setPetsInternal(pets);

		// when
		Pet actualPet = owner.getPet(nonExistent);

		// Verify
		assertNull(actualPet);
	}

	/**
	 * Helper method to produce unsorted Set<Pet> just for test purpose
	 * @param setPetIdA: whether the id of pet a should be set
	 * @return Set<Pet> pets: pet c, pet a, pet b
	 */
	private static Set<Pet> makePets(boolean setPetIdA) {
		Pet pet1 = new Pet();
		pet1.setName("aPet");
		if (setPetIdA)
			pet1.setId(1);

		Pet pet2 = new Pet();
		pet2.setName("bPet");
		pet2.setId(2);

		Pet pet3 = new Pet();
		pet3.setName("cPet");
		pet3.setId(3);

		return new HashSet<>(Arrays.asList(pet3, pet1, pet2)); // pet c, pet a, pet b
	}

}

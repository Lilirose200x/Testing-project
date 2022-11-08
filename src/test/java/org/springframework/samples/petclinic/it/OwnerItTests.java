package org.springframework.samples.petclinic.it;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OwnerItTests {

	private Owner owner;

	@BeforeEach
	void setup() {
		this.owner = new Owner();
	}

	@AfterEach
	void teardown() {
		this.owner = null;
	}

	@Test // Add NEW pet, Verify pet's owner set
	public void testAddPet_Pet_setOwner() {
		// Given
		List<Pet> initialPets = owner.getPets();
		Pet pet = new Pet();

		// When
		owner.addPet(pet);
		List<Pet> actualPets = owner.getPets();

		// Verify
		assertEquals(initialPets.size() + 1, actualPets.size());
		assertTrue(actualPets.contains(pet));
		assertEquals(owner, pet.getOwner());
	}

}

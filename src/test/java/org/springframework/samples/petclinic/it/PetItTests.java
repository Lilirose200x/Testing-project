package org.springframework.samples.petclinic.it;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PetItTests {

	private Pet pet;

	@BeforeEach
	void setup() {
		this.pet = new Pet();
	}

	@AfterEach
	void teardown() {
		this.pet = null;
	}

	@Test // Initial: No visits, Add new visit Verify Visit's pet id is correctly set
	public void testAddVisit_Visit_SetPetId() {
		// Given
		List<Visit> initialVisits = pet.getVisits();
		int expectedPetId = 1;
		pet.setId(expectedPetId);
		Visit visit = new Visit();
		// When
		pet.addVisit(visit);
		List<Visit> actualVisits = pet.getVisits();
		int actualPetId = visit.getPetId();
		// Then
		assertEquals(initialVisits.size() + 1, actualVisits.size());
		assertTrue(actualVisits.contains(visit));
		assertEquals(expectedPetId, actualPetId);
	}

}

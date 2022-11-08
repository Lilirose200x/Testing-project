package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VetsTests {

	@Test
	void testGetVetList_Empty() {
		Vets vetsInstance = new Vets();
		List<Vet> result = vetsInstance.getVetList();
		List<Vet> expected = new ArrayList<>();
		assertEquals(expected, result);
	}

}

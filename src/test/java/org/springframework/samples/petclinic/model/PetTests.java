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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class PetTests {

	private Pet pet;

	@Mock
	Visit visitMock;

	@BeforeEach
	void setup() {
		this.pet = new Pet();
	}

	@AfterEach
	void teardown() {
		this.pet = null;
	}

	@Test
	public void testGetVisitsInternal_Empty() {
		Set<Visit> actualVisits = pet.getVisitsInternal();
		assertEquals(new HashSet<>(), actualVisits);
	}

	@Test
	public void testGetVisitsInternal() {
		Set<Visit> visits = makeVisits(); // 3 visits in the system
		pet.setVisitsInternal(visits);

		Set<Visit> actualVisits = pet.getVisitsInternal();
		assertEquals(visits, actualVisits);
	}

	@Test
	public void testGetVisits() {
		Set<Visit> visits = makeVisits();
		pet.setVisitsInternal(visits);
		ArrayList<Visit> visitArrayList = new ArrayList<>(visits);
		PropertyComparator.sort(visitArrayList, new MutableSortDefinition("date", false, false));
		List<Visit> expected = Collections.unmodifiableList(visitArrayList);
		List<Visit> actualVisits = pet.getVisits();
		assertEquals(expected, actualVisits);
	}

	@Test // Initial: No visits, Add new visit
	public void testAddVisit() {
		// Given
		List<Visit> initialVisits = pet.getVisits();
		doNothing().when(visitMock).setPetId(any());

		// When
		pet.addVisit(visitMock);
		List<Visit> actualVisits = pet.getVisits();
		// Then
		assertEquals(initialVisits.size() + 1, actualVisits.size());
		assertTrue(actualVisits.contains(visitMock));
	}

	/**
	 * Helper method to produce unsorted Set<Visit> just for test purpose
	 * @return Set<Visit> visits: visit3, visit1, visit2
	 */
	private static Set<Visit> makeVisits() {
		Visit visit1 = new Visit();
		Visit visit2 = new Visit();
		Visit visit3 = new Visit();
		return new HashSet<>(Arrays.asList(visit3, visit1, visit2));
	}

}

/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.util.SerializationUtils;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Dave Syer
 */
@ExtendWith(MockitoExtension.class)
class VetTests {

	private Vet vet;

	@Mock
	private Specialty specialtyMock;

	@BeforeEach
	void setup() {
		this.vet = new Vet();
	}

	@AfterEach
	void teardown() {
		this.vet = null;
	}

	@Test
	void testSerialization() {
		Vet vet = new Vet();
		vet.setFirstName("Zaphod");
		vet.setLastName("Beeblebrox");
		vet.setId(123);
		Vet other = (Vet) SerializationUtils.deserialize(SerializationUtils.serialize(vet));
		assertThat(other.getFirstName()).isEqualTo(vet.getFirstName());
		assertThat(other.getLastName()).isEqualTo(vet.getLastName());
		assertThat(other.getId()).isEqualTo(vet.getId());
	}

	@Test
	void testGetSpecialtiesInternal() {
		Set<Specialty> result = vet.getSpecialtiesInternal();
		Set<Specialty> expected = new HashSet<>();
		assertEquals(expected, result);
	}

	@Test
	void testGetSpecialtiesInternal_Multiple() {
		Set<Specialty> specialties = makeSpecialities(); // 3 specialties in system
		vet.setSpecialtiesInternal(specialties);
		Set<Specialty> result = vet.getSpecialtiesInternal();
		assertThat(result).isEqualTo(specialties);
	}

	@Test
	void testGetSpecialties() {
		Set<Specialty> specialties = makeSpecialities(); // Add 3 specialties
		vet.setSpecialtiesInternal(specialties);
		ArrayList<Specialty> specialtyArrayList = new ArrayList<>(specialties);
		PropertyComparator.sort(specialtyArrayList, new MutableSortDefinition("name", true, true));
		List<Specialty> expected = Collections.unmodifiableList(specialtyArrayList);
		List<Specialty> actualSpecialties = vet.getSpecialties();
		assertEquals(expected, actualSpecialties);
	}

	@Test
	void testGetNrOfSpecialties() {
		// Given
		vet.setSpecialtiesInternal(makeSpecialities()); // Add 3 specialties

		// When
		int actualNbSpecialties = vet.getNrOfSpecialties();

		// Verify
		assertEquals(3, actualNbSpecialties);
	}

	@Test // Initial: No Specialties, Add new specialty
	public void testAddSpecialty() {
		// Given
		List<Specialty> initialSpecialties = vet.getSpecialties();

		// When
		vet.addSpecialty(specialtyMock);
		List<Specialty> actualSpecialties = vet.getSpecialties();

		// verify
		assertEquals(initialSpecialties.size() + 1, actualSpecialties.size());
		assertTrue(actualSpecialties.contains(specialtyMock));
	}

	/**
	 * Helper method to produce unsorted Set<Specialty> just for test purpose
	 * @return Set<Specialty> specialities: specialty3, specialty1, specialty2
	 */
	private static Set<Specialty> makeSpecialities() {
		Specialty specialty1 = new Specialty();
		Specialty specialty2 = new Specialty();
		Specialty specialty3 = new Specialty();
		return new HashSet<>(Arrays.asList(specialty3, specialty1, specialty2));
	}

}

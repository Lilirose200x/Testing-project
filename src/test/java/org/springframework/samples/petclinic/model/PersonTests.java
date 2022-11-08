package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonTests {

	private static final String PERSON_FIRST_NAME = "Someone";

	@Test
	void testGetSetFirstName() {
		// Identical test for both get and set
		// because Vet doesn't have a constructor with params
		Person person = new Vet();
		person.setFirstName(PERSON_FIRST_NAME);
		String result = person.getFirstName();
		assertThat(result).isEqualTo(PERSON_FIRST_NAME);
	}

}

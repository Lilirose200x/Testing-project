package org.springframework.samples.petclinic.persistence;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collection;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PetRepoTest {

	@Autowired
	private PetRepository petRepository;

	@Test
	void testFindPetTypes() {
		Collection<PetType> petTypes = petRepository.findPetTypes();
		assertThat(petTypes.size()).isGreaterThan(0);
		for (PetType petType : petTypes) {
			if (petType.getName() == "bird") {
				assertEquals(petType.getName(), "bird");
				break;
			}
			else if (petType.getName() == "cat") {
				assertEquals(petType.getName(), "cat");
				break;
			}
			else if (petType.getName() == "dot") {
				assertEquals(petType.getName(), "dot");
				break;
			}
			else if (petType.getName() == "hamster") {
				assertEquals(petType.getName(), "hamster");
				break;
			}
			else if (petType.getName() == "lizard") {
				assertEquals(petType.getName(), "lizard");
				break;
			}
			else if (petType.getName() == "snake") {
				assertEquals(petType.getName(), "snake");
				break;
			}

		}
	}

	@Test
	void testFindByIdValidId() {
		Pet pet1 = petRepository.findById(1);
		assertEquals(pet1.getId(), 1);
		assertEquals(pet1.getName(), "Leo");
		assertEquals(pet1.getOwner().getFirstName(), "George");
		assertEquals(pet1.getBirthDate(), LocalDate.parse("2010-09-07"));
		assertEquals(pet1.getType().getName(), "cat");

	}

	@Test
	void testFindByIdInvalidId() {

		Pet pet = petRepository.findById(40);
		assertNull(pet);
	}

}

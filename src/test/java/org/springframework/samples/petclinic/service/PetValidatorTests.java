package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PetValidatorTests {

	private PetValidator petValidator;

	@BeforeEach
	void setup() {
		this.petValidator = new PetValidator();
	}

	@AfterEach
	void tearDown() {
		this.petValidator = null;
	}

	@Test
	void testValidate() {
		// Given
		Pet pet = makePet();
		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// When
		petValidator.validate(pet, errors);

		// Verify
		assertEquals(0, errors.getFieldErrorCount());
	}

	@Test
	void testValidate_allWrong() {
		// Given
		Pet pet = new Pet();
		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// When
		petValidator.validate(pet, errors);

		// Verify
		assertEquals(3, errors.getFieldErrorCount());
		assertTrue(errors.hasFieldErrors("name"));
		assertTrue(errors.hasFieldErrors("type"));
		assertTrue(errors.hasFieldErrors("birthDate"));
	}

	@Test
	void testValidate_MissingName() {
		// Given
		Pet pet = makePet();
		pet.setName(null);
		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// When
		petValidator.validate(pet, errors);

		// Verify
		assertEquals(1, errors.getFieldErrorCount());
		assertTrue(errors.hasFieldErrors("name"));
	}

	@Test
	void testValidate_MissingBirthDate() {
		// Given
		Pet pet = makePet();
		pet.setBirthDate(null);
		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// When
		petValidator.validate(pet, errors);

		// Verify
		assertTrue(errors.hasFieldErrors("birthDate"));
	}

	@Test
	void testValidate_MissingIdAndType() {
		// Given
		Pet pet = new Pet();
		pet.setName("Cool Pet");
		pet.setBirthDate(LocalDate.of(Integer.parseInt("2020"), Integer.parseInt("5"), Integer.parseInt("9")));
		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// When
		petValidator.validate(pet, errors);

		// Verify
		assertEquals(1, errors.getFieldErrorCount());
		assertTrue(errors.hasFieldErrors("type"));
	}

	@Test
	void testValidate_MissingId() {
		// Given
		Pet pet = makePet();
		pet.setId(null);
		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// When
		petValidator.validate(pet, errors);

		// Verify
		assertEquals(0, errors.getFieldErrorCount());
	}

	@Test
	void testValidate_MissingType() {
		// Given
		Pet pet = makePet();
		pet.setType(null);
		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// When
		petValidator.validate(pet, errors);

		// Verify
		assertEquals(0, errors.getFieldErrorCount());
	}

	@Test
	void testSupportsEqual() {
		assertTrue(petValidator.supports(Pet.class));
	}

	@Test
	void testSupportsSuper() {
		assertFalse(petValidator.supports(BaseEntity.class));
	}

	/**
	 * Helper method to create Pet just for test purpose
	 * @return Pet with name, birthdate, pet type, pet id
	 */
	private static Pet makePet() {
		Pet pet = new Pet();
		pet.setName("Cool Pet");
		pet.setBirthDate(LocalDate.of(Integer.parseInt("2020"), Integer.parseInt("5"), Integer.parseInt("9")));
		pet.setId(1);
		PetType petType = new PetType();
		petType.setName("Turtle");
		pet.setType(petType);
		return pet;
	}

}

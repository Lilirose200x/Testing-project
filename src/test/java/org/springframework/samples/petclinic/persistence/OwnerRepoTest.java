package org.springframework.samples.petclinic.persistence;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OwnerRepoTest {

	static String lastName = "Owner";

	@Autowired
	private OwnerRepository ownerRepository;

	@BeforeEach
	void createOwner() {
		String lastName = "Owner";
		Owner owner = new Owner();
		owner.setAddress("Address");
		owner.setCity("City");
		owner.setFirstName("Owner");
		owner.setLastName(lastName);
		owner.setTelephone("1234567890");
		owner.setId(1);
		ownerRepository.save(owner);
	}

	@Test
	void testFindByLastNameValid() {

		Owner owner = null;

		List<Owner> list;
		if ((List<Owner>) ownerRepository.findByLastName(lastName) instanceof List)
			list = (List<Owner>) ownerRepository.findByLastName(lastName);
		else
			list = new ArrayList(ownerRepository.findByLastName(lastName));

		owner = list.get(0);

		assertNotNull(list.get(0));
		assertEquals("Owner", owner.getFirstName());
		assertEquals(lastName, owner.getLastName());
		assertEquals("City", owner.getCity());
		assertEquals("Address", owner.getAddress());
		assertEquals("1234567890", owner.getTelephone());

	}

	@Test
	void testFindByLastNameInvalid() {
		String lastName = "Invalid";
		List<Owner> list;
		if ((List<Owner>) ownerRepository.findByLastName(lastName) instanceof List)
			list = (List<Owner>) ownerRepository.findByLastName(lastName);
		else
			list = new ArrayList(ownerRepository.findByLastName(lastName));

		assertEquals(list.size(), 0);

	}

	@Test
	void testFindByValidId() {

		int id = 1;
		Owner owner = null;

		owner = ownerRepository.findById(id);

		assertNotNull(owner);
		assertEquals("Owner", owner.getFirstName());
		assertEquals("City", owner.getCity());
		assertEquals("Address", owner.getAddress());
		assertEquals("1234567890", owner.getTelephone());
		assertEquals(lastName, owner.getLastName());

	}

	@Test
	void testFindByInvalidID() {
		Owner owner = ownerRepository.findById(100);
		assertNull(owner);
	}

}

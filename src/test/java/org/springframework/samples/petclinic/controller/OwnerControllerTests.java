/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for the {@link OwnerController}
 */

@AutoConfigureMockMvc()
@SpringBootTest
class OwnerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OwnerRepository owners;

	private org.springframework.samples.petclinic.model.Owner saagar(String lastname) {
		org.springframework.samples.petclinic.model.Owner saagar = new org.springframework.samples.petclinic.model.Owner();
		saagar.setId(1);
		saagar.setFirstName("Saagar");
		saagar.setLastName(lastname);
		saagar.setAddress("1234 get on the dance floor bld");
		saagar.setTelephone("6085551023");
		saagar.setCity("montreal");
		Pet venom = new Pet();
		PetType cat = new PetType();
		cat.setName("cat");
		venom.setType(cat);
		venom.setName("Venom");
		venom.setBirthDate(LocalDate.now());
		saagar.addPet(venom);
		venom.setId(1);
		return saagar;
	};

	@BeforeEach
	void setup() {
		Owner saagar = saagar("Arya");
		Owner saagar2 = saagar("Nam");
		Owner kevin = saagar("Nam");

		given(this.owners.findByLastName("Arya")).willReturn((Lists.newArrayList(saagar)));
		given(this.owners.findByLastName("Nam")).willReturn((Lists.newArrayList(kevin, saagar2)));

		given(this.owners.findById(1)).willReturn(saagar);
		Visit visit = new Visit();
		visit.setDate(LocalDate.now());
	}

	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/new").param("firstName", "Kevin").param("lastName", "Nam")
				.param("address", "1234 get on the dance floor bld").param("city", "montreal")
				.param("telephone", "6085551023")).andExpect(status().is3xxRedirection());
	}

	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/new").param("firstName", "Camiller").param("lastName", "Cabeller").param("city",

				"Montreal")).andExpect(status().isOk()).andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "address"))
				.andExpect(model().attributeHasFieldErrors("owner", "telephone"))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testInitFindForm() throws Exception {

		mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(model().attributeExists("owner"))
				.andExpect(view().name("owners/findOwners"));
	}

	@Test
	void testProcessFindFormNoOwnersFound() throws Exception {
		mockMvc.perform(get("/owners").param("lastName", "LOL")).andExpect(status().isOk())
				.andExpect(view().name("owners/findOwners"));
	}

	@Test
	void testProcessFindFormOneOwnerFound() throws Exception {
		mockMvc.perform(get("/owners").param("lastName", "Arya")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/1"));
	}

	@Test
	void testProcessFindFormMultipleOwners() throws Exception {
		mockMvc.perform(get("/owners").param("lastName", "Nam")).andExpect(status().isOk())
				.andExpect(model().attributeExists("selections")).andExpect(view().name("owners/ownersList"));
	}

	@Test
	void testInitUpdateOwnerForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/edit", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("owner")).andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", 1).param("firstName", "Saagar").param("lastName", "Arya")
				.param("address", "1234 get on the dance floor bld").param("city", "montreal")
				.param("telephone", "6085551023")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", 1).param("firstName", "Joe").param("lastName", "Bloggs")
				.param("address", "").param("telephone", "")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "address"))
				.andExpect(model().attributeHasFieldErrors("owner", "telephone"))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testShowOwner() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}", 1)).andExpect(status().isOk())
				.andExpect(model().attributeExists("owner"));
	}

}
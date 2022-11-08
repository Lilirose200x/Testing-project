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

package org.springframework.samples.petclinic.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc()
@SpringBootTest
public class PetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetRepository pets;

	@Test
	void testInitCreationForm() throws Exception {
		try {
			mockMvc.perform(get("/owners/{ownerId}/pets/new", 1)).andExpect(status().isOk())
					.andExpect(model().attributeExists("pet")).andExpect(view().name("pets/createOrUpdatePetForm"));
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/new", 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", "bob").param("birthDate", "2016/12/12")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("pet")).andExpect(model().attributeHasFieldErrors("pet", "type"))
				.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(
				post("/owners/{ownerId}/pets/{petId}/edit", 1, 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name", "Betty").param("birthDate", "2016/12/12").param("type", "hamster"))
				.andExpect(status().isOk()).andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(
				post("/owners/{ownerId}/pets/{petId}/edit", 1, 1).contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("name", "Betty").param("birthDate", "2016/12/12"))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("pet"))
				.andExpect(model().attributeHasFieldErrors("pet", "type"))
				.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

}

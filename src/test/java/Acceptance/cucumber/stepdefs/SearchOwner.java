package Acceptance.cucumber.stepdefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.test.annotation.DirtiesContext;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SearchOwner {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private OwnerRepository ownerRepo;

	private List<Owner> owners_want;

	private List<Owner> owners_recieved;

	private String error = "";

	private ResponseEntity<String> response;

	@Before
	public void setUp() {
		owners_want = new ArrayList<>();
		owners_recieved = new ArrayList<>();
		new ArrayList<>();
	}

	@Given("the following Owners are in the system:")
	public void the_following_owners_are_in_the_system(final List<Owner> ownerInfo) throws Exception {

		// add all the owners
		owners_want.addAll(ownerInfo);

		// save all the owners
		for (Owner owner : ownerInfo) {
			ownerRepo.save(owner);
		}

	}

	@When("the User searches for Owners with last name {string}")
	public void the_User_searches_for_Owners_with_last_name(String string) throws Exception {
		if (string.equals(" ")) {
			response = testRestTemplate.getForEntity("/owners", String.class);
		}
		else {
			response = testRestTemplate.getForEntity("/owners?lastName=" + string, String.class);
		}
		Collection<Owner> foundOwner = ownerRepo.findByLastName(string);
		if (foundOwner.isEmpty()) {
			error = "has not been found";
		}
		else {
			Document doc = Jsoup.parse(response.getBody());
			Elements row = doc.selectFirst("table").select("tr");
			for (int i = 1; i < row.size(); i++) {
				Element theRow = row.get(i);
				Elements cols = theRow.select("td");
				Owner rowOwner = new Owner();
				rowOwner.setFirstName(cols.get(0).text());
				rowOwner.setTelephone(cols.get(3).text());
				rowOwner.setAddress(cols.get(1).text());
				rowOwner.setCity(cols.get(2).text());
				rowOwner.setId(i);
				owners_want.add(rowOwner);
				owners_recieved.add(rowOwner);
			}
		}
	}

	@Then("these Owners are displayed:")
	public void these_Owners_are_displayed(final List<Owner> ownerInfo) throws Exception {
		Boolean allFound = true;
		for (Owner owner : ownerInfo) {
			for (Owner actualOwner : owners_recieved) {
				System.out.println("different: " + owner.getFirstName() + " " + actualOwner.getFirstName());
				if (actualOwner.getFirstName().contains(owner.getFirstName())
						&& owner.getAddress().equals(actualOwner.getAddress())
						&& owner.getCity().equals(actualOwner.getCity())
						&& owner.getTelephone().equals(actualOwner.getTelephone())) {
					allFound = true;
					break;
				}
				else {
					allFound = false;
				}
			}
		}
		assertTrue(allFound);
	}

	@Test
	@Then("the system shall raise the error {string}")
	public void the_system_shall_raise_the_error(String string) throws Exception {
		assertEquals("has not been found", error);
	}

}
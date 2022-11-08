package Acceptance.cucumber.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.persistence.OwnerRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;

public class AddOwner {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private OwnerRepository ownerRepository;

	private List<Owner> owners_want;

	private List<Owner> owners_recieved;

	private Owner owner_toAdd;

	private ResponseEntity<String> addOwnerReponse;

	@Before
	public void setUp() {
		owners_want = new ArrayList<>();
		owners_recieved = new ArrayList<>();
		ResponseEntity<String> response = testRestTemplate.getForEntity("/owners", String.class);
		Document document = Jsoup.parse(response.getBody());
		Elements row = document.selectFirst("table").select("tr");

		for (int i = 1; i < row.size(); i++) {
			Element selectedRow = row.get(i);
			Elements cols = selectedRow.select("td");
			Owner owner = new Owner();
			owners_want.add(owner);
			owners_recieved.add(owner);
			owner.setFirstName(cols.get(0).text());
			owner.setLastName(cols.get(1).text());
			owner.setId(i);
			owner.setTelephone(cols.get(2).text());
			owner.setAddress(cols.get(3).text());
			owner.setCity(cols.get(4).text());
		}
	}

	@Given("the following Owner information")
	public void the_following_Owner_information(final Owner ownerInformation) {
		owner_toAdd = ownerInformation;
		owners_want.add(ownerInformation);
	};

	@When("the User asks the System to add the Owner")
	public void the_User_asks_the_System_to_add_the_Owner() {
		addOwnerReponse = testRestTemplate.postForEntity("/owners/new", owner_toAdd, String.class);
	}

	@Then("the System adds the Owner to the database")
	public void the_System_adds_the_Owner_to_the_database() {
		Owner returnedOwner = ownerRepository.findById(owner_toAdd.getId());
		assertNull(returnedOwner);
		System.out.println(owner_toAdd.getId());
		owners_recieved.add(returnedOwner);
		assertEquals(owners_want.get(owners_want.size() - 1), owners_recieved.get(owners_recieved.size() - 1));
	}

	@Then("the new Owner is not added")
	public void the_new_owner_should_not_be_added() {
		Owner returnedOwner = ownerRepository.findById(owner_toAdd.getId());
		assertNull(returnedOwner);
	}

	@Then("a First Name Missing error message is displayed to the User")
	public void a_First_Name_Missing_error_message_is_displayed_to_the_User() {
		Document document = Jsoup.parse(addOwnerReponse.getBody());
		Elements inputFirstName = document.select("div.has-error");
		for (Element el : inputFirstName) {
			if (el.select("input#firstName") != null) {
				assertEquals("must not be empty", el.select("span.help-inline").text());
				return;
			}
		}
		fail();
	}

	@Then("a Last Name Missing error message is displayed to the User")
	public void a_Last_Name_Missing_error_message_is_displayed_to_the_User() {
		Document document = Jsoup.parse(addOwnerReponse.getBody());
		Elements inputLastName = document.select("div.has-error");
		for (Element el : inputLastName) {
			if (el.select("input#lastName") != null) {
				assertEquals("must not be empty", el.select("span.help-inline").text());
				return;
			}
		}
		fail();
	}

	@Then("a Telephone Missing error message is displayed to the User")
	public void a_Telephone_Missing_error_message_is_displayed_to_the_User() {
		Document document = Jsoup.parse(addOwnerReponse.getBody());
		Elements inputAddress = document.select("div.has-error");
		for (Element el : inputAddress) {
			if (el.select("input#address") != null) {
				String telephone = el.select("span.help-inline").text();
				assertEquals("must not be empty", telephone);
				return;
			}
		}
		fail();
	}

	@Then("an Address Missing error message is displayed to the User")
	public void an_Address_Missing_error_message_is_displayed_to_the_User() {
		Document document = Jsoup.parse(addOwnerReponse.getBody());
		Elements inputAddress = document.select("div.has-error");
		for (Element el : inputAddress) {
			if (el.select("input#address") != null) {
				assertEquals("must not be empty", el.select("span.help-inline").text());
				return;
			}
		}
		fail();
	}

	@Then("a City Missing error message is displayed to the User")
	public void a_City_Missing_error_message_is_displayed_to_the_User() {
		Document document = Jsoup.parse(addOwnerReponse.getBody());
		Elements inputCity = document.select("div.has-error");
		for (Element el : inputCity) {
			if (el.select("input#city") != null) {
				assertEquals("must not be empty", el.select("span.help-inline").text());
				return;
			}
		}
		fail();
	}

}

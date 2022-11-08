package Acceptance.cucumber.stepdefs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.persistence.VisitRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateVisit {

	@LocalServerPort
	public int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private VisitRepository visitRepository;

	HttpHeaders httpHeaders = new HttpHeaders();

	private Pet selectedPet;

	private List<Visit> visits_want;

	private List<Visit> visits_recieved;

	private Visit visit_toAdd;

	private ResponseEntity<String> addVisitResponse;

	@Before
	public void setup() {
		visits_recieved = new ArrayList<>();
		visits_want = new ArrayList<>();
	}

	@Given("the following Visit information")
	public void the_following_Visit_information(final Visit visit) {
		visit_toAdd = visit;
		selectedPet = petRepository.findById(visit.getPetId());
		List<Visit> listOfVisits = visitRepository.findByPetId(selectedPet.getId());
		if (listOfVisits.size() > 0) {
			visits_want.addAll(listOfVisits);
		}
	}

	@Given("the Visit has date")
	public void the_Visit_has_date(DataTable dataTable) {
		List<String> rows = dataTable.asList(String.class);
		String input = rows.get(0);
		boolean shouldAdd = true;
		if (!input.equals("-")) {
			// format if it is valid
			if (isDateValidFormat(input)) {
				shouldAdd = true;
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				visit_toAdd.setDate(LocalDate.parse(input, dateFormatter));
			}
			// if it isn't blank, but has the wrong format
			else {
				visit_toAdd.setDate(null);
			}
		}

		if (visit_toAdd.getDescription() == null) {
			shouldAdd = false;
		}
		// default value already exists, so do nothing if it has a value
		if (shouldAdd) {
			visits_want.add(visit_toAdd);
			visitRepository.save(visit_toAdd);
		}
	}

	@When("the user submits the new Visit")
	public void the_user_submits_the_new_Visit() throws JsonMappingException, JsonProcessingException {
		String url = "/owners/{ownerId}/pets/{petId}/visits/new";
		String ownerId = Integer.toString(selectedPet.getOwner().getId());
		String petId = Integer.toString(selectedPet.getId());
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("ownerId", ownerId);
		urlParams.put("petId", petId);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		builder.buildAndExpand(urlParams).toUri().toString();
		addVisitResponse = testRestTemplate.postForEntity(builder.buildAndExpand(urlParams).toUri(), visit_toAdd,
				String.class);
	}

	@Then("the Visit is added to the system")
	public void the_Visit_is_added_to_the_system() {
		List<Visit> listOfVisits = visitRepository.findByPetId(selectedPet.getId());
		visits_recieved.addAll(listOfVisits);
		assertEquals(visits_want.size(), visits_recieved.size());
		for (int i = 0; i < visits_want.size(); i++) {
			assertEquals(visits_want.get(i).getDate(), visits_recieved.get(i).getDate());
			assertEquals(visits_want.get(i).getDescription(), visits_recieved.get(i).getDescription());
		}
	}

	@Then("the visit should not be added to the database")
	public void the_visit_should_not_be_added_to_the_database() {
		int id = selectedPet.getId();
		List<Visit> returnedVisits = visitRepository.findByPetId(id);
		if (returnedVisits != null) {
			visits_recieved.addAll(returnedVisits);
		}
		int size_expected = visits_want.size();
		int size_actual = visits_recieved.size();
		if (size_expected == 0 || size_actual == 0) {
			assertEquals(size_expected, size_actual);
		}
		else {
			assertEquals(visits_want.get(visits_want.size() - 1).getId(),
					visits_recieved.get(visits_recieved.size() - 1).getId());
			assertEquals(size_expected, size_actual);
		}
	}

	@And("the system will redisplay the form page")
	public void the_system_will_redisplay_the_form_page() {
		Document document = Jsoup.parse(addVisitResponse.getBody());
		Element form = document.selectFirst("div.form-group");
		Elements inputs = form.select("div.form-group");
		List<String> actualLabelText = new ArrayList<>();
		actualLabelText.add(document.selectFirst("button.btn").text());
		for (Element element : inputs) {
			actualLabelText.add(element.selectFirst("label").text());
		}

		assertTrue(actualLabelText.contains("Add Visit"));
		assertTrue(actualLabelText.contains("Description"));
		assertTrue(actualLabelText.contains("Date"));

	}

	@Then("an error message is displayed to the User.")
	public void an_error_message_is_displayed_to_the_User() {
		Document document = Jsoup.parse(addVisitResponse.getBody());
		Element form = document.selectFirst("div.has-error");
		Element errorMessage = form.selectFirst("span.help-inline");
		assertEquals("must not be empty", errorMessage.text());
	}

	@Then("a date error message is displayed to the User.")
	public void a_date_error_message_is_displayed_to_the_User() {
		Document document = Jsoup.parse(addVisitResponse.getBody());
		Element form = document.selectFirst("div.has-error");
		Element errorMessage = form.selectFirst("span.help-inline");
		assertEquals("must not be empty", errorMessage.text());
	}

	@After()
	public void tearDown() {
		visits_want.clear();
		visits_recieved.clear();
	}

	private boolean isDateValidFormat(String date) {
		Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		return pattern.matcher(date).matches();
	}

}
package Acceptance.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.PetClinicApplication;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = { PetClinicApplication.class, CucumberTest.class },
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberOptions(plugin = { "pretty" }, features = "src/test/java/Acceptance/features", tags = "not @ignore")
public class CucumberTest {

}
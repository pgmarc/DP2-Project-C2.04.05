
package acme.testing.assistant.tutorial;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialDeleteTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest()
	@CsvFileSource(resources = "/assistant/tutorial/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String draftMode) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Tutorials", "My tutorials");
		super.checkCurrentPath("/assistant/tutorial/list");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		if (draftMode == "true") {
			super.checkSubmitExists("Delete");
			super.clickOnSubmit("Delete");
			super.checkNotPanicExists();
			super.checkCurrentPath("/assistant/tutorial/list");
			super.checkListingExists();
		}

		super.signOut();
	}

	@Test()
	public void test200negative() {
		//No negative test needed
	}

	@Test
	public void test300hacking() {

		final Tutorial tutorial = ((List<Tutorial>) this.repository.findAllTutorials()).get(0);
		final String path = "/assistant/tutorial/delete";
		final String query = "id=" + tutorial.getId();

		super.checkLinkExists("Sign in");
		super.request(path, query);
		super.checkPanicExists();

		super.signIn("Administrator1", "administrator1");
		super.request(path, query);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request(path, query);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request(path, query);
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request(path, query);
		super.checkPanicExists();
		super.signOut();
	}
}

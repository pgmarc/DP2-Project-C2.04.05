
package acme.testing.assistant.tutorial;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialShowTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String code, final String title, final String abstrac, final String goals, final String estimatedHours, final String draftMode) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Tutorials", "My tutorials");
		super.checkCurrentPath("/assistant/tutorial/list");
		super.checkListingExists();
		super.checkNotListingEmpty();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("abstrac", abstrac);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("estimatedHours", estimatedHours);
		super.checkInputBoxHasValue("draftMode", draftMode);

		if (draftMode == "false") {
			super.checkButtonExists("Update");
			super.checkButtonExists("Delete");
			super.checkButtonExists("Publish");
		}
		super.checkButtonExists("Sessions");
		super.checkButtonExists("Course");
		super.checkNotButtonExists("Create");

		super.signOut();
	}

	@Test
	public void test200negative() {
		//No negative testing needed
	}

	@Test
	public void test300hacking() {

		super.signIn("Administrator1", "administrator1");

		final List<Tutorial> tutorials = (List<Tutorial>) this.repository.findAllTutorials();

		for (final Tutorial tutorial : tutorials) {
			final int id = tutorial.getId();
			super.request("/assistant/tutorial/show?id=" + id);
			super.checkCurrentPath("/assistant/tutorial/show?id=" + id);
			super.checkPanicExists();
		}

		super.signOut();
	}

}


package acme.testing.assistant.tutorialsession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialSessionCreateTest extends TestHarness {

	final String										path	= "/assistant/tutorial-session/create";
	@Autowired
	protected AssistantTutorialSessionTestRepository	repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialsession/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndexTutorial, final String draftMode, final String title, final String abstrac, final String goals, final String sessionNature, final String startDate, final String finishDate) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Tutorials", "My tutorials");
		super.checkCurrentPath("/assistant/tutorial/list");
		super.checkListingExists();
		super.checkNotListingEmpty();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndexTutorial);
		super.checkButtonExists("Sessions");
		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.checkNotListingEmpty();
		super.sortListing(0, "asc");

		if (draftMode.equals("true")) {
			super.checkButtonExists("Create");
			super.clickOnButton("Create");

			super.checkFormExists();
			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("abstrac", abstrac);
			super.fillInputBoxIn("goals", goals);
			super.fillInputBoxIn("sessionNature", sessionNature);
			super.fillInputBoxIn("startDate", startDate);
			super.fillInputBoxIn("finishDate", finishDate);
			super.checkSubmitExists("Create");
			super.clickOnSubmit("Create");
			super.checkNotErrorsExist();
			super.checkListingExists();
		} else
			super.checkNotSubmitExists("Create");

		super.signOut();
	}

	@ParameterizedTest()
	@CsvFileSource(resources = "/assistant/tutorial/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200negative(final int recordIndexTutorial, final String draftMode, final String title, final String abstrac, final String goals, final String startDate, final String finishDate) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Tutorials", "My tutorials");
		super.checkCurrentPath("/assistant/tutorial/list");
		super.checkListingExists();
		super.checkNotListingEmpty();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndexTutorial);
		super.checkButtonExists("Sessions");
		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.checkNotListingEmpty();
		super.sortListing(0, "asc");

		if (draftMode.equals("true")) {
			super.checkButtonExists("Create");
			super.clickOnButton("Create");

			super.checkFormExists();
			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("abstrac", abstrac);
			super.fillInputBoxIn("goals", goals);
			super.fillInputBoxIn("startDate", startDate);
			super.fillInputBoxIn("finishDate", finishDate);
			super.checkSubmitExists("Create");
			super.clickOnSubmit("Create");
			super.checkErrorsExist();
			super.checkNotListingExists();
		} else
			super.checkNotSubmitExists("Create");

		super.signOut();
	}

	@Test
	public void test300hacking() {
		String query;

		final Collection<Tutorial> tutorials = this.repository.findManyTutorialsByAssitantUsername("assistant2");
		for (final Tutorial t : tutorials) {
			query = String.format("tutorialId=%d", t.getId());

			super.checkLinkExists("Sign in");
			super.request(this.path, query);
			super.checkPanicExists();

			super.signIn("Administrator1", "administrator1");
			super.request(this.path, query);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request(this.path, query);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request(this.path, query);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request(this.path, query);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request(this.path, query);
			super.checkPanicExists();
			super.signOut();
		}

	}
}

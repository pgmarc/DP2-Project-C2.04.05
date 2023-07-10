
package acme.testing.assistant.tutorialsession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialSessionListTest extends TestHarness {

	final String										path	= "/assistant/tutorial-session/list";

	@Autowired
	protected AssistantTutorialSessionTestRepository	repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialsession/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndexTutorial, final int recordIndexSession, final String title, final String abstrac) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("My tutorials");
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
		super.checkColumnHasValue(recordIndexSession, 0, title);
		super.checkColumnHasValue(recordIndexSession, 1, abstrac);

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

			super.signIn("administrator1", "administrator1");
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
		}

	}

}

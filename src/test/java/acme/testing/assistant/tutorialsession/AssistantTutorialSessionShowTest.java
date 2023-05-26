
package acme.testing.assistant.tutorialsession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionShowTest extends TestHarness {

	@Autowired
	protected AssistantTutorialSessionTestRepository	repository;

	final String										path	= "/assistant/tutorial-session/show";


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialsession/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndexTutorial, final int recordIndexSession, final String title, final String abstrac, final String goals, final String sessionNature, final String startDate, final String finishDate) {

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
		super.checkColumnHasValue(recordIndexSession, 0, title);
		super.checkColumnHasValue(recordIndexSession, 1, abstrac);
		super.clickOnListingRecord(recordIndexSession);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstrac", abstrac);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("sessionNature", sessionNature);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);

		super.signOut();
	}

	@Test
	public void test300hacking() {
		String query;

		final Collection<Tutorial> tutorials = this.repository.findManyTutorialsByAssitantUsername("assistant2");
		for (final Tutorial t : tutorials) {
			final Collection<TutorialSession> sessions = this.repository.findTutorialSessionsByTutorialId(t.getId());
			for (final TutorialSession ts : sessions) {
				query = String.format("id=%d", ts.getId());

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
			}

		}
	}

}


package acme.testing.assistant.tutorialsession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionUpdateTest extends TestHarness {

	@Autowired
	protected AssistantTutorialSessionTestRepository	repository;

	final String										path	= "/assistant/tutorial-session/update";


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialsession/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndexTutorial, final int recordIndexSession, final String draftMode, final String title, final String abstrac, final String goals, final String sessionNature, final String startDate,
		final String finishDate) {

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
		super.clickOnListingRecord(recordIndexSession);
		super.checkFormExists();

		if (draftMode.equals("true")) {
			super.checkFormExists();
			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("abstrac", abstrac);
			super.fillInputBoxIn("goals", goals);
			super.fillInputBoxIn("sessionNature", sessionNature);
			super.fillInputBoxIn("startDate", startDate);
			super.fillInputBoxIn("finishDate", finishDate);
			super.checkSubmitExists("Update");
			super.clickOnSubmit("Update");
			super.checkNotErrorsExist();
			super.checkListingExists();
		}

		super.signOut();
	}

	@ParameterizedTest()
	@CsvFileSource(resources = "/assistant/tutorialsession/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200negative(final int recordIndexTutorial, final int recordIndexSession, final String draftMode, final String title, final String abstrac, final String goals, final String sessionNature, final String startDate,
		final String finishDate) {

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
		super.clickOnListingRecord(recordIndexSession);
		super.checkFormExists();

		if (draftMode.equals("true")) {
			super.checkFormExists();
			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("abstrac", abstrac);
			super.fillInputBoxIn("goals", goals);
			super.fillInputBoxIn("sessionNature", sessionNature);
			super.fillInputBoxIn("startDate", startDate);
			super.fillInputBoxIn("finishDate", finishDate);
			super.checkSubmitExists("Update");
			super.clickOnSubmit("Update");
			super.checkErrorsExist();
			super.checkNotListingExists();
		}

		super.signOut();
	}

	@Test
	void test300Hacking() {
		String query;

		final Collection<Tutorial> tutorials = this.repository.findManyTutorialsByAssitantUsername("assistant2");
		for (final Tutorial t : tutorials) {
			final Collection<TutorialSession> sessions = this.repository.findTutorialSessionsByTutorialId(t.getId());
			for (final TutorialSession ts : sessions) {
				query = String.format("id=%d", ts.getId());

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

				super.signIn("assistant1", "assistant1");
				super.request(this.path, query);
				super.checkPanicExists();
				super.signOut();

				if (!t.isDraftMode()) {
					super.signIn("assistant2", "assistant2");
					super.request(this.path, query);
					super.checkPanicExists();
					super.signOut();
				}
			}

		}
	}

}

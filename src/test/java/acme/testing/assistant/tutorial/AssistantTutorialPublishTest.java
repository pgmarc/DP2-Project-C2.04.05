
package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialPublishTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository	repository;

	final String								path	= "/assistant/tutorial/publish";


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String title, final String code, final String draftMode) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Tutorials", "My tutorials");
		super.checkCurrentPath("/assistant/tutorial/list");
		super.checkListingExists();
		super.checkNotListingEmpty();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkSubmitExists("Publish");
		super.clickOnSubmit("Publish");

		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("draftMode", "false");
		super.checkNotSubmitExists("Publish");

		super.signOut();
	}

	@Test
	void test300Hacking() {
		Collection<Tutorial> tutorials;
		String query;

		tutorials = this.repository.findManyTutorialsByAssitantUsername("assistant1");
		for (final Tutorial tutorial : tutorials)
			if (tutorial.isDraftMode()) {
				query = String.format("id=%d", tutorial.getId());

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

				super.signIn("assistant2", "assistant2");
				super.request(this.path, query);
				super.checkPanicExists();
				super.signOut();

			}
	}

	@Test
	void test301Hacking() {
		Collection<Tutorial> tutorials;
		String query;

		tutorials = this.repository.findManyTutorialsByAssitantUsername("assitant1");
		for (final Tutorial tutorial : tutorials)
			if (!tutorial.isDraftMode()) {
				query = String.format("id=%d", tutorial.getId());

				super.signIn("assistant1", "assistant1");
				super.request(this.path, query);
				super.checkPanicExists();
				super.signOut();

			}
	}
}
